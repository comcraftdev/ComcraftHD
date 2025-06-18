package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;
import comcrafthd.*;
import comcrafthd.client.*;

public class GameScreen extends ScreenBase implements CanvasVisibilityListener {

    private Command exitCommand;
    private ComcraftGame game;
    private ComcraftGameThread gameThread;

    public GameScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
        exitCommand = new Command("Exit", Command.EXIT, 0);
    }

    protected Displayable createDisplayable() {
        ComcraftGameConfiguration gameConfiguration = new ComcraftGameConfiguration();

        GameCanvas gameCanvas = new GameCanvas(this);
        gameCanvas.setTitle("ComcraftHD");
        gameCanvas.addCommand(exitCommand);
        gameCanvas.setFullScreenMode(true);

        ComcraftRenderer renderer = new ComcraftRenderer(gameCanvas);
        game = new ComcraftGame(gameConfiguration, renderer);
        gameThread = new ComcraftGameThread(game);

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
        if (game != null) {
            game.stop();
            game.clear();
            game = null;
        }
    }

    public void onCanvasShown() {
        if (gameThread != null) {
            gameThread.resume();
        }
    }

    public void onCanvasHidden() {
        if (gameThread != null) {
            gameThread.pause();
        }
    }
}
