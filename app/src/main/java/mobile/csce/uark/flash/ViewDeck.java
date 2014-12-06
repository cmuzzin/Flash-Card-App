package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ViewDeck extends Activity {

    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deck);

        // get serializable
        Deck vd = (Deck) getIntent().getSerializableExtra("nameid");
        //bundle

        // get position
        Bundle extras = getIntent().getExtras();
        int spot = (int) getIntent().getIntExtra("spot", 0);
        position = spot;



        String deckname = vd.GetDeckname();
        TextView namedisplay = (TextView) findViewById(R.id.textView2);
        namedisplay.setText(deckname);
        System.out.println("\n");
        System.out.println(deckname);
        System.out.println("\n");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_deck, menu);
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

    public void back(View v){
        ViewDeck.this.finish();
    }

    public void deleteitem(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("spot", position);
        intent.putExtra("delete", (Deck) getIntent().getSerializableExtra("nameid"));
        setResult(RESULT_OK, intent);

        finish();
    }

}
