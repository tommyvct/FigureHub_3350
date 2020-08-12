package comp3350.pbbs.acceptance;

import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.Espresso;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.transition.Transition;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import comp3350.pbbs.R;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.presentation.Auth;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AcceptanceTests {

    @Rule
    public ActivityTestRule<Auth> auth = new ActivityTestRule<>(Auth.class);

//    @Test
//    public void testAfirstActivity(){
//        //REINSTALL THE APP EVERY TIME YOU RUN THE ACCEPTANCE TEST
//        //otherwise, this test will catch a NoMatchingViewException as the first log in page will be changed
//        try {
//            onView(withId(R.id.textView5)).check(matches(isDisplayed()));
//            onView(withId(R.id.textBox)).perform(typeText("Aziz"));
//            Espresso.closeSoftKeyboard();
//            onView(withText("CONTINUE")).perform(click());
//            onView(withText("Hello, Aziz!")).check(matches(isDisplayed()));
//            onView(withText("CHANGE NAME")).check(matches(isDisplayed()));
//        }
//        catch (NoMatchingViewException n){
//            n.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void testBudgetCategories() {
//        //TODO: ADD, UPDATE AND INVALID CHECKS
//        //testing the add function
//        onView(withId(R.id.main_budget)).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.addObjectFAB)).perform(click());
//        onView(withId(R.id.editBudgetName)).perform(typeText("NewBudget"));
//        onView(withId(R.id.editBudgetLimit)).perform(typeText("100.00"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.addBudgetSubmit)).perform(click());
//        Espresso.pressBack();
//
//        //testing the update function
//        //onView(withText("Hello, Aziz!")).check(matches(isDisplayed()));
//        onView(withId(R.id.main_budget)).perform(click());
//        onView(withText("NewBudget\nLimit: $100.00")).check(matches(isDisplayed())).perform(click());
//        onView(withText("UPDATE")).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.editBudgetName)).perform(clearText(), typeText("UpdatedBudget"));
//        onView(withId(R.id.editBudgetLimit)).perform(clearText(), typeText("50.00"));
//        Espresso.closeSoftKeyboard();
//        onView(withText("UPDATE")).perform(click());
//
////
////
//////        //testing with invalid values
//////        onView(withText("Hello, Aziz!")).check(matches(isDisplayed()));
//////        onView(withId(R.id.main_budget)).perform(click());
//////        onView(withText("UpdatedBudget\nLimit: $50.00")).check(matches(isDisplayed())).perform(clearText());
//////        onView(withText("UPDATE")).perform(click());
//////        onView(withId(R.id.editBudgetName)).perform(typeText(null));
//////        onView(withText("UPDATE")).perform(click());
//////        onView(withId(R.id.editBudgetName)).perform(typeText(" "));
//////        onView(withText("UPDATE")).perform(click());
//////        onView(withId(R.id.editBudgetName)).perform(clearText(),typeText("00"));
//////        onView(withText("UPDATE")).perform(click());
//////        onView(withId(R.id.editBudgetName)).perform(clearText(),typeText("Must be a valid name"));
//////        onView(withId(R.id.editBudgetLimit)).perform(clearText(),typeText("80.00"));
//////        onView(withText("UPDATE")).perform(click());
////
//    }

//    @Test
//    public void testCard() {
//        //testing the add credit card
//        onView(withId(R.id.main_cards)).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.addObjectFAB)).perform(click());
//        onView(withId(R.id.cardName)).perform(typeText("NewCreditCard"));
//        onView(withId(R.id.cardNumber)).perform(typeText("123412341234"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.ValidThruMonth)).perform(typeText("12"));
//        onView(withId(R.id.validThruYear)).perform(typeText("22"));
//        onView(withId(R.id.cardholderName)).perform(typeText("Aziz"));
//        onView(withId(R.id.payDay)).perform(typeText("23"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.addCardSubmit)).perform(click());
//        Espresso.pressBack();
//
//        //testing add debit card
//        onView(withId(R.id.main_cards)).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.addObjectFAB)).perform(click());
//        onView(withId(R.id.addDebitRadioButton)).perform(click());
//        onView(withId(R.id.cardName)).perform(typeText("NewDebitCard"));
//        onView(withId(R.id.cardNumber)).perform(typeText("12312312333"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.ValidThruMonth)).perform(typeText("2"));
//        onView(withId(R.id.validThruYear)).perform(typeText("22"));
//        onView(withId(R.id.cardholderName)).perform(typeText("Aziz"));
//        onView(withId(R.id.addDebitDefaultBankAccountName)).perform(typeText("TD Bank"));
//        onView(withId(R.id.addDebitDefaultBankAccountNumber)).perform(typeText("654567"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.addCardSubmit)).perform(click());
//        Espresso.pressBack();

    //testing the update credit card
    //       onView(withId(R.id.main_cards)).perform(click());
//        onView(withSubstring("NewCredit")).check(matches(isDisplayed())).perform(click());
//        onView(withText("UPDATE CARD INFORMATION")).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.cardName)).perform(clearText(), typeText("UpdatedCreditCard"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.ValidThruMonth)).perform(clearText(), typeText("11"));
//        onView(withId(R.id.validThruYear)).perform(clearText(), typeText("2023"));
//        onView(withId(R.id.cardholderName)).perform(clearText(), typeText("Josh"));
//        onView(withId(R.id.payDay)).perform(clearText(), typeText("25"));
//        Espresso.closeSoftKeyboard();
//        onView(withText("UPDATE")).perform(click());
//        Espresso.pressBack();
//
//        //testing the update debit card
//        onView(withId(R.id.main_cards)).perform(click());
//        onView(withSubstring("NewDebit")).check(matches(isDisplayed())).perform(click());
//        onView(withText("UPDATE CARD INFORMATION")).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.cardName)).perform(clearText(), typeText("UpdatedDebitCard"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.ValidThruMonth)).perform(clearText(), typeText("10"));
//        onView(withId(R.id.validThruYear)).perform(clearText(), typeText("2023"));
//        onView(withId(R.id.cardholderName)).perform(clearText(), typeText("Josh"));
//        Espresso.closeSoftKeyboard();
//        onView(withText("UPDATE")).perform(click());
//        Espresso.pressBack();
//
//        //testing mark inactive
//        onView(withId(R.id.main_cards)).perform(click());
//        onData(anything()).atPosition(1).perform(click());
//        onView(withText("UPDATE CARD INFORMATION")).check(matches(isDisplayed())).perform(click());
//        onView(withText("MARK INACTIVE")).check(matches(isDisplayed())).perform(click());
//        Espresso.pressBack();
//
//    }

    @Test
    public void testTransactions() {
        //testing the add function
//        onView(withId(R.id.main_transactions)).check(matches(isDisplayed())).perform(click());
//        onView(withId(R.id.addObjectFAB)).perform(click());
//        onView(withId(R.id.addTransDescription)).perform(typeText("NewTransaction"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.dateInput)).perform(click());
//        onView(withText("OK")).perform(click());
//        onView(withId(R.id.timeInput)).perform(click());
//        onView(withText("OK")).perform(click());
//        onView(withId(R.id.addTransAmount)).check(matches(isDisplayed())).perform(typeText("20.00"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.cardSelector)).perform(click());
//        onData(anything()).atPosition(1).perform(click());
//        onView(withId(R.id.budgetSelector)).perform(click());
//        onData(anything()).atPosition(1).perform(click());
//        onView(withId(R.id.addTransSubmit)).perform(click());
//        Espresso.pressBack();

        //testing the update function
        onView(withId(R.id.main_transactions)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.addTransDescription)).perform(clearText(), typeText("UpdatedTransaction"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.dateInput)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.timeInput)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.addTransAmount)).check(matches(isDisplayed())).perform(clearText(), typeText("30.00"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.cardSelector)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.budgetSelector)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.updateTrans)).perform(click());
        Espresso.pressBack();

        //testing the delete transaction
        onView(withId(R.id.main_transactions)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withText("DELETE")).check(matches(isDisplayed())).perform(click());
        Espresso.pressBack();
    }
}
