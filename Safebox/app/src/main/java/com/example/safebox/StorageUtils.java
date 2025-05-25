package com.example.safebox;

import android.content.Context;
import java.io.*;

public class StorageUtils {
    public static void saveToFile(Context context, String filename, String data) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(Context context, String filename) {
        try (FileInputStream fis = context.openFileInput(filename)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            return "No data found.";
        }
    }
}