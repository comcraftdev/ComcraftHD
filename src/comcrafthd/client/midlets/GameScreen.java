package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;
import comcrafthd.*;

public class GameScreen extends ScreenBase {
    
    private Command exitCommand;
    private ComcraftGameThread gameThread;
    
    public GameScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
        exitCommand = new Command("Exit", Command.EXIT, 0);
    }
    
    protected Displayable createDisplayable() {
        ComcraftGameConfiguration gameConfiguration = new ComcraftGameConfiguration();
        gameThread = new ComcraftGameThread(gameConfiguration);
        
        GameCanvas gameCanvas = new GameCanvas(gameThread);
        gameCanvas.setTitle("ComcraftHD");
        gameCanvas.addCommand(exitCommand);
        gameCanvas.setFullScreenMode(true);
        return gameCanvas;
    }
    
    public void show() {
        super.show();
        if (gameThread != null) {
            gameThread.start();
        }
    }
    
    protected void handleCommand(Command command, Displayable displayable) {
        if (command == exitCommand) {
            stopGame();
            MainMenuScreen mainMenu = new MainMenuScreen(midlet, screenManager);
            mainMenu.show();
        }
    }
    
    private void stopGame() {
        if (gameThread != null) {
            gameThread.stop();
            gameThread = null;
        }
    }
}