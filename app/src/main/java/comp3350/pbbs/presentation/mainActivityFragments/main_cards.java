package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.presentation.ViewCard;

/**
 * main_cards
 * Group4
 * PBBS
 *
 * This fragment displays all cards.
 */
public class main_cards extends Fragment
{
    private AccessCard accessCard;
    private ArrayList<Card> cardsList;
    private ArrayAdapter<Card> listViewAdapter;
    private ListView listView;


    // Required empty public constructor
    public main_cards() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessCard = new AccessCard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_cards, container, false);

        // list all the credit cards
        listView = view.findViewById(R.id.listCards);
        accessCard = new AccessCard();    // gain access to credit cards
        cardsList = accessCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                cardsList
        );
        listView.setAdapter(listViewAdapter);

        // display Card detail
        listView.setOnItemClickListener((arg0, view1, position, arg3) ->
        {
            Intent viewCard = new Intent(view1.getContext(), ViewCard.class);
            viewCard.putExtra("creditCard", cardsList.get(position));
            startActivity(viewCard);
        });
        return view;
    }

    /**
     * This method updates the list after adding.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        cardsList = accessCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                cardsList
        );

        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
    }
}