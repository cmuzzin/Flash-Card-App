package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.Serializable;
import java.util.List;


public class StartActivity extends Activity implements AdapterView.OnItemClickListener {

    List<Deck> createdeckItems;
    DeckArrayAdapter adapter;
    GridView gridView;
    private FlashDatabase dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        dataSource = new FlashDatabase(this);
        dataSource.open();
        createdeckItems = dataSource.GetAllDecks();



        setContentView(R.layout.activity_start);
        gridView = (GridView) findViewById(R.id.gridView);
        //createdeckItems = new ArrayList<Deck>();

       // gridView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(this);

        adapter = new DeckArrayAdapter(this,createdeckItems);

        gridView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return id == R.id.action_settings;
        }
        return super.onOptionsItemSelected(item);
    }

    public void StartCreateDeckActivity(View view)
    {
        Intent intent = new Intent(this,CreateDeck.class);
        startActivityForResult(intent, 1);

    }

    //public Intent ViewDeck(Deck deck)
    //{
       // Intent intent = new Intent(StartActivity.this,ViewDeck.class);
        //intent.putExtra("nameid", deck);
        //return intent;
    //}

    protected void onActivityResult(int request, int result, Intent data)
    {
        super.onActivityResult(request, result, data);
        if (result == RESULT_OK)

        {
            if (request == 1)
            {
                createdeckItems = dataSource.GetAllDecks();
                adapter = new DeckArrayAdapter(this,createdeckItems);
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);

            }
            if (request == 2)
            {

                int position = data.getIntExtra("spot", 0);
               adapter.remove(position);
                adapter.notifyDataSetChanged();

            }
        }


    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        // Then you start a new Activity via Intent
        Deck packed = (Deck) adapter.getItem(position);
        Intent i = packitup(packed);
        i.putExtra("Deck2", (Serializable)packed);
        //System.out.println("\n");
        //System.out.print(position);
        //System.out.println("\n");
        i.putExtra("position", position);
        //position = (int) getIntent().getIntExtra("position", 0);
        //System.out.println("\n");
        //System.out.print(position);
        //System.out.println("\n");

        startActivityForResult(i, 1);

    }

    public Intent packitup(Deck d){

        Intent i = new Intent(this, DeckOverview.class);
        i.putExtra("deck",(Serializable)d);
        return i;

    }



}
