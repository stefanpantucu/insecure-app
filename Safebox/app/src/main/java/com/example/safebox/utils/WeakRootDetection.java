package com.example.safebox.utils;

import java.io.File;

public class WeakRootDetection {
    // Intentionally insecure: Simple and bypassable root detection
    public static boolean isDeviceRooted() {
        // Check for su binary
        String[] paths = {"/system/bin/su", "/system/xbin/su"};
        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        
        // Intentionally weak: Only checking build tags
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        return false; // Intentionally insecure: Easy to bypass
    }

    // Intentionally insecure: Can be easily hooked or modified
    public static void preventRootedExecution() {
        if (isDeviceRooted()) {
            // Intentionally weak: Just throwing an exception that can be caught
            throw new SecurityException("Root detected!");
        }
    }
} 