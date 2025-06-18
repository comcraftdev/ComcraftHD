package comcrafthd.client.midlets;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public final class ComcraftMIDlet extends MIDlet {

    public static ComcraftMIDlet instance;
    private boolean midletPaused = false;
    private ScreenManager screenManager;

    public ComcraftMIDlet() {
        instance = this;
    }

    private void initialize() {
        screenManager = new ScreenManager(Display.getDisplay(this));
    }

    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    public void pauseApp() {
        midletPaused = true;
    }

    public void destroyApp(boolean unconditional) {
        // Cleanup handled by screens
    }

    private void startMIDlet() {
        SplashScreen splash = new SplashScreen(this, screenManager);
        splash.show();
    }

    private void resumeMIDlet() {
        // Resume logic if needed
    }

    public void exitMIDlet() {
        destroyApp(true);
        notifyDestroyed();
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
