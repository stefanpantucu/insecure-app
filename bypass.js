Java.perform(function() {
    console.log("[+] Frida script loaded");
    
    // Target the WeakRootDetection class
    var RootDetection = Java.use("com.example.safebox.utils.WeakRootDetection");
    
    // Hook the isDeviceRooted method
    RootDetection.isDeviceRooted.implementation = function() {
        console.log("[+] Root Detection Bypassed!");
        return false;
    };
}); 