package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;

public class SplashScreen extends ScreenBase {

    public SplashScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
    }

    protected Displayable createDisplayable() {
        org.netbeans.microedition.lcdui.SplashScreen splashScreen = new org.netbeans.microedition.lcdui.SplashScreen(screenManager.getDisplay());
        splashScreen.setTitle("ComcraftHD");
        splashScreen.setFullScreenMode(true);
        splashScreen.setText("ComcraftHD 2021 Edition");
        splashScreen.setTimeout(1000);
        return splashScreen;
    }

    protected void handleCommand(Command command, Displayable displayable) {
        if (command == org.netbeans.microedition.lcdui.SplashScreen.DISMISS_COMMAND) {
            MainMenuScreen mainMenu = new MainMenuScreen(midlet, screenManager);
            mainMenu.show();
        }
    }
}
