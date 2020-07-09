package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.presentation.addObject.addBudgetCategory;

/**
 * main_budget
 * Group4
 * PBBS
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link main_budget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_budget extends Fragment {
    private AccessBudgetCategory accessBudgetCategory;
    private ArrayList<BudgetCategory> budgetCategoryList;
    private ArrayAdapter<BudgetCategory> listViewAdaptor;
    private ListView listView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Required empty public constructor
    public main_budget() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main_budget.
     */
    // TODO: Rename and change types and number of parameters
    public static main_budget newInstance(String param1, String param2) {
        main_budget fragment = new main_budget();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessBudgetCategory = new AccessBudgetCategory();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_budget, container, false);
        listView = view.findViewById(R.id.listBudgets);

        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
        listViewAdaptor = new ArrayAdapter<BudgetCategory>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                budgetCategoryList
        );

        listView.setAdapter(listViewAdaptor);

        FloatingActionButton fab = view.findViewById(R.id.addBudgFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(), addBudgetCategory.class), 1);
            }
        });

        return view;
    }

    /**
     * This method updates the list after adding.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
        listViewAdaptor = new ArrayAdapter<BudgetCategory>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                budgetCategoryList
        );

        listView.setAdapter(listViewAdaptor);
        listViewAdaptor.notifyDataSetChanged();
    }
}