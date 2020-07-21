package comp3350.pbbs.presentation;

import android.content.Intent;
//import android.os.Build;
import android.os.Bundle;
//import android.provider.Settings;
//import android.widget.Button;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.biometric.BiometricPrompt;
//import androidx.core.content.ContextCompat;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.AccessUser;


/**
 * Used to be utilizing android lock screen credential to secure the app,
 * now it's only a dummy entry activity used as a junction for asking user nickname on first launch.
 */
public class Auth extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        Main.startup();
        if (new AccessUser().getUsername() == null)  // first launch
        {
            this.startActivity(new Intent(this, firstTimeGreeting.class));
        }
        else
        {
            this.startActivity(new Intent(this, MainActivity.class));
        }
        finish(); // done with authentication


        // damn you marshmallow!
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
//        {
//            showMarshmallowNoCredentialDialogue();
//        }
//
//        newBiometricPrompt().authenticate(newPromptInfo());
//
//        Button authButton = findViewById(R.id.AuthenticateButton);
//        authButton.setOnClickListener(view -> newBiometricPrompt().authenticate(newPromptInfo()));
    }

//    /**
//     * treat it like a black box.
//     * @return a new BiometricPrompt.PromptInfo object
//     */
//    private BiometricPrompt.PromptInfo newPromptInfo()
//    {
//        return new BiometricPrompt.PromptInfo.Builder()
//                // Title and subtitle need to be changed.
//                    .setTitle("Biometric login for my app")
//                    .setSubtitle("Log in using your lock screen credential")
//                    .setDeviceCredentialAllowed(true)
//                    .build();
//    }
//
//    /**
//     * treat it like a black box.
//     * @return a new BiometricPrompt object
//     */
//    private BiometricPrompt newBiometricPrompt()
//    {
//        //  ERROR_NO_DEVICE_CREDENTIAL
//
//        return new BiometricPrompt(
//                Auth.this,
//                ContextCompat.getMainExecutor(this),
//                new BiometricPrompt.AuthenticationCallback()
//                {
//                    /**
//                     * What happens if the user pressed 'back' button?
//                     * Expected: the user will not get in
//                     */
//                    @Override
//                    public void onAuthenticationError(int errorCode,
//                                                      @NonNull CharSequence errString)
//                    {
//                        super.onAuthenticationError(errorCode, errString);
////                        Toast.makeText(getApplicationContext(),
////                                       "Authentication error: " + errorCode+ errString, Toast.LENGTH_SHORT
////                        ).show();
//
//                        if (errorCode == BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL)
//                        {
//                            showNoCredentialDialogue();
//                        }
//                    }
//
//                    /**
//                     * what happens if credential matched
//                     */
//                    @Override
//                    public void onAuthenticationSucceeded(
//                            @NonNull BiometricPrompt.AuthenticationResult result)
//                    {
//                        super.onAuthenticationSucceeded(result);
////                        Toast.makeText(getApplicationContext(),
////                                       "Authentication succeeded!", Toast.LENGTH_SHORT
////                        ).show();
//                        toMainActivity();
//                    }
//                }
//        );
//    }

//    private void toMainActivity()
//    {
//        Main.startup();
//        if (new AccessUser().getUsername() == null)  // first launch
//        {
//            this.startActivity(new Intent(this, firstTimeGreeting.class));
//        }
//        else
//        {
//            this.startActivity(new Intent(this, MainActivity.class));
//        }
//        finish(); // done with authentication
//    }

//    private void showNoCredentialDialogue()
//    {
//        new AlertDialog.Builder(this)
//                .setTitle("Lock screen password required")
//                .setMessage("This app requires a lock screen password to secure your data.\nPlease go to Settings app to setup a lock screen password.")
//                .setPositiveButton("Go to Settings", (dialogInterface, i) -> this.startActivity(new Intent(Settings.ACTION_SETTINGS)))
//                .setNegativeButton("Quit", ((dialogInterface, i) -> finish()))
//                .show();
//    }
//
//    private void showMarshmallowNoCredentialDialogue()
//    {
//        new AlertDialog.Builder(this)
//                .setTitle("Lock screen password required")
//                .setMessage("This app requires a lock screen password to work properly.\nPlease make sure you have a lock screen password set.")
//                .setPositiveButton("Go to Settings", (dialogInterface, i) -> this.startActivity(new Intent(Settings.ACTION_SETTINGS)))
//                .setNegativeButton("Continue", null)
//                .show();
//    }
}
