package mobile.csce.uark.flash;

    import android.app.Activity;
    import android.app.FragmentManager;
    import android.app.FragmentTransaction;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;


    public class CardCreation extends Activity {
        Button B1,B2,save;
        FlashDatabase database;
        long deckid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_creation);
        B1 = (Button) findViewById(R.id.front);
        B2 = (Button) findViewById(R.id.BackSide);
        save = (Button) findViewById(R.id.Save);
        final EditText fronttext = (EditText) findViewById(R.id.FrontCardText);
        fronttext.setVisibility(View.GONE);
        final EditText backtext = (EditText) findViewById(R.id.BackCardText);
        backtext.setVisibility(View.GONE);
        database = new FlashDatabase(this);
        deckid = getIntent().getLongExtra("D",0);
        database.open();


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                FragmentOne F1 = new FragmentOne();
                FT.add(R.id.fr1_id, F1);
                fronttext.setVisibility(View.VISIBLE);
                backtext.setVisibility(View.GONE);
                FT.commit();
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                Fragmenttwo F2 = new Fragmenttwo();

                FT.add(R.id.fr1_id, F2);
                fronttext.setVisibility(View.GONE);
                backtext.setVisibility(View.VISIBLE);
                FT.commit();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card newCard;
                 String front = fronttext.getText().toString();
                String back = backtext.getText().toString();
                 newCard = new Card(0,front,back,0,deckid);
                 database.InsertCard(newCard);
                Intent i = new Intent();
                setResult(RESULT_OK,i);
                 finish();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_creation, menu);
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

        public void GoBackToCardsView(View view)
        {
            this.finish();
        }
}
