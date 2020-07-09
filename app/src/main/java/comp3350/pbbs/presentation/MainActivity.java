package comp3350.pbbs.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private AccessBudgetCategory accessBudgetCategory;
    private ArrayList<BudgetCategory> budgetCategoryList;
    private ArrayAdapter<String> listViewAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessBudgetCategory = new AccessBudgetCategory();
        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
        ArrayList<String> budgetTotals = new ArrayList<String>();
        for(int i = 0; i < budgetCategoryList.size(); i++){
            BudgetCategory currentBudgetCat = budgetCategoryList.get(i);
            budgetTotals.add("Category: " + currentBudgetCat.getBudgetName() + " Amount Spent: $" +
                    accessBudgetCategory.calculateBudgetCategoryTotal(currentBudgetCat) + " / $" + currentBudgetCat.getBudgetLimit());
        }
        listViewAdaptor = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                budgetTotals);
        final ListView listView = (ListView)findViewById(R.id.listBudgetTotals);
        listView.setAdapter(listViewAdaptor);

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        NavController nc = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(bnv, nc);
    }
}