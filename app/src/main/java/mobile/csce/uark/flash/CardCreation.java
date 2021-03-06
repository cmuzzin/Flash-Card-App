package mobile.csce.uark.flash;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

// import android.app.Fragment;

public class CardCreation extends Activity implements FragmentManager.OnBackStackChangedListener{



        Button save,back,delete,add;

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
        delete = (Button) findViewById(R.id.Delete);
        frame = (FrameLayout)findViewById(R.id.fr1_id);
        add = (Button) findViewById(R.id.CreateNew);

        database = new FlashDatabase(this);
        deckid = getIntent().getLongExtra("D",-1);
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
                textView.setText((database.GetNumOfCardsInDeck(deckid) + 1) + "/" + (database.GetNumOfCardsInDeck(deckid) + 1));
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
                    if (cardBeingViewed != null && database.GetNextCard(cardBeingViewed)!=null) {
                        cardBeingViewed = database.GetNextCard(cardBeingViewed);
                        slidecard();
                       // F1.setText(cardBeingViewed.getFrontSide());
                        //F2.setText(cardBeingViewed.getBackSide());
                        //ChangeCardNext();
                        textView.setText(cardBeingViewed.getNumber()+"/"+database.GetNumOfCardsInDeck(cardBeingViewed.getDeckID()));
                    }
                }
                else if (GestureHelper.Direction == GestureHelper.DIRECTION_DOWN)
                {
                    if (cardBeingViewed != null &&database.GetPreviousCard(cardBeingViewed)!=null) {
                        cardBeingViewed = database.GetPreviousCard(cardBeingViewed);
                        slidecardDown();
                        // F1.setText(cardBeingViewed.getFrontSide());
                        //F2.setText(cardBeingViewed.getBackSide());
                        //ChangeCardNext();
                        textView.setText(cardBeingViewed.getNumber()+"/"+database.GetNumOfCardsInDeck(cardBeingViewed.getDeckID()));
                    }
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
                                  public void run() {
                                      invalidateOptionsMenu();
                                  }
                              }

        );
        F1 = temp;
    }

    public void GoBackToCardsView(View view)
        {
            if (Editing == true) {

                if(cardBeingViewed!=null) {

                    if (FrontVisible)
                        F1.setText(cardBeingViewed.getFrontSide());
                    else
                        F2.setText(cardBeingViewed.getBackSide());
                }
                SetWatchView();
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
              if(cardBeingViewed!=null)
              b.putString("T",cardBeingViewed.getBackSide());
              else
              b.putString("T","");
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
              if(cardBeingViewed!=null)
              b.putString("T",cardBeingViewed.getFrontSide());
              else
              b.putString("T","");
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

    public void slidecardDown() {

        //Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.slide_down);
        //RL.startAnimation(slide);
        FragmentOne temp = new FragmentOne();

            Bundle bundle = new Bundle();
        if (cardBeingViewed != null)
        bundle.putString("T", cardBeingViewed.getFrontSide());

        else
        bundle.putString("T","");

        temp.setArguments(bundle);
        F1 = temp;

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
        getFragmentManager().executePendingTransactions();

    }



    public void DeleteCard(View view)
    {
        if(cardBeingViewed != null) {
            if (database.GetPreviousCard(cardBeingViewed) != null) {
                database.DeleteCard(cardBeingViewed);
                cardBeingViewed = database.GetPreviousCard(cardBeingViewed);
                textView.setText(cardBeingViewed.getNumber()+"/"+database.GetNumOfCardsInDeck(deckid));
                slidecardDown();
            } else {
                database.DeleteCard(cardBeingViewed);
                Editing = false;
                GoBackToCardsView(view);
            }
        }
        else {
            GoBackToCardsView(view);
            GoBackToCardsView(view);
        }


    }
    public void  SaveCard(View view) {


        if(cardBeingViewed == null) {
            Card newCard;
            String tf ="";
            String tb ="";
            if (Editing == false) {
              SetEditView();
            }
            else if (Editing == true)
            {
                if (FrontVisible)
                    tf = (F1.getText());
                else
                    tb = (F2.getText());



               cardBeingViewed =  database.InsertCard(new Card(0,tf,tb,0,deckid));
                isCreating = false;
                SetWatchView();

            }
        }

        else
        {
            if (Editing == false) {
                SetEditView();

            }
            else if (Editing == true)
            {
                if (FrontVisible)
                    cardBeingViewed.setFrontSide(F1.getText());
                else
                    cardBeingViewed.setBackSide(F2.getText());


                if (!isCreating)
                database.UpdateCardText(cardBeingViewed);
                else {
                    database.InsertCard(cardBeingViewed);
                    isCreating = false;
                }
               SetWatchView();
            }


        }



    }

    public void  SetWatchView()
    {
        TouchLayer = (RelativeLayout)findViewById(R.id.fr2_id);
        TouchLayer.bringToFront();
        delete.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        add.setEnabled(false);
        delete.setEnabled(false);
        Editing = false;
        save.setText("Edit");
        back.setText("Back");
    }

    public void SetEditView()
    {
        frame.bringToFront();
        save.setText("Save");
        back.setText("Cancel");
        Editing = true;
        if (FrontVisible)
            F1.Edit();
        else
            F2.Edit();
        add.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        delete.bringToFront();
        add.bringToFront();
        add.setEnabled(true);
        delete.setEnabled(true);
    }
    public void  NewCard(View view)
    {
        Card temp;
        if(isCreating == false) {
            if (FrontVisible) {
                temp = new Card(cardBeingViewed.getID(), F1.getText(), cardBeingViewed.getBackSide(),cardBeingViewed.getNumber(), cardBeingViewed.getDeckID());
            } else {
                temp = new Card(cardBeingViewed.getID(), cardBeingViewed.getFrontSide(), F2.getText(),cardBeingViewed.getNumber(), cardBeingViewed.getDeckID());
            }
            database.UpdateCardText(temp);
            isCreating = true;
        }
        else
        {

                if (FrontVisible) {
                    temp = new Card(0, F1.getText(), "",0, deckid);
                } else {
                    temp = new Card(0, "", F2.getText(),0, deckid);
                }
                database.InsertCard(temp);
                isCreating = true;

        }
        cardBeingViewed = new Card(0,"","",0,deckid);

        slidecardDown();
        textView.setText((database.GetNumOfCardsInDeck(deckid)+1)+"/"+(database.GetNumOfCardsInDeck(deckid)+1));
        SetEditView();


    }

    }
