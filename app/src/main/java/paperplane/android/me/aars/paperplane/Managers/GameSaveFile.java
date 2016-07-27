package paperplane.android.me.aars.paperplane.Managers;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * Created by Kristian on 17.04.2016.
 */
public class GameSaveFile {
    private String path;

    private Properties properties;
    private File file;

    private FileInputStream inputStream;
    private FileOutputStream outStream;

    public GameSaveFile(String path, Context context) {
        this.path = path;

        file = new File(context.getFilesDir(), path);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        properties = new Properties();
    }

    public void setData(String ID, String data) {
        openOutputStream();

        properties.setProperty(ID, data);

        try {
            properties.store(outStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeOutputStream();
    }

    public String getData(String ID) {
        openInputStream();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String temp = properties.getProperty(ID);

        closeInputStream();

        return temp;
    }

    private void openOutputStream() {
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e("IOExeption", "Error opening opening stream on GameSaveFile " + path);
            e.printStackTrace();
        }
    }

    private void closeOutputStream() {
        try {
            outStream.close();
        } catch (IOException e) {
            Log.e("IOExeption", "Error opening closing stream on GameSaveFile " + path);
            e.printStackTrace();
        }
    }

    private void openInputStream() {
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeInputStream() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
