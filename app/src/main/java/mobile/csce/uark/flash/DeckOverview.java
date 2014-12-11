package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;


public class DeckOverview extends Activity  implements AdapterView.OnItemClickListener {

    CardArrayAdapter adapter;
    FlashDatabase database;
    GridView gridView;
    List<Card> cards;
    Button CreateNewCardButton;
    Deck curdeck;


    //change
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_overview);
        getActionBar().hide();
        curdeck = (Deck) getIntent().getSerializableExtra("deck");

        String name = curdeck.GetDeckname();
        System.out.println("THE DECK NAME IS: " + name);
        TextView Dname = (TextView) findViewById(R.id.textView);
        if (name.length() > 18) {
            name = name.substring(0, 15);
            name = name + "...";
        }
        Dname.setText(name);
        database = new FlashDatabase(this);
        database.open();
        cards = database.GetAllCardsInADeck(curdeck);
        // cards = new ArrayList<Card>();
        //cards.add(new Card(2,"TEST","TEST",1,2));
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        adapter = new CardArrayAdapter(this, cards);
        gridView.setAdapter(adapter);

        CreateNewCardButton = (Button) findViewById(R.id.addcardbutton);

        //CreateNewCardButton.setOnClickListener(new View.OnClickListener(){

        //@Override

        //});

    }


    public void StartCreateCardActivity(View view) {
        Intent intent = new Intent(this, CardCreation.class);
        intent.putExtra("D", curdeck.getID());
        intent.putExtra("Creating", true);
        startActivityForResult(intent, 1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (result == RESULT_OK)

        {

            cards = database.GetAllCardsInADeck(curdeck);
            adapter = new CardArrayAdapter(this, cards);
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);


        }
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        // Then you start a new Activity via Intent
        Card packed = (Card) adapter.getItem(position);
        Intent i = packitup(packed);
        i.putExtra("Card2", packed);
        i.putExtra("Creating", false);
        //System.out.println("\n");
        //System.out.print(position);
        //System.out.println("\n");
        i.putExtra("position", position);
        //position = (int) getIntent().getIntExtra("position", 0);
        //System.out.println("\n");
        //System.out.print(position);
        //System.out.println("\n");

        startActivityForResult(i, 9);
    }

    public Intent packitup(Card c) {

        Intent i = new Intent(this, CardCreation.class);
        i.putExtra("card", (Serializable) c);
        return i;

    }

    public void goBacktoDecks(View view) {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void DeleteDeck(View view) {
        database.DeleteDeck(curdeck);
        goBacktoDecks(view);
    }

    public void Quiz_Mode(View view){

        Intent i = new Intent(this, Quiz.class);
        i.putExtra("deckid", curdeck.getID());
        startActivity(i);


    }
}