package com.example.safebox;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.safebox.utils.WeakEncryption;
import android.content.SharedPreferences;

public class VaultActivity extends AppCompatActivity {
    private EditText secretInput;
    private TextView secretsView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        // Intentionally missing: Screenshot protection
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
        //                     WindowManager.LayoutParams.FLAG_SECURE);

        secretInput = findViewById(R.id.secretInput);
        secretsView = findViewById(R.id.secretsView);
        Button saveButton = findViewById(R.id.saveButton);

        // Intentionally insecure: Using MODE_PRIVATE instead of encrypted preferences
        prefs = getSharedPreferences("vault_secrets", MODE_PRIVATE);

        loadSecrets();

        saveButton.setOnClickListener(v -> saveSecret());
    }

    private void saveSecret() {
        String secret = secretInput.getText().toString();
        if (!secret.isEmpty()) {
            // Intentionally insecure: Weak encryption
            String encryptedSecret = WeakEncryption.encrypt(secret);
            
            // Intentionally insecure: Storing all secrets in one string
            String existingSecrets = prefs.getString("secrets", "");
            String newSecrets = existingSecrets + "\n" + encryptedSecret;
            
            prefs.edit().putString("secrets", newSecrets).apply();
            loadSecrets();
            secretInput.setText("");
        }
    }

    private void loadSecrets() {
        String encryptedSecrets = prefs.getString("secrets", "");
        StringBuilder decryptedSecrets = new StringBuilder();

        // Intentionally insecure: Simple string splitting
        for (String encryptedSecret : encryptedSecrets.split("\n")) {
            if (!encryptedSecret.isEmpty()) {
                // Intentionally insecure: Weak decryption
                String decryptedSecret = WeakEncryption.decrypt(encryptedSecret);
                decryptedSecrets.append(decryptedSecret).append("\n");
            }
        }

        secretsView.setText(decryptedSecrets.toString());
    }

    // Intentionally missing: Proper cleanup in onPause/onDestroy
}