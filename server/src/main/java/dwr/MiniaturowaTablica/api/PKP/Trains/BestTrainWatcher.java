package dwr.MiniaturowaTablica.api.PKP.Trains;

import dwr.MiniaturowaTablica.api.PKP.PKPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BestTrainWatcher {
    @Autowired
    private PKPRepository pkpRepository;
    @Autowired
    private TrainPKPService trainPKPService;


    private List<String> czasList = new ArrayList<>(); // lista najbliższych terminów odjazdów lub przyjazdów dla danego pociągu
    private List<Train> bestTrainslist = new ArrayList<>(); // tutaj mamy listę obiektów ktore bedziemy przesylac na endpoincie, nie updaetujemy ich w bazie
    private List<Double>  distanceIncrement = new ArrayList<>(); // lista z przyrostami dystansu co sekunde dla konkretnych pociagow (powiazanie po indexach)

    public void run() {
        bestTrainslist = pkpRepository.getBestTrains();
        for (int i = 0; i <bestTrainslist.size() ; i++) {
            czasList.add("00:00");
        }
        updateFullTimeList(); // init dla listy czasow
        LocalTime currentTime = LocalTime.now();
        bestTrainslist.forEach(e->{
            System.out.println(e);
        });

        for (int i = 0; i < czasList.size(); i++) {
            String czas = czasList.get(i);
            LocalTime targetTime = LocalTime.parse(czas);
            if (currentTime.equals(targetTime)) {
                System.out.println("Spełniony warunek dla czasu " + czas + " (indeks: " + i + ")");
            }
        }

        try {
            Thread.sleep(1000); // Poczekaj sekundę przed sprawdzeniem ponownie
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
                czasList.set(index, t.getScheduleList().get(0).getArrival_time());
            }
            else if (currentTime.isBefore(LocalTime.parse(t.getScheduleList().get(0).getEstimatedTime()))) { // jesli przed odjazdem
                czasList.set(index, t.getScheduleList().get(0).getEstimatedTime());
                t.setStatus("NA STACJI");
            }
            else {  // jesli juz po odjezdzie, usun pierwszy rekord z schedule dla danego t (w bazie)
                Schedule lastStopArrival = t.getFirstSchedule(); // wez pierwszy element z listy ostatnich arrivals, bo chcemy go zapisać do ostatniego przystanku dla pociągu
                t.deleteFirstSchedule();
                trainPKPService.updateTrainSchedule(t.getId(), t.getScheduleList(),lastStopArrival);
                if (t.getScheduleList().size() > 0) {// jesli w ogole jest kolejny przystanek
                    t.setStatus("W DRODZE2");
                    czasList.set(index, t.getScheduleList().get(0).getArrival_time());
                }
                else t.setStatus("KONIEC");
            }
        }
    }

    private void calculateDistanceIncrement(Train t){
        Double incrementValue = 0.0;
        switch(t.getStatus()){
            case "NA STACJI" :
            case "KONIEC":
                break;
            case " W DRODZE":
                // od arrival time najblizszego przystanku ODEJMUJEMYestimated time w historyArrival (sekundy)
                // od przyszlego officialDistance odejmujemy historyczny (metry)
                // dzielimy metry przez sekundy

        }
    }

    }

