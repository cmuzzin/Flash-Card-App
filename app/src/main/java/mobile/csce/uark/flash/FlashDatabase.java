package mobile.csce.uark.flash;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class FlashDatabase extends ContentProvider {

    public static final String uriString = "content://mobile.csce.uark.flash/Decks";
    public static final Uri CONTENT_URI = Uri.parse(uriString);

    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";
    public static final String KEY_NAME = "Name";
    public static final String KEY_DECKID = "latis";
    public static final String KEY_LONGS = "longs";

    private MySQLiteOpenHelper myOpenHelper;
    private static final int ALLROWS = 1;
    private static final int SINGLEROW = 2;

    private static final UriMatcher myUriMatcher;
    static {
        myUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        myUriMatcher.addURI("mobile.csce.uark.flash", "Decks", ALLROWS);
        myUriMatcher.addURI("mobile.csce.uark.flash", "Decks/#", SINGLEROW);
    }

    public FlashDatabase() {
    }

    @SuppressWarnings("static-access")
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        selection = KEY_ID + "=" + selection;
        int deleteCount = db.delete(myOpenHelper.DATABASE_TABLE, selection, selectionArgs);
        System.out.println(deleteCount);
        System.out.println("\n");
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        switch(myUriMatcher.match(uri)) {
            case ALLROWS:
                // vnd stands for "vendor-specific MIME types". vnd.<company name>.<provider name>
                return "vnd.android.cursor.dir/vnd.uark.Decks";
            case SINGLEROW:
                return "vnd.android.cursor.item/vnd.uark.Decks";
            default:
                throw new IllegalArgumentException("Unsupported URI: "+ uri);
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColHack = null;
        long id = db.insert(myOpenHelper.DATABASE_TABLE, nullColHack, values);

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
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String groupby = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(myOpenHelper.DATABASE_TABLE);

        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupby, having, sortOrder);
        return cursor;
    }

    @SuppressWarnings("static-access")
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (myUriMatcher.match(uri)) {
            case SINGLEROW:
                String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID;
                if (!TextUtils.isEmpty(selection)) {
                    String appendString = " and (" + selection + ")";
                    selection += appendString;
                }
            default:
                break;
        }

        int updateCount = db.update(myOpenHelper.DATABASE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    // SQLite
    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "FlashDatabase.db";
        private static final int DATABASE_VERSION = 2;
        private static final String DATABASE_TABLE = "FlashTable";

        private static final String DATABASE_CREATE_CMD =
                "create table "+ DATABASE_TABLE + " (" + KEY_ID +
                        " integer primary key autoincrement, " + KEY_STEPS + " text not null, " +
                        KEY_TIME + " text not null, " + KEY_LATS + " text not null, " + KEY_LONGS + " text not null, " +
                        KEY_DATE + " long); delete from sqlite_sequence where name='" + DATABASE_TABLE +"';"
                ;
        private static final String DATABASE_DROP_CMD =
                "drop table " + DATABASE_TABLE;

        public MySQLiteOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_CMD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("FLASHPROVIDER", "Upgrading from version " + oldVersion +
                            " to " + newVersion + ". All data will be deleted."
            );
            db.execSQL(DATABASE_DROP_CMD);
            db.execSQL(DATABASE_CREATE_CMD);
        }




    }
}