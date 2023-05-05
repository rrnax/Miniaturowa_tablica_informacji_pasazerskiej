package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmObject;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmValue;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays.WarsawDisplay;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
// FUNCTION READS FROM STATIC JSON FILE IN /resources/static and returns array with Objects
public class staticDataLoader {
    public static ArrayList<WarsawDisplay>  loadDataFromJsonFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (f.exists()){
            InputStream is = new FileInputStream(fileName);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ZtmObject>>() {}.getType();
            List<ZtmObject> ztmObjectList = gson.fromJson(jsonTxt, listType);

            List<ZtmValue> ztmValueList = new ArrayList<>();
            for (ZtmObject ztmObject : ztmObjectList) {
                ztmValueList.addAll(ztmObject.getValues());
            }
            ArrayList<WarsawDisplay> warsawDisplayList = new ArrayList<>();
            WarsawDisplay warsawDisplay = new WarsawDisplay();
            Integer i = 0;
            for (ZtmValue ztmValue : ztmValueList) {
                switch(ztmValue.getKey()){
                    case "zespol":
                        warsawDisplay.setZespol(ztmValue.getValue());
                        i++;
                        break;
                    case "slupek":
                        warsawDisplay.setSlupek(ztmValue.getValue());
                        i++;
                        break;
                    case "nazwa_zespolu":
                        warsawDisplay.setNazwa_zespolu(ztmValue.getValue());
                        i++;
                        break;
                    case "id_ulicy":
                        warsawDisplay.setId_ulicy(ztmValue.getValue());
                        i++;
                        break;
                    case "szer_geo":
                        warsawDisplay.setSzer_geo(ztmValue.getValue());
                        i++;
                        break;
                    case "dlug_geo":
                        warsawDisplay.setDlug_geo(ztmValue.getValue());
                        i++;
                        break;
                    case "kierunek":
                        warsawDisplay.setKierunek(ztmValue.getValue());
                        i++;
                        break;
                    default:
                        break;
                }
                if(i==7){
                    i=0;
                    warsawDisplayList.add(warsawDisplay);
                    warsawDisplay = new WarsawDisplay();
                }

            }
            return warsawDisplayList;
        }else{
            System.out.println("No file named: "+fileName+ " for Warsaw ZTM Static Data");
            return null;
        }
    }





}