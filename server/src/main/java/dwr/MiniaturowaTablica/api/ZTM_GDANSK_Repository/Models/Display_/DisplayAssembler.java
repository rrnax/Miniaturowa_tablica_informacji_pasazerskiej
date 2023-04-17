package dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_;

import org.springframework.stereotype.Service;

@Service
public class DisplayAssembler {

    public DisplayDTO toDisplayDTO(Display display) {
        DisplayDTO displayDTO = new DisplayDTO();


        if (display != null) {
            displayDTO.setDisplayCode( display.getDisplayCode());
            displayDTO.setOriginalName( display.getName());
            displayDTO.setIdStop1(display.getIdStop1());
            displayDTO.setIdStop2(display.getIdStop2());
            displayDTO.setIdStop3(display.getIdStop3());
            displayDTO.setIdStop4(display.getIdStop4());

            if (displayDTO.getOriginalName() != null) { // Check if originalName is not null before using it
                displayDTO.setName(displayDTO.getOriginalName());
                // Find the index of the separator character ('>' in displayDTO case)
                int separatorIndex = displayDTO.getOriginalName().indexOf(">");
                if (separatorIndex >= 2) {
                    // Extract the station name as a substring of the input string, up until the separator character
                    displayDTO.setName( displayDTO.getOriginalName().substring(0, separatorIndex - 2).trim());

                    // Extract the characters before the separator (including whitespace)
                    String beforeSeparator = displayDTO.getOriginalName().substring(0, separatorIndex).trim();

                    // Check if the last character before the separator is 'A' or 'T', and set the type accordingly
                    char lastChar = beforeSeparator.charAt(beforeSeparator.length() - 1);
                    if (lastChar == 'A' || lastChar == 'T') {
                        displayDTO.setType( String.valueOf(lastChar));
                        // Check if the second to last character before the separator is 'A' or 'T', and add it to the type
                        if (beforeSeparator.length() >= 2) {
                            char secondLastChar = beforeSeparator.charAt(beforeSeparator.length() - 2);
                            if (secondLastChar == 'A' || secondLastChar == 'T') {
                                displayDTO.setType( String.valueOf(secondLastChar) + displayDTO.getType());
                            }
                        }
                    } else {
                        displayDTO.setType(null);
                    }

                    // Extract the destination (the substring after the separator character)
                    displayDTO.setDrivingDirection( displayDTO.getOriginalName().substring(separatorIndex + 1).trim());
                } else {
                    // If the separator character is not found or is in an invalid position, set the name, type, and destination to null
                    displayDTO.setType( null);
                    displayDTO.setDrivingDirection(null);
                }

                // Remove the trailing 'A' in the name, if it exists
                if (displayDTO.getName().endsWith("A")) {
                    displayDTO.setName( displayDTO.getName().substring(0, displayDTO.getName().length() - 1).trim());
                    displayDTO.setType( "TA" );
                }
            } else {
                // If originalName is null, set all the fields to null
                displayDTO.setName(null);
                displayDTO.setType( null);
                displayDTO.setDrivingDirection(  null);
            }
        }

            return displayDTO;
    }

}