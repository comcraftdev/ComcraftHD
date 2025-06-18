package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;

public class MainMenuScreen extends ScreenBase {

    private Command exitCommand;
    private Command okCommand;

    public MainMenuScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
        exitCommand = new Command("Exit", Command.EXIT, 0);
        okCommand = new Command("Ok", Command.OK, 0);
    }

    protected Displayable createDisplayable() {
        List mainMenu = new List("ComcraftHD", Choice.IMPLICIT);
        mainMenu.append("Singleplayer", null);
        mainMenu.append("Settings", null);
        mainMenu.append("Info", null);
        mainMenu.addCommand(exitCommand);
        mainMenu.addCommand(okCommand);
        mainMenu.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);
        mainMenu.setSelectedFlags(new boolean[]{false, false, false});
        return mainMenu;
    }

    protected void handleCommand(Command command, Displayable displayable) {
        if (command == exitCommand) {
            midlet.exitMIDlet();
        } else if (command == List.SELECT_COMMAND || command == okCommand) {
            handleMenuAction();
        }
    }

    private void handleMenuAction() {
        List mainMenu = (List) getDisplayable();
        String selectedString = mainMenu.getString(mainMenu.getSelectedIndex());
        if (selectedString != null) {
            if (selectedString.equals("Singleplayer")) {
                GameScreen gameScreen = new GameScreen(midlet, screenManager);
                gameScreen.show();
            } else if (selectedString.equals("Settings")) {
                SettingsScreen settingsScreen = new SettingsScreen(midlet, screenManager);
                settingsScreen.show();
            } else if (selectedString.equals("Info")) {
                // Info screen to be implemented
            }
        }
    }
}
