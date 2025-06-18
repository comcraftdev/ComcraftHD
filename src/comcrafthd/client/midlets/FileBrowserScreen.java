package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.pda.FileBrowser;

public class FileBrowserScreen extends ScreenBase {

    public FileBrowserScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
    }

    protected Displayable createDisplayable() {
        FileBrowser fileBrowser = new FileBrowser(screenManager.getDisplay());
        fileBrowser.setTitle("Select world save location");
        fileBrowser.addCommand(FileBrowser.SELECT_FILE_COMMAND);
        fileBrowser.addCommand(backCommand);
        return fileBrowser;
    }

    protected void handleCommand(Command command, Displayable displayable) {
        if (command == FileBrowser.SELECT_FILE_COMMAND) {
            // Handle file selection
        }
    }
}
