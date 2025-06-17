package comcrafthd.client.midlets;

import javax.microedition.lcdui.*;
import comcrafthd.client.ComcraftPrefs;

public class SettingsScreen extends ScreenBase {
    
    private TextField renderDistanceField;
    private ChoiceGroup fogChoiceGroup;
    private Command saveCommand;
    private Command cancelCommand;
    
    public SettingsScreen(ComcraftMIDlet midlet, ScreenManager screenManager) {
        super(midlet, screenManager);
        saveCommand = new Command("Save", Command.OK, -1);
        cancelCommand = new Command("Cancel", Command.CANCEL, 0);
    }
    
    protected Displayable createDisplayable() {
        renderDistanceField = new TextField("Render distance", null, 2, TextField.NUMERIC);
        renderDistanceField.setLayout(ImageItem.LAYOUT_DEFAULT | Item.LAYOUT_EXPAND);
        
        fogChoiceGroup = new ChoiceGroup("Fog (experimental)", Choice.EXCLUSIVE);
        fogChoiceGroup.append("On", null);
        fogChoiceGroup.append("Off", null);
        fogChoiceGroup.setLayout(ImageItem.LAYOUT_DEFAULT | Item.LAYOUT_EXPAND);
        fogChoiceGroup.setSelectedFlags(new boolean[]{true, false});
        
        Form settingsForm = new Form("Settings", new Item[]{renderDistanceField, fogChoiceGroup});
        settingsForm.addCommand(saveCommand);
        settingsForm.addCommand(cancelCommand);
        
        loadSettings();
        return settingsForm;
    }
    
    protected void handleCommand(Command command, Displayable displayable) {
        if (command == cancelCommand) {
            screenManager.switchToPrevious();
        } else if (command == saveCommand) {
            saveSettings();
            screenManager.switchToPrevious();
        }
    }
    
    private void loadSettings() {
        ComcraftPrefs.load();
        final ComcraftPrefs prefs = ComcraftPrefs.instance;
        
        renderDistanceField.setString("" + prefs.chunkRenderDistance);
        fogChoiceGroup.setSelectedIndex(prefs.fogEnabled ? 0 : 1, true);
    }
    
    private void saveSettings() {
        final ComcraftPrefs prefs = ComcraftPrefs.instance;
        
        prefs.chunkRenderDistance = Integer.parseInt(renderDistanceField.getString());
        prefs.fogEnabled = fogChoiceGroup.getSelectedIndex() == 0;
        
        ComcraftPrefs.save();
    }
}