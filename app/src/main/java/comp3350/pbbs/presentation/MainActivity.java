package comp3350.pbbs.presentation;

import java.util.Objects;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import comp3350.pbbs.R;
import comp3350.pbbs.presentation.addObject.addTransaction;
import comp3350.pbbs.presentation.addObject.addCard;
import comp3350.pbbs.presentation.addObject.addBudgetCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This class implements the main page
 */
public class MainActivity extends AppCompatActivity {
    FloatingActionButton addObjectFAB;

    /**
     * This method initiates the bottom navigation view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        NavController nc = Navigation.findNavController(this, R.id.fragment);
        addObjectFAB = findViewById(R.id.addObjectFAB);

        NavigationUI.setupWithNavController(bnv, nc);


        nc.addOnDestinationChangedListener((controller, destination, arguments) ->
        {
            if (Objects.requireNonNull(destination.getLabel()).equals("fragment_main_home"))
            {
               addObjectFAB.setVisibility(View.GONE);
               addObjectFAB.setOnClickListener(null);
            }
            else if (destination.getLabel().equals("fragment_main_transactions"))
            {
               addObjectFAB.setVisibility(View.VISIBLE);
               addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), addTransaction.class), 1));
            }
            else if (destination.getLabel().equals("fragment_main_cards"))
            {
               addObjectFAB.setVisibility(View.VISIBLE);
               addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), addCard.class), 2));
            }
            else if (destination.getLabel().equals("fragment_main_budget"))
            {
               addObjectFAB.setVisibility(View.VISIBLE);
               addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), addBudgetCategory.class), 3));
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        NavigationUI.setupWithNavController(
            (BottomNavigationView) findViewById(R.id.bottomNavigationView),
            Navigation.findNavController(this, R.id.fragment));
    }
}