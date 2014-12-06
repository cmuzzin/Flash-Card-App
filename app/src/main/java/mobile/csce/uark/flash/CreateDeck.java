package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class CreateDeck extends Activity {
    private Toast toast = null;

    //private Button savedeck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_deck, menu);
        return true;
    }



    public void savedeck(View view){



        String dn = ((TextView)  findViewById(R.id.editText)).getText().toString();
        String temp = dn.trim();

        if (temp.equals(""))
        {
            toast.setText("Deck needs a name");
            toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 500);
            toast.show();
        }

        else
        {
            Deck createdeckitem = new Deck(0,((TextView) findViewById(R.id.editText)).getText().toString());
            FlashDatabase db = new FlashDatabase(this);
            db.open();
            db.InsertDeck(createdeckitem);
            db.close();
            Intent i = new Intent();
            setResult(RESULT_OK,i);
            toast.cancel();
            finish();
        }



        //createdeckitem.setDeckname(((TextView) findViewById(R.id.editText)).getText().toString());



    }



}
