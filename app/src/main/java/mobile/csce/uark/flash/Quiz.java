package mobile.csce.uark.flash;

import android.app.ActionBar;

// import android.app.Fragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends Activity implements FragmentManager.OnBackStackChangedListener{



    Button back;

    FlashDatabase database;
    long deckid;

    private boolean mShowingBack = false;
    private Handler mHandler = new Handler();
    Fragmenttwo F2;
    FragmentOne F1;
    FragmentManager FM;
    private GestureDetector gestureDetector;
    List<Card> Cards;
    private int curcard = 0;
    private int score = 0;

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
        back = (Button) findViewById(R.id.button5);
        frame = (FrameLayout)findViewById(R.id.fr1_id);
        database = new FlashDatabase(this);
        database.open();


        deckid = getIntent().getLongExtra("deckid",0);
        isCreating = getIntent().getBooleanExtra("Creating",true);
        Cards = database.GetAllCardsInDeck(deckid);
        cardBeingViewed = Cards.get(curcard);




        F1 = new FragmentOne();
        F2 = new Fragmenttwo();
        FM = getFragmentManager();

        if (cardBeingViewed != null) {
            // F1Text.setText(cardBeingViewed.getFrontSide());
            //backtext.setText(cardBeingViewed.getBackSide());
            textView.setText("Score: " + score);

            Bundle bundle = new Bundle();
            bundle.putString("T", cardBeingViewed.getFrontSide());
            F1.setArguments(bundle);
            bundle = new Bundle();
            bundle.putString("T", cardBeingViewed.getBackSide());
            F2.setArguments(bundle);
        }
        else
        {
            textView.setText("Score: " + score);
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
                else if (GestureHelper.Direction == GestureHelper.DIRECTION_UP)
                {
                        if (curcard+1 > 0 && curcard+1 < Cards.size()){
                            if(Cards.size() < 2){
                                Cards = database.GetAllCardsInDeck(deckid);
                                curcard=0;
                            }
                            Cards.remove(curcard);
                            if(Cards.size() < 2){
                                Cards = database.GetAllCardsInDeck(deckid);
                                curcard=0;
                            }
                            slidecard();
                            System.out.println("The current card is: " + curcard);
                            cardBeingViewed = Cards.get(curcard);
                            score++;
                            textView.setText("Score: " + Integer.toString(score));
                        }
                        // F1.setText(cardBeingViewed.getFrontSide());
                        //F2.setText(cardBeingViewed.getBackSide());
                        //ChangeCardNext();
                }
                else if (GestureHelper.Direction == GestureHelper.DIRECTION_DOWN)
                {


                        if(curcard+1 > 0 && curcard+1 < Cards.size()) {
                            slidecardDown();
                            curcard++;
                            if(Cards.size() < 2){
                                Cards = database.GetAllCardsInDeck(deckid);
                                curcard=0;
                            }
                            cardBeingViewed = Cards.get(curcard);
                            textView.setText("Score: " + Integer.toString(score));
                        }
                        // F1.setText(cardBeingViewed.getFrontSide());
                        //F2.setText(cardBeingViewed.getBackSide());
                        //ChangeCardNext();
                }
                return true;
                //else return false;

            }


            //EditText et = new EditText(getBaseContext());





        };
        TouchLayer.setOnTouchListener(gestureListener);


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
        FragmentOne temp = new FragmentOne();

        Bundle bundle = new Bundle();
        bundle.putString("T",cardBeingViewed.getFrontSide());
        temp.setArguments(bundle);

        FrontVisible = true;
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
                                R.animator.slide_up, R.animator.slide_up_off)

                                // Replace any fragments currently in the container view with a fragment
                                // representing the next page (indicated by the just-incremented currentPage
                                // variable).



                .

                        replace(R.id.fr1_id,temp


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
        F1 = temp;
    }

    public void GoBackToCardsView(View view)
    {
        if (Editing == true) {
            frame.bringToFront();
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


        // Flip to the back.


        if(FrontVisible) {
            FM.beginTransaction();
            Fragmenttwo temp = new Fragmenttwo();
            Bundle b = new Bundle();
            b.putString("T",cardBeingViewed.getBackSide());
            temp.setArguments(b);
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
                            //.replace(R.id.fr2_id,new Fragmenttwo() )

                    .replace(R.id.fr1_id, temp)

                            // Add this transaction to the back stack, allowing users to press Back
                            // to get to the front of the card.
                            //.addToBackStack(null)

                            // Commit the transaction.
                    .commit();

            F2 = temp;
        }
        else
        {
            FM.beginTransaction();
            FM.beginTransaction();
            FragmentOne temp = new FragmentOne();
            Bundle b = new Bundle();
            b.putString("T",cardBeingViewed.getFrontSide());
            temp.setArguments(b);

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
                            //.replace(R.id.fr2_id,new Fragmenttwo() )

                    .replace(R.id.fr1_id, temp)

                            // Add this transaction to the back stack, allowing users to press Back
                            // to get to the front of the card.
                            //.addToBackStack(null)

                            // Commit the transaction.
                    .commit();
            F1 = temp;
        }
        FrontVisible = !FrontVisible;

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

    public void slidecardDown(){

        //Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.slide_down);
        //RL.startAnimation(slide);
        FragmentOne temp = new FragmentOne();

        Bundle bundle = new Bundle();
        bundle.putString("T",cardBeingViewed.getFrontSide());
        temp.setArguments(bundle);

        FrontVisible = true;
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
                                R.animator.slide_down, R.animator.slide_down_off)

                                // Replace any fragments currently in the container view with a fragment
                                // representing the next page (indicated by the just-incremented currentPage
                                // variable).



                .

                        replace(R.id.fr1_id,temp


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
        F1 = temp;
    }


}
