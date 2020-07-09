package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.Transaction;

/**
 * main_transaction
 * Group4
 * PBBS
 *
 * This fragment displays all transactions.
 */
public class main_transactions extends Fragment {
    private AccessTransaction accessTransaction;
    private ListView transactionList;
    private ArrayAdapter<Transaction> listAdapter;

    // Required empty public constructor
    public main_transactions() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_transactions, container, false);

        // List view
        accessTransaction = new AccessTransaction();
        transactionList = view.findViewById(R.id.transactionList);
        listAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                accessTransaction.retrieveTransactions()
        );

        transactionList.setAdapter(listAdapter);

        return view;
    }

    /**
     * This method updates the list after adding.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        listAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                accessTransaction.retrieveTransactions()
        );
        transactionList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}