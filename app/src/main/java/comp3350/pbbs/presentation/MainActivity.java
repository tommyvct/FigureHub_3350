package comp3350.pbbs.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp3350.pbbs.R;

/**
 * This class implements the main page
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method initiates the bottom navigation view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        NavController nc = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(bnv, nc);
    }
}