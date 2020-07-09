package comp3350.pbbs.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.presentation.addObject.addTransaction;
import comp3350.pbbs.presentation.addObject.addCard;
import comp3350.pbbs.presentation.addObject.addBudgetCategory;
import comp3350.pbbs.presentation.mainActivityFragments.main_budget;
import comp3350.pbbs.presentation.mainActivityFragments.main_cards;
import comp3350.pbbs.presentation.mainActivityFragments.main_transactions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

//            Toast.makeText(MainActivity.this, ""+destination.getLabel(), Toast.LENGTH_LONG).show();
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("herewref");
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = null;
        switch(requestCode){
            case 1:
                //TODO: This is returning null
                f = this.getSupportFragmentManager().findFragmentById(R.id.);

                System.out.println(f.getClass().getSimpleName());
                if(f instanceof main_transactions)
                {
                    System.out.println("there");

                    main_transactions transactionsFragment = (main_transactions)f;
                    transactionsFragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case 2:
                f = this.getSupportFragmentManager().findFragmentById(R.id.main_cards);
                if(f instanceof main_cards)
                {
                    main_cards cardsFragment = (main_cards)f;
                    cardsFragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case 3:
                f = this.getSupportFragmentManager().findFragmentById(R.id.main_budget);
                if(f instanceof main_budget)
                {
                    main_budget cardsFragment = (main_budget)f;
                    cardsFragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
        }
    }
}