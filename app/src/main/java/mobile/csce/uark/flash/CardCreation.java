package mobile.csce.uark.flash;

    import android.app.ActionBar;

   // import android.app.Fragment;
    import android.app.Activity;
    import android.app.FragmentManager;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.support.v4.app.Fragment;


    import android.support.v4.view.MotionEventCompat;
    import android.view.GestureDetector;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;

    import android.widget.FrameLayout;
    import android.widget.RelativeLayout;
    import android.widget.TextView;
    import android.widget.Toast;


public class CardCreation extends Activity implements FragmentManager.OnBackStackChangedListener{



        Button save,back;

        FlashDatabase database;
        long deckid;

        private boolean mShowingBack = false;
        private Handler mHandler = new Handler();
        Fragmenttwo F2;
        FragmentOne F1;
        FragmentManager FM;
        private GestureDetector gestureDetector;

         Card cardBeingViewed;
    boolean isCreating;
    TextView textView;
    RelativeLayout TouchLayer;
    FrameLayout frame;

    ActionBar actionBar;
    boolean FrontVisible= true;
    boolean Editing = false;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_creation);

        actionBar= getActionBar();
        actionBar.hide();

        textView = (TextView) findViewById(R.id.navigationbarlabel);
        save = (Button) findViewById(R.id.Save);
        back = (Button) findViewById(R.id.button5);
        frame = (FrameLayout)findViewById(R.id.fr1_id);

        database = new FlashDatabase(this);
        deckid = getIntent().getLongExtra("D",0);
        cardBeingViewed = (Card)getIntent().getSerializableExtra("Card2");
        isCreating = getIntent().getBooleanExtra("Creating",true);

        database.open();

        F1 = new FragmentOne();
        F2 = new Fragmenttwo();
        FM = getFragmentManager();

        if (cardBeingViewed != null) {
            // F1Text.setText(cardBeingViewed.getFrontSide());
            //backtext.setText(cardBeingViewed.getBackSide());
             textView.setText(cardBeingViewed.getNumber()+"/"+database.GetNumOfCardsInDeck(cardBeingViewed.getDeckID()));

            Bundle bundle = new Bundle();
            bundle.putString("T", cardBeingViewed.getFrontSide());
            F1.setArguments(bundle);
            bundle = new Bundle();
            bundle.putString("T", cardBeingViewed.getBackSide());
            F2.setArguments(bundle);
        }
        else
            {
                textView.setText((database.GetNumOfCardsInDeck(deckid)+1)+"/"+(database.GetNumOfCardsInDeck(deckid)+1));
                Bundle bundle = new Bundle();
                bundle.putString("T", "");
                F1.setArguments(bundle);
                bundle = new Bundle();
                bundle.putString("T", "");
                F2.setArguments(bundle);
            }










        FM.beginTransaction()
                .add(R.id.fr1_id, F1,"Frag1")
                //.add(F2,"Frag2")
                .commit();
       // getSupportFragmentManager().beginTransaction().add(R.id.fr1_id, F2,"Frag2").commit();
        //FM.executePendingTransactions();

       // backtext = (EditText) findViewById(R.id.BackCardText);












        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardBeingViewed == null) {
                    Card newCard;
                    String tf ="";
                    String tb ="";
                    if (Editing == false) {
                        frame.bringToFront();
                        save.setText("Save");
                        back.setText("Cancel");
                        Editing = true;
                        if (FrontVisible)
                            F1.Edit();
                        else
                            F2.Edit();
                    }
                    else if (Editing == true)
                    {
                        if (FrontVisible)
                            tf = (F1.getText());
                        else
                            tb = (F2.getText());
                        Editing = false;

                        database.InsertCard(new Card(0,tf,tb,0,deckid));
                        cardBeingViewed = database.GetLastCardInDeck(deckid);
                        TouchLayer = (RelativeLayout)findViewById(R.id.fr2_id);
                        TouchLayer.bringToFront();
                        save.setText("Edit");
                        back.setText("Back");
                    }
                }

                else
                {
                    if (Editing == false) {
                        frame.bringToFront();
                        save.setText("Save");
                        back.setText("Cancel");
                        Editing = true;
                        if (FrontVisible)
                            F1.Edit();
                        else
                            F2.Edit();
                    }
                    else if (Editing == true)
                    {
                        if (FrontVisible)
                        cardBeingViewed.setFrontSide(F1.getText());
                        else
                        cardBeingViewed.setBackSide(F2.getText());
                        Editing = false;

                        database.UpdateCardText(cardBeingViewed);
                        TouchLayer = (RelativeLayout)findViewById(R.id.fr2_id);
                        TouchLayer.bringToFront();
                        save.setText("Edit");
                        back.setText("Back");
                    }

                    //flipCard();
                    //cardBeingViewed.setFrontSide(F1.getText());
                    //cardBeingViewed.setBackSide(F2.getText());
                    //database.UpdateCardText(cardBeingViewed);
                    //Intent i = new Intent();
                    //setResult(RESULT_OK, i);
                   // finish();
                }

            }
        });

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing the
            // front of the card to this activity. If there is saved instance state,
            // this fragment will have already been added to the activity.
            //getSupportFragmentManager()
              //      .beginTransaction()
                //    .add(R.id.fr2_id, new FragmentOne())
                  //  .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        FM.addOnBackStackChangedListener(this);

        gestureDetector = new GestureDetector(this, new GestureHelper());

        RelativeLayout TouchLayer = (RelativeLayout)findViewById(R.id.fr2_id);


        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 gestureDetector.onTouchEvent(event);
                if (GestureHelper.Direction == GestureHelper.DIRECTION_LEFT || GestureHelper.Direction == GestureHelper.DIRECTION_RIGHT)
                {
                    //System.out.println("HERE");
                    flipCard();
                    return true;
                }
                return true;
                //else return false;

            }


            //EditText et = new EditText(getBaseContext());





        };
        TouchLayer.setOnTouchListener(gestureListener);

        //findViewById(R.id.TouchLayer).setOnTouchListener(gestureListener);


       // textView.setText((database.GetNumOfCardsInDeck(deckid)+1)+"/"+(database.GetNumOfCardsInDeck(deckid)+1));


/*
        backtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (backtext.getLayout().getLineCount()>12)
                {
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(backtext.getText().length());
                    backtext.setFilters(fArray);
                }
            }
        });


*/


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

        public void Back(View view)
        {
            if (Editing == true) {
                frame.bringToFront();
                save.setText("Edit");
                back.setText("Back");
                Editing = false;
                if (FrontVisible)
                    F1.setText(cardBeingViewed.getFrontSide());
                else
                    F2.setText(cardBeingViewed.getBackSide());

                TouchLayer = (RelativeLayout)findViewById(R.id.fr2_id);
                TouchLayer.bringToFront();
            }
            else if (Editing == false)
            {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }


        }

        @Override
        public void onBackStackChanged() {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

            // When the back stack changes, invalidate the options menu (action bar).
            invalidateOptionsMenu();
        }

        private void flipCard() {
            FrontVisible = !FrontVisible;
            if (mShowingBack) {
                getFragmentManager().popBackStack();
                return;
            }

            // Flip to the back.

            mShowingBack = true;

            // Create and commit a new fragment transaction that adds the fragment for the back of
            // the card, uses custom animations, and is part of the fragment manager's back stack.
           // if (F2.getText() != cardBeingViewed.getBackSide())
            //{
               // cardBeingViewed.setBackSide(F2.getText());
            //}
            FM.beginTransaction()//.add(R.id.fr1_id, F2,"Frag2")

                            // Replace the default fragment animations with animator resources representing
                            // rotations when switching to the back of the card, as well as animator
                            // resources representing rotations when flipping back to the front (e.g. when
                            // the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                            // Replace any fragments currently in the container view with a fragment
                            // representing the next page (indicated by the just-incremented currentPage
                            // variable).

                    .replace(R.id.fr1_id, F2)

                            // Add this transaction to the back stack, allowing users to press Back
                            // to get to the front of the card.
                    .addToBackStack(null)

                            // Commit the transaction.
                    .commit();

            // Defer an invalidation of the options menu (on modern devices, the action bar). This
            // can't be done immediately because the transaction may not yet be committed. Commits
            // are asynchronous in that they are posted to the main thread's message loop.
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    invalidateOptionsMenu();
                }
            });
        }




    }
