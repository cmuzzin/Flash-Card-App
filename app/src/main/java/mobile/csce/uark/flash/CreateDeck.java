package mobile.csce.uark.flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        toast = Toast.makeText(getApplicationContext(), "",Toast.LENGTH_LONG);

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
        Intent intent = new Intent();
        intent.putExtra("nameid", (Serializable)createdeckitem);
        TextView deckname = (TextView) findViewById(R.id.editText);
        String dn = deckname.getText().toString();
        System.out.println("Value of my string" + dn);
        if (dn.isEmpty())
        {
            toast.setText("Deck needs a name");
            toast.show();
            setResult(RESULT_CANCELED,intent);


            //setResult(RESULT_OK, intent);
            //finish();
        }

        else
        {

            setResult(RESULT_OK, intent);
            toast.cancel();
            finish();
        }



        //setResult(RESULT_OK, intent);
        //finish();

    }



}
