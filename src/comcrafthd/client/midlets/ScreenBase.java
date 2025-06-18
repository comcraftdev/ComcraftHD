package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;

public abstract class ScreenBase implements CommandListener {

    protected final ComcraftMIDlet midlet;
    protected final ScreenManager screenManager;
    protected Command backCommand;
    private Displayable displayable;

    public ScreenBase(ComcraftMIDlet midlet, ScreenManager screenManager) {
        this.midlet = midlet;
        this.screenManager = screenManager;
        this.backCommand = new Command("Back", Command.BACK, 0);
    }

    protected abstract Displayable createDisplayable();

    public final Displayable getDisplayable() {
        if (displayable == null) {
            displayable = createDisplayable();
        }
        return displayable;
    }

    public void show() {
        Displayable displayable = getDisplayable();
        displayable.setCommandListener(this);
        screenManager.switchScreen(displayable);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == backCommand) {
            screenManager.switchToPrevious();
        } else {
            handleCommand(command, displayable);
        }
    }

    protected abstract void handleCommand(Command command, Displayable displayable);
}
