package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;


public class StartActivity extends Activity implements AdapterView.OnItemClickListener{

    ArrayList<Deck> createdeckItems;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        createdeckItems = new ArrayList<Deck>();
        final  ListView listview = (ListView) findViewById(R.id.listView);
        listview.setOnItemClickListener(this);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,createdeckItems);
        listview.setAdapter(adapter);
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
            return true;
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
        intent.putExtra("nameid",(Serializable) deck);
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
            }
            if (request == 2)
            {
                Deck j = (Deck) data.getSerializableExtra("delete");
                Bundle extras = data.getExtras();
                int position = data.getIntExtra("spot", 0);
                adapter.remove(adapter.getItem(position));
            }
        }
        else if (result == RESULT_CANCELED)
        {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Deck pass = (Deck) adapter.getItem(position);
        Intent intent = ViewDeck(pass);

        intent.putExtra("spot", position);
        position = (int) getIntent().getIntExtra("spot", 0);

        startActivityForResult(intent, 2);

    }
}
