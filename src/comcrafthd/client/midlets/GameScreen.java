package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;

public class GameScreen extends ScreenBase {
    
    private Command exitCommand;
    
    public GameScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
        exitCommand = new Command("Exit", Command.EXIT, 0);
    }
    
    protected Displayable createDisplayable() {
        GameCanvas gameCanvas = new GameCanvas();
        gameCanvas.setTitle("ComcraftHD");
        gameCanvas.addCommand(exitCommand);
        gameCanvas.setFullScreenMode(true);
        return gameCanvas;
    }
    
    protected void handleCommand(Command command, Displayable displayable) {
        if (command == exitCommand) {
            midlet.stopGame();
            MainMenuScreen mainMenu = new MainMenuScreen(midlet, screenManager);
            mainMenu.show();
        }
    }
}