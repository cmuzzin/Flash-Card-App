package mobile.csce.uark.flash;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FlashDatabase{





    private SQLHelper myOpenHelper;
    private SQLiteDatabase database;
   // private static final UriMatcher myUriMatcher;

   // static {
    //    myUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //    myUriMatcher.addURI("mobile.csce.uark.flash", "Decks", ALLROWS);
     //   myUriMatcher.addURI("mobile.csce.uark.flash", "Decks/#", SINGLEROW);
    //}

    public FlashDatabase(Context context) {
        myOpenHelper = new SQLHelper(context);

    }

    public void open(){database = myOpenHelper.getWritableDatabase();}

    public void close(){database.close();}

    public void InsertDeck(Deck d)
    {
        ContentValues values = new ContentValues();
        values.put(myOpenHelper.DECK_COLUMN_NAME,d.deckname);
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

    /*public int deletecard(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        selection = DECK_COLUMN_ID + "=" + selection;
        int deleteCount = db.delete(myOpenHelper.DATABASE_CARD_TABLE, selection, selectionArgs);
        System.out.println(deleteCount);
        System.out.println("\n");
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }
    */
/*
    @Override
    public String getType(Uri uri) {
        switch (myUriMatcher.match(uri)) {
            case ALLROWS:
                // vnd stands for "vendor-specific MIME types". vnd.<company name>.<provider name>
                return "vnd.android.cursor.dir/vnd.uark.Decks";
            case SINGLEROW:
                return "vnd.android.cursor.item/vnd.uark.Decks";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
*\
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    /*public void InsertDeck(Deck D){
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.execSQL("Insert Into " + DATABASE_);
    }*/

  /* @SuppressWarnings("static-access")
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColHack = null;
        long id = db.insert(myOpenHelper.DATABASE_DECK_TABLE, nullColHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }
        return null;
    }

    @SuppressWarnings("static-access")
    public Uri insertcard(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColHack = null;
        long id = db.insert(myOpenHelper.DATABASE_CARD_TABLE, nullColHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        myOpenHelper = new MySQLiteOpenHelper(getContext(),
                MySQLiteOpenHelper.DATABASE_NAME,
                null,
                MySQLiteOpenHelper.DATABASE_VERSION);
        return true;
    }

    @SuppressWarnings("static-access")
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String groupby = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(myOpenHelper.DATABASE_DECK_TABLE);

        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DECK_COLUMN_ID + "=" + rowID);
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupby, having, sortOrder);
        return cursor;
    }

    @SuppressWarnings("static-access")
    public Cursor querycard(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String groupby = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(myOpenHelper.DATABASE_CARD_TABLE);

        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DECK_COLUMN_ID + "=" + rowID);
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupby, having, sortOrder);
        return cursor;
    }

    @SuppressWarnings("static-access")
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                selection = DECK_COLUMN_ID + "=" + rowID;
                if (!TextUtils.isEmpty(selection)) {
                    String appendString = " and (" + selection + ")";
                    selection += appendString;
                }
            default:
                break;
        }

        int updateCount = db.update(myOpenHelper.DATABASE_DECK_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    @SuppressWarnings("static-access")
    public int updatecard(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                selection = DECK_COLUMN_ID + "=" + rowID;
                if (!TextUtils.isEmpty(selection)) {
                    String appendString = " and (" + selection + ")";
                    selection += appendString;
                }
            default:
                break;
        }

        int updateCount = db.update(myOpenHelper.DATABASE_CARD_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }
    */
}

    // SQLite
