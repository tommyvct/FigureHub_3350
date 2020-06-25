package comp3350.pbbs.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import comp3350.pbbs.R;

import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

//change practice
public class Auth extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }


    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Auth.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                // TODO: move to next activity
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
//                .setNegativeButtonText("Use account password")
                .setDeviceCredentialAllowed(true)
                .build();


        biometricPrompt.authenticate(promptInfo);

//         Prompt appears when user clicks "Log in".
//         Consider integrating with the keystore to unlock cryptographic operations,
//         if needed by your app.
//        Button biometricLoginButton = findViewById(R.id.biometric_login);
//        biometricLoginButton.setOnClickListener(view -> {
//        });

        Button authButton = findViewById(R.id.AuthenticateButton);
        authButton.setOnClickListener(view ->
        {
            biometricPrompt.authenticate(promptInfo);
        });
    }

}
