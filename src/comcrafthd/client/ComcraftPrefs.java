/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd.client;

import comcrafthd.ComcraftGame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author quead
 */
public final class ComcraftPrefs {

    public static ComcraftPrefs instance = new ComcraftPrefs();

    public int chunkRenderDistance = 5;
    public boolean fogEnabled = false;

    private void saveWork(DataOutputStream out) throws IOException {
        out.writeInt(chunkRenderDistance);
        out.writeBoolean(fogEnabled);
    }

    private void loadWork(DataInputStream in) throws IOException {
        chunkRenderDistance = in.readInt();
        fogEnabled = in.readBoolean();
    }

    private static final String STORE_NAME = "prefs";

    public static void load() {
        try {
            loadImpl();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        try {
            saveImpl();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static RecordStore recordStore;

    private static void initRecordStore() throws RecordStoreException {
        if (recordStore == null) {
            recordStore = RecordStore.openRecordStore(STORE_NAME, true);
        }
    }

    private static void saveImpl() throws RecordStoreException, IOException {
        initRecordStore();

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);

        instance.saveWork(dataOutputStream);

        dataOutputStream.flush();

        byte[] recordOut = byteOutputStream.toByteArray();

        if (recordStore.getNumRecords() == 0) {
            recordStore.addRecord(recordOut, 0, 0);
        }

        recordStore.setRecord(1, recordOut, 0, recordOut.length);

        dataOutputStream.close();
        byteOutputStream.close();
    }

    private static void loadImpl() throws RecordStoreException, IOException {
        initRecordStore();

        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(recordStore.getRecord(1));
        DataInputStream dataInputStream = new DataInputStream(byteInputStream);

        instance.loadWork(dataInputStream);

        dataInputStream.close();
        byteInputStream.close();
    }

}
