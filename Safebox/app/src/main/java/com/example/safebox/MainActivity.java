package com.example.safebox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.example.safebox.utils.InsecureStorage;
import com.example.safebox.utils.WeakRootDetection;

public class MainActivity extends AppCompatActivity {
    private InsecureStorage storage;
    private EditText pinInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check for root before showing any UI
        if (WeakRootDetection.isDeviceRooted()) {
            Intent intent = new Intent(this, RootDetectedActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        storage = new InsecureStorage(this);

        pinInput = findViewById(R.id.pinInput);
        Button unlockButton = findViewById(R.id.unlockButton);
        Button hintButton = findViewById(R.id.hintButton);

        // Intentionally insecure: Check for backdoor intent
        if (getIntent().getAction() != null &&
            getIntent().getAction().equals("com.example.safebox.BACKDOOR")) {
            startVaultActivity();
            return;
        }

        unlockButton.setOnClickListener(v -> verifyPin());
        hintButton.setOnClickListener(v -> showHint());
    }

    private void showHint() {
        new AlertDialog.Builder(this)
            .setTitle("PIN Hint")
            .setMessage("Check the app's external storage for a file named vault_pin.txt")
            .setPositiveButton("Got it", null)
            .show();
    }

    private void verifyPin() {
        String pin = pinInput.getText().toString();
        // Intentionally insecure: Direct string comparison
        if (storage.verifyPin(pin)) {
            startVaultActivity();
        } else {
            Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show();
        }
    }

    private void startVaultActivity() {
        Intent intent = new Intent(this, Challenge3.class);
        startActivity(intent);
        finish();
    }
}