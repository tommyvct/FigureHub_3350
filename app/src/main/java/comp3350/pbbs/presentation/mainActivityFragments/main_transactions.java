package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.presentation.addObject.addTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link main_transactions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_transactions extends Fragment
{
    private AccessTransaction accessTransaction;
    private String[] displayItems;
    private ListView transactionList;
    private ArrayAdapter<String> listAdapter;

    public main_transactions()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment main_transactions.
     */
    public static main_transactions newInstance()
    {
        main_transactions fragment = new main_transactions();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_transactions, container, false);

        // Floating add transaction button
        FloatingActionButton fab = view.findViewById(R.id.addTransFAB);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(view.getContext(), addTransaction.class));
                listAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        accessTransaction.getFormattedTransactionList()
                );
                transactionList.setAdapter(listAdapter);
            }
        });

        // List view
        accessTransaction = new AccessTransaction();
        transactionList = view.findViewById(R.id.transactionList);
        listAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                accessTransaction.getFormattedTransactionList()
        );
        transactionList.setAdapter(listAdapter);

        return view;
    }
}