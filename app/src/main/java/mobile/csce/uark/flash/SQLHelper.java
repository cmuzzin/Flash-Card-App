package mobile.csce.uark.flash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mcurtiss on 12/5/2014.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final String DECK_COLUMN_ID = "_id";
    public static final String CARD_COLUMN_ID = "_id";
    public static final String DECK_COLUMN_DATE = "date";
    public static final String CARD_COLUMN_DATE = "date";
    public static final String DECK_COLUMN_NAME = "Name";
    public static final String DECK_COLUMN_NUMCARDS = "numberofcards";
    public static final String DECK_COLUMN_CARDKEYS = "cardkeys";
    public static final String CARD_COLUMN_FRONT = "frontcardinfo";
    public static final String CARD_COLUMN_BACK = "backcardinfo";
    public static final String CARD_COLUMN_DECK_ID = "Carddeckid";

    public static final String DATABASE_NAME = "FlashDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_DECK_TABLE = "FlashTable";
    public static final String DATABASE_CARD_TABLE = "CardTable";
    private SQLiteDatabase db;

    private static final String DATABASE_CREATE_DECK_TABLE =
            "create table "+ DATABASE_DECK_TABLE + " (" + DECK_COLUMN_ID +
                    " integer primary key autoincrement, " + DECK_COLUMN_NAME + " text not null);"
            ;

    /*private static final String DATABASE_CREATE_CARD_TABLE =
            "create table "+ DATABASE_CARD_TABLE + " (" + CARD_COLUMN_ID +
                    " integer primary key autoincrement, FOREIGN KEY (" +CARD_COLUMN_DECK_ID+") references "+DATABASE_DECK_TABLE+ "("+DECK_COLUMN_ID+"),"+
                    CARD_COLUMN_FRONT + " text not null, " +
                    CARD_COLUMN_BACK + " text not null, " +
                    CARD_COLUMN_DATE + " long);"

            ;
            */
    private static final String DATABASE_DECK_DROP_CMD =
            "drop table " + DATABASE_DECK_TABLE;

    private static final String DATABASE_CARD_DROP_CMD =
            "drop table " + DATABASE_CARD_TABLE;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
       database.execSQL(DATABASE_CREATE_DECK_TABLE);
       //database.execSQL(DATABASE_CREATE_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("FLASHPROVIDER", "Upgrading from version " + oldVersion +
                        " to " + newVersion + ". All data will be deleted."
        );
        db.execSQL(DATABASE_DECK_DROP_CMD);
        db.execSQL(DATABASE_CREATE_DECK_TABLE);
        db.execSQL(DATABASE_CARD_DROP_CMD);
        //db.execSQL(DATABASE_CREATE_CARD_TABLE);
    }






}


