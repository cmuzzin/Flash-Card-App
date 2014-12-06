package mobile.csce.uark.flash;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FlashDatabase{





    private SQLHelper myOpenHelper;
    private SQLiteDatabase database;

     public FlashDatabase(Context context) {
        myOpenHelper = new SQLHelper(context);

    }

    public void open(){database = myOpenHelper.getWritableDatabase();}

    public void close(){database.close();}

    public void InsertDeck(Deck d)
    {
        ContentValues values = new ContentValues();
        values.put(myOpenHelper.DECK_COLUMN_NAME,d.DeckName);
        long insertID = database.insert(myOpenHelper.DATABASE_DECK_TABLE,null,values);
        d.setID(insertID);
       //Cursor cursor = database.query(myOpenHelper.DATABASE_DECK_TABLE,myOpenHelper.allColumns,myOpenHelper.DECK_COLUMN_ID + " = " + insertID, null,null,null,null);
        //cursor.moveToFirst();
    }

    public void DeleteDeck(Deck d)
    {
        long id = d.getID();
        database.delete(myOpenHelper.DATABASE_DECK_TABLE, myOpenHelper.DECK_COLUMN_ID + " = " +id, null);
        System.out.println("YOU DELETED ITEM: " + myOpenHelper.DECK_COLUMN_ID + " = " +id);


    }

    public void InsertCard(Card c)
    {
        ContentValues values = new ContentValues();
        values.put(myOpenHelper.CARD_COLUMN_NUMBER,c.getNumber());
        values.put(myOpenHelper.CARD_COLUMN_DECK_ID,c.getDeckID());
        values.put(myOpenHelper.CARD_COLUMN_BACK,c.getBackSide());
        values.put(myOpenHelper.CARD_COLUMN_FRONT, c.getFrontSide());
        long insertID = database.insert(myOpenHelper.DATABASE_CARD_TABLE, null, values);
        c.setID(insertID);
    }

    public void DeleteCard(Card c)
    {
        long id = c.getID();
        database.delete(myOpenHelper.DATABASE_CARD_TABLE, myOpenHelper.CARD_COLUMN_ID + " = " + id, null);
        System.out.println("YOU DELETED CARD: " + myOpenHelper.CARD_COLUMN_ID + " = " + id);
    }

    public List<Card> GetAllCardsInADeck(Deck d)
    {
        List<Card> cards = new ArrayList<Card>();
        Cursor cursor = database.rawQuery("SELECT * FROM  "+SQLHelper.DATABASE_CARD_TABLE+ " WHERE " + SQLHelper.CARD_COLUMN_DECK_ID+" = "+d.getID()+"order by "+SQLHelper.CARD_COLUMN_NUMBER+ " desc;",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast());
        {
            Card c = cursorToCard(cursor);
            cards.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        return cards;
    }
    private Card cursorToCard(Cursor cursor)
    {
        Card c = new Card(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
        return c;

    }

    public List<Deck> GetAllDecks()
    {
        List<Deck> decks = new ArrayList<Deck>();

        Cursor cursor = database.rawQuery("select * from " + SQLHelper.DATABASE_DECK_TABLE + " order by " + SQLHelper.DECK_COLUMN_ID + " desc;",null);
        //query(SQLHelper.TABLE_WORKOUTS,allColumns,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Deck d = cursorToDeck(cursor);
            decks.add(d);
            cursor.moveToNext();
        }

        cursor.close();
        return decks;
    }

    private Deck cursorToDeck(Cursor cursor)
    {
        Deck d = new Deck(cursor.getLong(0),cursor.getString(1));
        return d;

    }




}

    // SQLite
