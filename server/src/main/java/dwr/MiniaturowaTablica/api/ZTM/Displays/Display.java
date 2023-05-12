package dwr.MiniaturowaTablica.api.ZTM.Displays;


// DISPLAY VERSION FOR FRONTEND

import java.util.List;

public class Display {
    public String name;
    public List<String> displayCodes;

    public Display(String name, List<String> displayCodes) {
        this.name = name;
        this.displayCodes = displayCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDisplayCodes() {
        return displayCodes;
    }

    public void setDisplayCodes(List<String> displayCodes) {
        this.displayCodes = displayCodes;
    }
}
