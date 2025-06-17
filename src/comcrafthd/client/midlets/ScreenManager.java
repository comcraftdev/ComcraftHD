package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;
import java.util.Hashtable;

public class ScreenManager {
    
    private final Display display;
    private final Hashtable previousDisplayables;
    
    public ScreenManager(Display display) {
        this.display = display;
        this.previousDisplayables = new Hashtable();
    }
    
    public void switchScreen(Alert alert, Displayable nextDisplayable) {
        Displayable currentDisplayable = display.getCurrent();
        if (currentDisplayable != null && nextDisplayable != null) {
            previousDisplayables.put(nextDisplayable, currentDisplayable);
        }
        
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
    }
    
    public void switchScreen(Displayable nextDisplayable) {
        switchScreen(null, nextDisplayable);
    }
    
    public void switchToPrevious() {
        Displayable currentDisplayable = display.getCurrent();
        if (currentDisplayable != null) {
            Displayable previousDisplayable = (Displayable) previousDisplayables.get(currentDisplayable);
            if (previousDisplayable != null) {
                switchScreen(null, previousDisplayable);
            }
        }
    }
    
    public Display getDisplay() {
        return display;
    }
}