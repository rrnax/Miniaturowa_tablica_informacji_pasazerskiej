package dwr.MiniaturowaTablica.api.PKP.Trains;

import dwr.MiniaturowaTablica.api.PKP.PKPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class BestTrainWatcher {

    @Autowired
    private TrainPKPService trainPKPService;


    private List<String> czasList = new ArrayList<>(); // lista najbliższych terminów odjazdów lub przyjazdów dla danego pociągu
    private List<Train> bestTrainslist = new ArrayList<>(); // tutaj mamy listę obiektów ktore bedziemy przesylac na endpoincie, nie updaetujemy ich w bazie
    private List<Double>  distanceIncrement = new ArrayList<>(); // lista z przyrostami dystansu co sekunde dla konkretnych pociagow (powiazanie po indexach)
    private List<Double> distanceNow = new ArrayList<>();

    public void run() {
        bestTrainslist = trainPKPService.getFirst10Trains();
        for (int i = 0; i <bestTrainslist.size() ; i++) {
            czasList.add("00:00");
            distanceIncrement.add(0.0);
        }
        updateFullTimeList(); // init dla listy czasow

        for (int i = 0; i < bestTrainslist.size(); i++) {
            distanceIncrement.add(calculateDistanceIncrement(bestTrainslist.get(i)));
            distanceNow.add(calculatePresentDistance(bestTrainslist.get(i),i));
        }
        loopThread.start();

        }

        Thread loopThread = new Thread(() -> {
        System.out.println("-------------------------------------------------------------");
        while (true) {
            for (int indeks = 0; indeks < czasList.size(); indeks++) {
                if (!bestTrainslist.get(indeks).getStatus().equals("KONIEC")) {
                    if (bestTrainslist.get(indeks).getStatus().equals("W DRODZE")) { // incrementing distance if "W DRODZE"
                        distanceNow.set(indeks, distanceNow.get(indeks) + distanceIncrement.get(indeks));
                        bestTrainslist.get(indeks).setDistanceTraveled(distanceNow.get(indeks).intValue());
                    }
                    LocalTime aktualnyCzas = LocalTime.now();
                    LocalTime czasDocelowy = LocalTime.parse(czasList.get(indeks));
                    if (aktualnyCzas.isAfter(czasDocelowy) || aktualnyCzas.equals(czasDocelowy)) {
                        updateTimeList(indeks);
                    }
                    System.out.println(bestTrainslist.get(indeks));
                    System.out.println(distanceIncrement.get(indeks));
                }
            }
            System.out.println("-------------------------------------------------------------");
            try {
                Thread.sleep(1000); // Oczekiwanie
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });




    //funkcja updateuje listę najbliższych terminów odjazdów lub przyjazdów dla danego pociągu
    private void updateFullTimeList(){
        LocalTime currentTime = LocalTime.now();
        for (int i = 0; i <bestTrainslist.size() ; i++) {
            updateTrainSchedule(bestTrainslist.get(i),currentTime,i);
        }
    }

    //funkcja updateuje jeden obiekt z listy najbliższych terminów odjazdów lub przyjazdów dla danego pociągu
    private void updateTimeList(Integer index){
        LocalTime currentTime = LocalTime.now();
        Train t = bestTrainslist.get(index);
        updateTrainSchedule(t,currentTime, index);

    }

    private void updateTrainSchedule(Train t, LocalTime currentTime, Integer index){
        if(t.getScheduleList().size()>0) { // sprawdzenie dla bezpieczenstwa
            if (currentTime.isBefore(LocalTime.parse(t.getScheduleList().get(0).getArrival_time())))
            { // jesli przed dojazdem
                t.setStatus("W DRODZE");
                distanceIncrement.set(index,calculateDistanceIncrement(t));
                czasList.set(index, t.getScheduleList().get(0).getArrival_time());

            }
            else if (currentTime.isBefore(LocalTime.parse(t.getScheduleList().get(0).getEstimatedTime()))) { // jesli przed odjazdem
                czasList.set(index, t.getScheduleList().get(0).getEstimatedTime());
                distanceIncrement.set(index,0.0);
                t.setStatus("NA STACJI");
            }
            else {  // jesli juz po odjezdzie, usun pierwszy rekord z schedule dla danego t (w bazie)
                if (t.getScheduleList().size() > 0) {// jesli w ogole jest kolejny przystanek
                    Schedule lastStopArrival = t.getFirstSchedule(); // wez pierwszy element z listy ostatnich arrivals, bo chcemy go zapisać do ostatniego przystanku dla pociągu
                    t.deleteFirstSchedule();
                    trainPKPService.updateTrainSchedule(t.getId(), t.getScheduleList(),lastStopArrival);
                    t.setStatus("W DRODZE");
                    distanceIncrement.set(index,calculateDistanceIncrement(t));
                    czasList.set(index, t.getScheduleList().get(0).getArrival_time());
                }
                else t.setStatus("KONIEC");
            }
        }
        else t.setStatus("KONIEC");
    }

    private Double calculateDistanceIncrement(Train t){
        Double incrementValue = 0.0;
        switch(t.getStatus()){
            case "NA STACJI" :
            case "KONIEC":
            case "?":
                break;
            case "W DRODZE":
                //1 od arrival time najblizszego przystanku ODEJMUJEMYestimated time w historyArrival (sekundy)
                //2 od przyszlego officialDistance odejmujemy historyczny (metry)
                //3 dzielimy metry przez sekundy
                //1
                String startTime = t.getFirstSchedule().arrival_time;
                String endTime = t.getLastStopSchedule().estimatedTime;
                LocalTime time1 = LocalTime.parse(startTime);
                LocalTime time2 = LocalTime.parse(endTime);
                long timeBetween = time2.until(time1, ChronoUnit.SECONDS);
                //2
                String startDistance = t.getLastStopSchedule().getOfficial_dist_traveled();
                String endDistance = t.getFirstSchedule().getOfficial_dist_traveled();
                int distanceBetween = Integer.valueOf(endDistance) - Integer.valueOf(startDistance);

// Konwersja na typ double przed wykonaniem dzielenia
                incrementValue = (double) distanceBetween / timeBetween;

        }
        return incrementValue;
    }

    private Double calculatePresentDistance(Train t, Integer indeks){
        switch(t.getStatus()) {
            case "KONIEC":
            case "NA STACJI":
            case "?":
                return Double.valueOf(t.getLastStopSchedule().official_dist_traveled);
            case "W DRODZE":
                try {
                    String startTime = t.getLastStopSchedule().estimatedTime;
                    LocalTime time1 = LocalTime.parse(startTime);
                    LocalTime time2 = LocalTime.now();
                    long timeBetween = time1.until(time2, ChronoUnit.SECONDS);
                    Double distanceBetween = (double) timeBetween * distanceIncrement.get(indeks);
                    return Double.valueOf(t.getLastStopSchedule().official_dist_traveled) + distanceBetween;
                } catch (DateTimeParseException e) {// Obsługa wyjątku parsowania czasu
                        System.out.println("Błąd parsowania czasu: " + e.getMessage());
                } catch (ArithmeticException e) {// Obsługa wyjątku arytmetycznego
                        System.out.println("Błąd arytmetyczny: " + e.getMessage());
                }  catch (Exception e) {// Obsługa innych wyjątków
                        System.out.println("Wystąpił inny wyjątek: " + e.getMessage());
        }
        }
        return Double.valueOf(t.getLastStopSchedule().official_dist_traveled);
    }
    public List<Train> getBestTrains(){
        return bestTrainslist;
    }

    }

