package mobile.csce.uark.flash;

    import android.app.ActionBar;
    import android.app.Activity;
    import android.app.FragmentManager;
    import android.app.FragmentTransaction;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.text.Editable;
    import android.text.InputFilter;
    import android.text.TextWatcher;
    import android.view.GestureDetector;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;


public class CardCreation extends Activity implements FragmentManager.OnBackStackChangedListener,View.OnClickListener {
        Button save;
        private View imageView;
        FlashDatabase database;
        long deckid;
        private EditText fronttext;
        private EditText backtext;
        private boolean mShowingBack = false;
        private Handler mHandler = new Handler();
        FragmentManager FM;
        FragmentTransaction FT;
        Fragmenttwo F2;
        FragmentOne F1;
        private GestureDetector gestureDetector;
        View.OnTouchListener gestureListener;
         Card cardBeingViewed;

    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar= getActionBar();

        actionBar.hide();
        FM = getFragmentManager();
        FT = FM.beginTransaction();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_creation);
        fronttext = (EditText) findViewById(R.id.FrontCardText);
        backtext = (EditText) findViewById(R.id.BackCardText);

        F2 = new Fragmenttwo();
        backtext.setMovementMethod(null);
        backtext.setMaxLines(12);

        imageView = findViewById(R.id.TouchView);

        F1 = new FragmentOne();
        //FT.add(R.id.fr1_id, F1);

        //FT.add(R.id.fr1_id, F2);

        save = (Button) findViewById(R.id.Save);
        fronttext.setVisibility(View.VISIBLE);
        backtext.setVisibility(View.GONE);
        database = new FlashDatabase(this);
        deckid = getIntent().getLongExtra("D",0);
        cardBeingViewed = getIntent().getSerializableExtra("Card2");
        database.open();

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

        if (savedInstanceState == null) {
            // If there is no saved instance state, add a fragment representing the
            // front of the card to this activity. If there is saved instance state,
            // this fragment will have already been added to the activity.
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fr2_id, new FragmentOne())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        // Monitor back stack changes to ensure the action bar shows the appropriate
        // button (either "photo" or "info").
        getFragmentManager().addOnBackStackChangedListener(this);

        gestureDetector = new GestureDetector(this, new GestureHelper());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event) == true)
                {
                    v.setEnabled(false);
                    flipCard();
                    if (fronttext.getVisibility()==View.VISIBLE) {
                        fronttext.setVisibility(View.GONE);
                        backtext.setVisibility(View.VISIBLE);
                    }
                    else if(fronttext.getVisibility()==View.GONE)
                    {
                        backtext.setVisibility(View.GONE);
                        fronttext.setVisibility(View.VISIBLE);
                    }
                    v.setEnabled(true);
                    //v.requestFocus();
                }
                else
                {
                    v.requestFocus();
                }

                return gestureDetector.onTouchEvent(event);
            }
        };

        fronttext.setOnTouchListener(gestureListener);
        backtext.setOnTouchListener(gestureListener);
        //F1.setOnTouchListener(gestureListener);

        //fronttext.setOnTouchListener(gestureListener);

       // backtext.setOnClickListener(CardCreation.this);
        //backtext.setOnTouchListener(gestureListener);

        TextView textView = (TextView) findViewById(R.id.navigationbarlabel);
        textView.setText((database.GetNumOfCardsInDeck(deckid)+1)+"/"+(database.GetNumOfCardsInDeck(deckid)+1));

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

        @Override
        public void onBackStackChanged() {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

            // When the back stack changes, invalidate the options menu (action bar).
            invalidateOptionsMenu();
        }

        private void flipCard() {
            if (mShowingBack) {
                getFragmentManager().popBackStack();
                return;
            }

            // Flip to the back.

            mShowingBack = true;

            // Create and commit a new fragment transaction that adds the fragment for the back of
            // the card, uses custom animations, and is part of the fragment manager's back stack.

            getFragmentManager()
                    .beginTransaction()

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
                    .replace(R.id.fr2_id,new Fragmenttwo() )

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



        @Override
        public void onClick(View v) {

        }
    }
