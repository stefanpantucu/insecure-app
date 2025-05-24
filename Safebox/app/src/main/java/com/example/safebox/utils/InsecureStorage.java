package com.example.safebox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import android.util.Base64;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class InsecureStorage {
    private static final String PREF_NAME = "vault_prefs";
    private static final String PIN_KEY = "pin";
    private final Context context;
    private final SharedPreferences prefs;

    public InsecureStorage(Context context) {
        this.context = context;
        // Intentionally insecure: Using MODE_WORLD_READABLE equivalent by storing in public file
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // Generate and store random PIN if not already set
        if (getPin() == null) {
            generateAndStoreRandomPin();
        }
    }

    // Generate a random 4-digit PIN
    private String generateRandomPin() {
        Random random = new Random();
        int pin = random.nextInt(9000) + 1000; // Generates number between 1000 and 9999
        return String.valueOf(pin);
    }

    private void generateAndStoreRandomPin() {
        String randomPin = generateRandomPin();
        try {
            // Store PIN in a public file with simple base64 encoding
            File pinFile = new File(context.getExternalFilesDir(null), "vault_pin.txt");
            
            // Create the content with proper line endings
            StringBuilder content = new StringBuilder();
            content.append("# This file contains sensitive data\n");
            content.append("# DO NOT MODIFY OR DELETE\n");
            content.append("# Format: Base64(VAULT_ACCESS_CODE:####:END)\n");
            
            // Create the encoded string
            String rawData = "VAULT_ACCESS_CODE:" + randomPin + ":END";
            String encodedPin = Base64.encodeToString(rawData.getBytes(), Base64.NO_WRAP);
            content.append(encodedPin).append("\n");

            // Write the file using UTF-8 encoding and proper line endings
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(pinFile), "UTF-8")
            );
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to SharedPreferences
            prefs.edit().putString(PIN_KEY, randomPin).apply();
        }
    }

    public String getPin() {
        try {
            // Read PIN from public file
            File pinFile = new File(context.getExternalFilesDir(null), "vault_pin.txt");
            if (!pinFile.exists()) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new FileReader(pinFile));
            String line;
            String encodedPin = null;
            // Skip comment lines
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    encodedPin = line.trim();
                    break;
                }
            }
            reader.close();
            
            if (encodedPin == null || encodedPin.isEmpty()) {
                return null;
            }

            // Decode and extract PIN
            String decoded = new String(Base64.decode(encodedPin, Base64.NO_WRAP));
            // Extract PIN from format: VAULT_ACCESS_CODE:####:END
            String[] parts = decoded.split(":");
            if (parts.length == 3 && parts[1].length() == 4) {
                return parts[1];
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to SharedPreferences
            return prefs.getString(PIN_KEY, null);
        }
    }

    // Intentionally insecure: Simple string comparison
    public boolean verifyPin(String inputPin) {
        String storedPin = getPin();
        if (storedPin == null) {
            return false;
        }
        return inputPin.equals(storedPin);
    }

    // Intentionally insecure: Exposing SharedPreferences
    public SharedPreferences getSharedPreferences() {
        return prefs;
    }
} 