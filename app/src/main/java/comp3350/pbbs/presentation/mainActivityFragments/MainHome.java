package comp3350.pbbs.presentation.mainActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessUser;
import comp3350.pbbs.presentation.FirstTimeGreeting;

/**
 * main_home
 * Group4
 * PBBS
 *
 * This fragment displays a summary of budgets, balances on credit cards and recent transactions
 * not implemented in Iteration 1.
 */
public class MainHome extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        ((TextView) view.findViewById(R.id.textView3)).setText("Hello, " + new AccessUser().getUsername() + "!");

        view.findViewById(R.id.changeUserNameButton).setOnClickListener(view1 ->
        {
            Intent intent = new Intent(view1.getContext(), FirstTimeGreeting.class);
            intent.putExtra("a", "a");
            startActivityForResult(intent, 0);
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ((TextView) requireView().findViewById(R.id.textView3)).setText("Hello, " + new AccessUser().getUsername() + "!");
    }
}