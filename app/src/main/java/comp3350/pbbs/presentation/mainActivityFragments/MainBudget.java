package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.presentation.viewObject.ViewBudgetCategory;

/**
 * main_budget
 * Group4
 * PBBS
 *
 * This fragment displays all budget category
 */
public class MainBudget extends Fragment {
    private AccessBudgetCategory accessBudgetCategory;
    private List<BudgetCategory> budgetCategoryList;
    private ArrayAdapter<BudgetCategory> listViewAdaptor;
    private ListView listView;


    // Required empty public constructor
    public MainBudget() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessBudgetCategory = new AccessBudgetCategory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_budget, container, false);

        listView = view.findViewById(R.id.listBudgets);
        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
        listViewAdaptor = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                budgetCategoryList
        );
        listView.setAdapter(listViewAdaptor);

        // display budget category detail
        listView.setOnItemClickListener((arg0, view1, position, arg3) ->
        {
            Intent viewBudget = new Intent(view1.getContext(), ViewBudgetCategory.class);
            viewBudget.putExtra("budgetCategory", budgetCategoryList.get(position));
            startActivityForResult(viewBudget, 0);
        });

        return view;
    }

    /**
     * This method updates the list after adding.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
        listViewAdaptor = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                budgetCategoryList
        );

        listView.setAdapter(listViewAdaptor);
        listViewAdaptor.notifyDataSetChanged();
    }
}