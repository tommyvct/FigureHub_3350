package comp3350.pbbs.presentation.mainActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessUser;

/**
 * main_home
 * Group4
 * PBBS
 *
 * This fragment displays a summary of budgets, balances on credit cards and recent transactions
 * not implemented in Iteration 1.
 */
public class main_home extends Fragment
{
    public main_home() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        ((TextView) view.findViewById(R.id.textView3)).setText("Hello, " + new AccessUser().getUsername() + "!");

        return view;
    }
}