package mobile.csce.uark.flash;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;


public class StartActivity extends Activity implements AdapterView.OnItemClickListener,  LoaderManager.LoaderCallbacks<Cursor>{

    ArrayList<Deck> createdeckItems;
    DeckArrayAdapter adapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gridView = (GridView) findViewById(R.id.gridView);
        createdeckItems = new ArrayList<Deck>();

        gridView.setOnItemClickListener(this);

        adapter = new DeckArrayAdapter(this);

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

    public Intent ViewDeck(Deck deck)
    {
        Intent intent = new Intent(StartActivity.this,ViewDeck.class);
        intent.putExtra("nameid", deck);
        return intent;
    }

    protected void onActivityResult(int request, int result, Intent data)
    {
        super.onActivityResult(request, result, data);
        if (result == RESULT_OK)

        {
            if (request == 1)
            {
                Deck i = (Deck) data.getSerializableExtra("nameid");
                adapter.add(i);
                adapter.notifyDataSetChanged();

            }
            if (request == 2)
            {

                int position = data.getIntExtra("spot", 0);
               adapter.remove(position);
                adapter.notifyDataSetChanged();

            }
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Deck pass = (Deck) adapter.getItem(position);
        Intent intent = ViewDeck(pass);

        intent.putExtra("spot", position);
        //position = (int) getIntent().getIntExtra("spot", 0);

        startActivityForResult(intent, 2);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
