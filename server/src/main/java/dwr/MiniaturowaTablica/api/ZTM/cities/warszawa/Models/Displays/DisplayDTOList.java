package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays;

import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;

import java.util.ArrayList;
import java.util.List;

public class DisplayDTOList {

    public ArrayList<DisplayDTO> displayDTOS = new ArrayList<>();;

    public DisplayDTOList(List<DisplayDTO> displayDTOS) {
        displayDTOS.forEach(e->{
            Integer index = this.returnContainIndex(e);
            if(index!=-1){
                this.displayDTOS.get(index).setNextIdStop(e);
            }else{
                this.displayDTOS.add(e);
            }
        });

    }

    public Integer length(){
        return displayDTOS.size();
    }

    public Integer returnContainIndex(DisplayDTO displayDTO){
        for (int i = 0; i < displayDTOS.size(); i++) {
            if(displayDTOS.get(i).getName().equals(displayDTO.getName())
                && displayDTOS.get(i).getDrivingDirection().equals(displayDTO.getDrivingDirection())){
                return i;
            }
        }
        return -1;
    }

}
