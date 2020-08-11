package comp3350.pbbs.presentation.mainActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.AccessUser;
import comp3350.pbbs.business.BudgetCategoryTransactionLinker;
import comp3350.pbbs.business.NotificationObservable;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.presentation.FirstTimeGreeting;
import comp3350.pbbs.presentation.NotificationObserver;

/**
 * main_home
 * Group4
 * PBBS
 *
 * This fragment displays a summary of budgets, balances on credit cards and recent transactions
 * not implemented in Iteration 1.
 */
public class MainHome extends Fragment implements NotificationObserver {
    private NotificationObservable observable;
    private AccessBudgetCategory accessBudgetCategory = new AccessBudgetCategory();
    private ArrayList<String> notifications = new ArrayList<>();
    private BudgetCategoryTransactionLinker bctLinker = new BudgetCategoryTransactionLinker();
    private ArrayAdapter<String> listViewAdaptor;
    private ListView listView;

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

        //Notifications
        observable = NotificationObservable.getInstance();
        observable.attach(this);
        listView = view.findViewById(R.id.notificationList);
        updateNotifications();
        listViewAdaptor = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                notifications
        );
        listView.setAdapter(listViewAdaptor);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ((TextView) requireView().findViewById(R.id.textView3)).setText("Hello, " + new AccessUser().getUsername() + "!");
    }

    @Override
    public void updateNotifications() {
        notifications.clear();
        for(BudgetCategory b: accessBudgetCategory.getAllBudgetCategories()){
            float bTotal = bctLinker.calculateBudgetCategoryTotal(b, Calendar.getInstance());
            if (bTotal > b.getBudgetLimit()){
                notifications.add(String.format("You are over budget in %s: $%.2f / $%.2f ", b.getBudgetName(), bTotal, b.getBudgetLimit()));
            } else if (bTotal > b.getBudgetLimit() * 0.9){
                notifications.add(String.format("You are about to go over budget in %s: $%.2f / $%.2f ", b.getBudgetName(), bTotal, b.getBudgetLimit()));
            }
        }
    }
}