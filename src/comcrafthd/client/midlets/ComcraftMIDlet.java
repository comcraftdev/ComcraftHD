/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client.midlets;

import comcrafthd.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.SplashScreen;
import org.netbeans.microedition.lcdui.pda.FileBrowser;

/**
 * @author quead
 */
public final class ComcraftMIDlet extends MIDlet implements CommandListener {

    public static ComcraftMIDlet instance;

    private boolean midletPaused = false;

    private ComcraftGameThread currentGameThread;

//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private java.util.Hashtable __previousDisplayables = new java.util.Hashtable();
    private Command exitCommand;
    private Command okCommand;
    private Command exitCommand1;
    private Command backCommand;
    private Command okCommand1;
    private Command exitCommand2;
    private Command okCommand2;
    private FileBrowser fileBrowser;
    private SplashScreen splashScreen;
    private List MainMenu;
    private ComcraftMIDPCanvas comcraftMIDPCanvas;
//</editor-fold>//GEN-END:|fields|0|

    public ComcraftMIDlet() {
        instance = this;
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    /**
     * Switches a display to previous displayable of the current displayable.
     * The <code>display</code> instance is obtain from the
     * <code>getDisplay</code> method.
     */
    private void switchToPreviousDisplayable() {
        Displayable __currentDisplayable = getDisplay().getCurrent();
        if (__currentDisplayable != null) {
            Displayable __nextDisplayable = (Displayable) __previousDisplayables.get(__currentDisplayable);
            if (__nextDisplayable != null) {
                switchDisplayable(null, __nextDisplayable);
            }
        }
    }
//</editor-fold>//GEN-END:|methods|0|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initializes the application. It is called only once when the MIDlet is
     * started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {
//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
}//GEN-BEGIN:|0-initialize|2|
//</editor-fold>//GEN-END:|0-initialize|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {
//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
switchDisplayable(null, getSplashScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
}//GEN-BEGIN:|3-startMIDlet|2|
//</editor-fold>//GEN-END:|3-startMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {
//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
}//GEN-BEGIN:|4-resumeMIDlet|2|
//</editor-fold>//GEN-END:|4-resumeMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code>
     * instance is taken from <code>getDisplay</code> method. This method is
     * used by all actions in the design for switching displayable.
     *
     * @param alert the Alert which is temporarily set to the display; if
     * <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        Displayable __currentDisplayable = display.getCurrent();
        if (__currentDisplayable != null && nextDisplayable != null) {
            __previousDisplayables.put(nextDisplayable, __currentDisplayable);
        }
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
}//GEN-BEGIN:|5-switchDisplayable|2|
//</editor-fold>//GEN-END:|5-switchDisplayable|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a
     * particular displayable.
     *
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {
//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
if (displayable == MainMenu) {//GEN-BEGIN:|7-commandAction|1|46-preAction
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|1|46-preAction
                // write pre-action user code here
MainMenuAction();//GEN-LINE:|7-commandAction|2|46-postAction
                // write post-action user code here
} else if (command == exitCommand1) {//GEN-LINE:|7-commandAction|3|58-preAction
                // write pre-action user code here
exitMIDlet();//GEN-LINE:|7-commandAction|4|58-postAction
                // write post-action user code here
} else if (command == okCommand2) {//GEN-LINE:|7-commandAction|5|73-preAction
                // write pre-action user code here
MainMenuAction();//GEN-LINE:|7-commandAction|6|73-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|7|67-preAction
} else if (displayable == comcraftMIDPCanvas) {
    if (command == exitCommand2) {//GEN-END:|7-commandAction|7|67-preAction
        // write pre-action user code here
switchDisplayable(null, getMainMenu());//GEN-LINE:|7-commandAction|8|67-postAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|9|24-preAction
} else if (displayable == fileBrowser) {
    if (command == FileBrowser.SELECT_FILE_COMMAND) {//GEN-END:|7-commandAction|9|24-preAction
        // write pre-action user code here
//GEN-LINE:|7-commandAction|10|24-postAction
        // write post-action user code here
} else if (command == backCommand) {//GEN-LINE:|7-commandAction|11|61-preAction
        // write pre-action user code here
switchToPreviousDisplayable();//GEN-LINE:|7-commandAction|12|61-postAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|13|27-preAction
} else if (displayable == splashScreen) {
    if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|13|27-preAction
        // write pre-action user code here
switchDisplayable(null, getMainMenu());//GEN-LINE:|7-commandAction|14|27-postAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|15|7-postCommandAction
        }//GEN-END:|7-commandAction|15|7-postCommandAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|16|
//</editor-fold>//GEN-END:|7-commandAction|16|


//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initialized instance of exitCommand component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {
//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
}//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
//</editor-fold>//GEN-END:|18-getter|2|


//<editor-fold defaultstate="collapsed" desc=" Generated Getter: fileBrowser ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initialized instance of fileBrowser component.
     *
     * @return the initialized component instance
     */
    public FileBrowser getFileBrowser() {
        if (fileBrowser == null) {
//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
fileBrowser = new FileBrowser(getDisplay());//GEN-BEGIN:|22-getter|1|22-postInit
            fileBrowser.setTitle("Select world save location");
            fileBrowser.setCommandListener(this);
            fileBrowser.addCommand(FileBrowser.SELECT_FILE_COMMAND);
            fileBrowser.addCommand(getBackCommand());//GEN-END:|22-getter|1|22-postInit
            // write post-init user code here
}//GEN-BEGIN:|22-getter|2|
        return fileBrowser;
    }
//</editor-fold>//GEN-END:|22-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: splashScreen ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initialized instance of splashScreen component.
     *
     * @return the initialized component instance
     */
    public SplashScreen getSplashScreen() {
        if (splashScreen == null) {
//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
splashScreen = new SplashScreen(getDisplay());//GEN-BEGIN:|25-getter|1|25-postInit
            splashScreen.setTitle("splashScreen");
            splashScreen.setCommandListener(this);
            splashScreen.setFullScreenMode(true);
            splashScreen.setText("ComcraftHD 2021 Edition");
            splashScreen.setTimeout(1000);//GEN-END:|25-getter|1|25-postInit
            // write post-init user code here
}//GEN-BEGIN:|25-getter|2|
        return splashScreen;
    }
//</editor-fold>//GEN-END:|25-getter|2|


//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|39-getter|0|39-preInit
    /**
     * Returns an initialized instance of okCommand component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {
//GEN-END:|39-getter|0|39-preInit
            // write pre-init user code here
okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|39-getter|1|39-postInit
            // write post-init user code here
}//GEN-BEGIN:|39-getter|2|
        return okCommand;
    }
//</editor-fold>//GEN-END:|39-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: MainMenu ">//GEN-BEGIN:|45-getter|0|45-preInit
    /**
     * Returns an initialized instance of MainMenu component.
     *
     * @return the initialized component instance
     */
    public List getMainMenu() {
        if (MainMenu == null) {
//GEN-END:|45-getter|0|45-preInit
            // write pre-init user code here
MainMenu = new List("ComcraftHD", Choice.IMPLICIT);//GEN-BEGIN:|45-getter|1|45-postInit
            MainMenu.append("Singleplayer", null);
            MainMenu.append("Options", null);
            MainMenu.append("Info", null);
            MainMenu.addCommand(getExitCommand1());
            MainMenu.addCommand(getOkCommand2());
            MainMenu.setCommandListener(this);
            MainMenu.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);
            MainMenu.setSelectedFlags(new boolean[]{false, false, false});//GEN-END:|45-getter|1|45-postInit
            // write post-init user code here
}//GEN-BEGIN:|45-getter|2|
        return MainMenu;
    }
//</editor-fold>//GEN-END:|45-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: MainMenuAction ">//GEN-BEGIN:|45-action|0|45-preAction
    /**
     * Performs an action assigned to the selected list element in the MainMenu
     * component.
     */
    public void MainMenuAction() {
//GEN-END:|45-action|0|45-preAction
        // enter pre-action user code here
String __selectedString = getMainMenu().getString(getMainMenu().getSelectedIndex());//GEN-BEGIN:|45-action|1|48-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Singleplayer")) {//GEN-END:|45-action|1|48-preAction
                // write pre-action user code here
switchDisplayable(null, getComcraftMIDPCanvas());//GEN-LINE:|45-action|2|48-postAction
                // write post-action user code here
                startSingleplayerGame();
            } else if (__selectedString.equals("Options")) {//GEN-LINE:|45-action|3|51-preAction
                // write pre-action user code here
//GEN-LINE:|45-action|4|51-postAction
                // write post-action user code here
} else if (__selectedString.equals("Info")) {//GEN-LINE:|45-action|5|49-preAction
                // write pre-action user code here
//GEN-LINE:|45-action|6|49-postAction
                // write post-action user code here
}//GEN-BEGIN:|45-action|7|45-postAction
        }//GEN-END:|45-action|7|45-postAction
        // enter post-action user code here
}//GEN-BEGIN:|45-action|8|
//</editor-fold>//GEN-END:|45-action|8|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initialized instance of exitCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {
//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|57-getter|1|57-postInit
            // write post-init user code here
}//GEN-BEGIN:|57-getter|2|
        return exitCommand1;
    }
//</editor-fold>//GEN-END:|57-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|60-getter|0|60-preInit
    /**
     * Returns an initialized instance of backCommand component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {
//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
backCommand = new Command("Back", Command.BACK, 0);//GEN-LINE:|60-getter|1|60-postInit
            // write post-init user code here
}//GEN-BEGIN:|60-getter|2|
        return backCommand;
    }
//</editor-fold>//GEN-END:|60-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|63-getter|0|63-preInit
    /**
     * Returns an initialized instance of okCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {
//GEN-END:|63-getter|0|63-preInit
            // write pre-init user code here
okCommand1 = new Command("Ok", Command.OK, 0);//GEN-LINE:|63-getter|1|63-postInit
            // write post-init user code here
}//GEN-BEGIN:|63-getter|2|
        return okCommand1;
    }
//</editor-fold>//GEN-END:|63-getter|2|


//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|66-getter|0|66-preInit
    /**
     * Returns an initialized instance of exitCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {
//GEN-END:|66-getter|0|66-preInit
            // write pre-init user code here
exitCommand2 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|66-getter|1|66-postInit
            // write post-init user code here
}//GEN-BEGIN:|66-getter|2|
        return exitCommand2;
    }
//</editor-fold>//GEN-END:|66-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand2 ">//GEN-BEGIN:|72-getter|0|72-preInit
    /**
     * Returns an initialized instance of okCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand2() {
        if (okCommand2 == null) {
//GEN-END:|72-getter|0|72-preInit
            // write pre-init user code here
okCommand2 = new Command("Ok", Command.OK, 0);//GEN-LINE:|72-getter|1|72-postInit
            // write post-init user code here
}//GEN-BEGIN:|72-getter|2|
        return okCommand2;
    }
//</editor-fold>//GEN-END:|72-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: comcraftMIDPCanvas ">//GEN-BEGIN:|65-getter|0|65-preInit
    /**
     * Returns an initialized instance of comcraftMIDPCanvas component.
     *
     * @return the initialized component instance
     */
    public ComcraftMIDPCanvas getComcraftMIDPCanvas() {
        if (comcraftMIDPCanvas == null) {
//GEN-END:|65-getter|0|65-preInit
            // write pre-init user code here
comcraftMIDPCanvas = new ComcraftMIDPCanvas();//GEN-BEGIN:|65-getter|1|65-postInit
            comcraftMIDPCanvas.setTitle("comcraftMIDPCanvas");
            comcraftMIDPCanvas.addCommand(getExitCommand2());
            comcraftMIDPCanvas.setCommandListener(this);
            comcraftMIDPCanvas.setFullScreenMode(true);//GEN-END:|65-getter|1|65-postInit
            // write post-init user code here
}//GEN-BEGIN:|65-getter|2|
        return comcraftMIDPCanvas;
    }
//</editor-fold>//GEN-END:|65-getter|2|

    /**
     * Returns a display instance.
     *
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started. Checks whether the MIDlet have been
     * already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     *
     * @param unconditional if true, then the MIDlet has to be unconditionally
     * terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
        stopGame();
    }

    private void startGame(ComcraftGameConfiguration gameConfiguration) {
        stopGame();

        currentGameThread = new ComcraftGameThread(gameConfiguration);
        currentGameThread.start();
    }

    private void stopGame() {
        if (currentGameThread != null) {
            currentGameThread.stop();
            currentGameThread = null;
        }
    }

    private void startSingleplayerGame() {
        ComcraftGameConfiguration gameConfiguration = new ComcraftGameConfiguration();
        startGame(gameConfiguration);
    }

}
