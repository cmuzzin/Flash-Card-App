package mobile.csce.uark.flash;

    import android.app.ActionBar;
    import android.app.Activity;
    import android.app.Fragment;
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
    import android.view.animation.Animation;
    import android.view.animation.AnimationUtils;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.FrameLayout;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.TextView;

    import java.util.ArrayList;


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
    boolean isCreating;

    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar = getActionBar();

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
        deckid = getIntent().getLongExtra("D", 0);
        cardBeingViewed = (Card) getIntent().getSerializableExtra("Card2");
        isCreating = getIntent().getBooleanExtra("Creating", false);

        database.open();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardBeingViewed == null) {
                    Card newCard;
                    String front = fronttext.getText().toString();
                    String back = backtext.getText().toString();
                    newCard = new Card(0, front, back, 0, deckid);
                    database.InsertCard(newCard);
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    cardBeingViewed.setFrontSide(fronttext.getText().toString());
                    cardBeingViewed.setBackSide(backtext.getText().toString());
                    database.UpdateCardText(cardBeingViewed);
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
                if (gestureDetector.onTouchEvent(event) == true) {
                    v.setEnabled(false);

                    if (GestureHelper.Direction == GestureHelper.DIRECTION_RIGHT || GestureHelper.Direction == GestureHelper.DIRECTION_LEFT) {

                        flipCard();
                        if (fronttext.getVisibility() == View.VISIBLE) {
                            fronttext.setVisibility(View.GONE);
                            backtext.setVisibility(View.VISIBLE);
                        } else if (fronttext.getVisibility() == View.GONE) {
                            backtext.setVisibility(View.GONE);
                            fronttext.setVisibility(View.VISIBLE);
                        }

                    } else if (GestureHelper.Direction == GestureHelper.DIRECTION_UP) {
                        //ArrayList<Card> temp = database.
                    }
                    v.setEnabled(true);
                    //v.requestFocus();
                } else {
                    // v.requestFocus();
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
        textView.setText((database.GetNumOfCardsInDeck(deckid) + 1) + "/" + (database.GetNumOfCardsInDeck(deckid) + 1));

        if (cardBeingViewed != null) {
            fronttext.setText(cardBeingViewed.getFrontSide());
            backtext.setText(cardBeingViewed.getBackSide());
            textView.setText(cardBeingViewed.getNumber() + "/" + database.GetNumOfCardsInDeck(cardBeingViewed.getDeckID()));
        }

        backtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (backtext.getLayout().getLineCount() > 12) {
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(backtext.getText().length());
                    backtext.setFilters(fArray);
                }
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

public void slidecard(){

    //Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.slide_down);
    //RL.startAnimation(slide);


    // Create and commit a new fragment transaction that adds the fragment for the back of
    // the card, uses custom animations, and is part of the fragment manager's back stack.

    getFragmentManager()

    .

    beginTransaction()

    // Replace the default fragment animations with animator resources representing
    // rotations when switching to the back of the card, as well as animator
    // resources representing rotations when flipping back to the front (e.g. when
    // the system Back button is pressed).
    .

    setCustomAnimations(
            R.animator.slide_up, R.animator.slide_up)

    // Replace any fragments currently in the container view with a fragment
    // representing the next page (indicated by the just-incremented currentPage
    // variable).
    .

    replace(R.id.fr1_id, new FragmentOne()

    )

            // Add this transaction to the back stack, allowing users to press Back
            // to get to the front of the card.
            .

    addToBackStack(null)

    // Commit the transaction.
    .

    commit();

    // Defer an invalidation of the options menu (on modern devices, the action bar). This
    // can't be done immediately because the transaction may not yet be committed. Commits
    // are asynchronous in that they are posted to the main thread's message loop.
    mHandler.post(new

    Runnable() {
        @Override
        public void run () {
            invalidateOptionsMenu();
        }
    }

    );

}

        public void GoBackToCardsView(View view)
        {



           // RelativeLayout f1 = (RelativeLayout) findViewById(R.id.fr1_id);
            slidecard();
            fronttext.setVisibility(View.VISIBLE);
            fronttext.setText("newcard");

/*
            RelativeLayout fl = (RelativeLayout) findViewById(R.id.fr1_id);
            Animation slideUp = AnimationUtils.loadAnimation(CardCreation.this, R.anim.slide_up);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
              @Override
              public void onAnimationEnd(Animation animation) {
                  RelativeLayout fl = (RelativeLayout) findViewById(R.id.fr1_id);
                  fl.setVisibility(fl.GONE);
              }
              @Override
                  public void onAnimationRepeat(Animation animation) {}
              @Override
                  public void onAnimationStart(Animation animation) {}
             });
            fl.startAnimation(slideUp);
            f1.startAnimation(slideDown);


            //Intent i = new Intent();
            //setResult(RESULT_OK, i);
            // finish();*/
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
