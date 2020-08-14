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

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.AccessUser;
import comp3350.pbbs.business.BudgetCategoryTransactionLinker;
import comp3350.pbbs.business.NotificationObservable;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.presentation.FirstTimeGreeting;
import comp3350.pbbs.presentation.NotificationObserver;

/**
 * MainHome
 * Group4
 * PBBS
 * <p>
 * This fragment displays a summary of budgets, balances on credit cards and recent transactions
 * not implemented in Iteration 1.
 * <p>
 * Implements NotificationObserver to get updates when a budget category has been updated.
 */
public class MainHome extends Fragment implements NotificationObserver {
	private NotificationObservable observable;
	private DataAccessI dataAccess = DataAccessController.getDataAccess(Main.dbName);
	private ArrayList<String> notifications = new ArrayList<>();
	private ArrayList<String> budgetNotifications = new ArrayList<>();
	private ArrayList<String> creditCardNotifications = new ArrayList<>();
	private BudgetCategoryTransactionLinker bctLinker = new BudgetCategoryTransactionLinker();
	private ArrayAdapter<String> listViewAdaptor;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		observable = NotificationObservable.getInstance();
		observable.attach(this);
		updateBudgetNotifications();
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

		// Print Notifications for Over-budget and credit cards
		updateCreditCardNotifications();
		notifications.addAll(budgetNotifications);
		notifications.addAll(creditCardNotifications);
		listView = view.findViewById(R.id.notificationList);
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

	/**
	 * Builds the list of notifications for budget categories that are about to go over limit (or have already)
	 **/
	@Override
	public void updateBudgetNotifications() {
		budgetNotifications.clear();
		for (BudgetCategory b : dataAccess.getBudgets()) {
			float bTotal = bctLinker.calculateBudgetCategoryTotal(b, Calendar.getInstance());
			if (bTotal > b.getBudgetLimit()) {
				budgetNotifications.add(String.format("You are over budget in %s: $%.2f / $%.2f ", b.getBudgetName(), bTotal, b.getBudgetLimit()));
			} else if (bTotal > b.getBudgetLimit() * 0.9) {
				budgetNotifications.add(String.format("You are about to go over budget in %s: $%.2f / $%.2f ", b.getBudgetName(), bTotal, b.getBudgetLimit()));
			}
		}
	}

	/**
	 * Builds the list of notifications for credit cards that are about to be due
	 **/
	private void updateCreditCardNotifications() {
		creditCardNotifications.clear();
		Calendar calendar = Calendar.getInstance();
		int currDate = calendar.get(Calendar.DAY_OF_MONTH);
		for (Card c : dataAccess.getCreditCards()) {
			if (currDate == c.getPayDate() && c.isActive()) {
				creditCardNotifications.add(String.format("Credit card %s is due for payment", c.getCardName()));
			}
		}
	}
}