Java.perform(function() {
    console.log("[+] Frida script loaded");
    
    // Hook the Native class
    var Native = Java.use("com.example.safebox.utils.Native");
    
    // Override the validateFlag method to always return true
    Native.validateFlag.implementation = function(flag) {
        console.log("[+] Native flag validation bypassed!");
        console.log("[*] Flag attempted: " + flag);
        return true;
    };
}); 