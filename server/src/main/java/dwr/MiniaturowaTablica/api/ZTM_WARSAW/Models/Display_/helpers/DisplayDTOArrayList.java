package dwr.MiniaturowaTablica.api.ZTM_WARSAW.Models.Display_.helpers;

import dwr.MiniaturowaTablica.api.models.ztm.DisplayDTO;

import java.util.ArrayList;
import java.util.List;

public class DisplayDTOArrayList {

    public ArrayList<DisplayDTO> displayDTOS = new ArrayList<>();;

    public DisplayDTOArrayList(List<DisplayDTO> displayDTOS) {
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
