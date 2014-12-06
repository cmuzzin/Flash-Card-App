package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class CreateDeck extends Activity {

    //private Button savedeck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_deck, menu);
        return true;
    }

    public void savedeck(View view){
        Deck createdeckitem = new Deck();
        createdeckitem.setDeckname(((TextView) findViewById(R.id.editText)).getText().toString());

        finish();

    }



}
