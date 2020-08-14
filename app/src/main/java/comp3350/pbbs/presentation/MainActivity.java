package comp3350.pbbs.presentation;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.presentation.addObject.AddTransaction;
import comp3350.pbbs.presentation.addObject.AddCard;
import comp3350.pbbs.presentation.addObject.AddBudgetCategory;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * MainActivity
 * Group4
 * PBBS
 * <p>
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
			if (Objects.requireNonNull(destination.getLabel()).equals("fragment_main_home")) {
				addObjectFAB.setVisibility(View.GONE);
				addObjectFAB.setOnClickListener(null);
			} else if (destination.getLabel().equals("fragment_main_transactions")) {
				addObjectFAB.setVisibility(View.VISIBLE);
				addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), AddTransaction.class), 1));
			} else if (destination.getLabel().equals("fragment_main_cards")) {
				addObjectFAB.setVisibility(View.VISIBLE);
				addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), AddCard.class), 2));
			} else if (destination.getLabel().equals("fragment_main_budget")) {
				addObjectFAB.setVisibility(View.VISIBLE);
				addObjectFAB.setOnClickListener(view -> startActivityForResult(new Intent(view.getContext(), AddBudgetCategory.class), 3));
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		NavigationUI.setupWithNavController(
				(BottomNavigationView) findViewById(R.id.bottomNavigationView),
				Navigation.findNavController(this, R.id.fragment));

		Fragment f = getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments().get(0);
		f.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 1) {
			String text = "";
			if (requestCode == 1) {
				text += "Transaction ";
			} else if (requestCode == 3) {
				text += "Budget ";
			} else if (requestCode == 2) {
				text += "Card ";
			}

			Toast.makeText(getApplicationContext(), text + "added!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Main.shutDown();
	}
}