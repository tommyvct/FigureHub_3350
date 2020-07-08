package comp3350.pbbs.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.presentation.addObject.addTransaction;
import comp3350.pbbs.presentation.addObject.addCard;
import comp3350.pbbs.presentation.addObject.addBudgetCategory;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    FloatingActionButton addObjectFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
               addObjectFAB.setOnClickListener(view -> startActivity(new Intent(view.getContext(), addTransaction.class)));
            }
            else if (destination.getLabel().equals("fragment_main_cards"))
            {
               addObjectFAB.setVisibility(View.VISIBLE);
               addObjectFAB.setOnClickListener(view -> startActivity(new Intent(view.getContext(), addCard.class)));
            }
            else if (destination.getLabel().equals("fragment_main_budget"))
            {
               addObjectFAB.setVisibility(View.VISIBLE);
               addObjectFAB.setOnClickListener(view -> startActivity(new Intent(view.getContext(), addBudgetCategory.class)));
            }

//            Toast.makeText(MainActivity.this, ""+destination.getLabel(), Toast.LENGTH_LONG).show();
        });

    }
}