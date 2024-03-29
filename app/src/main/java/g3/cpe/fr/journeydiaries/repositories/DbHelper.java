package g3.cpe.fr.journeydiaries.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_JOURNEY = "CREATE TABLE journey (" +
            "`name` TEXT," +
            "`from` INTEGER," +
            "`to` INTEGER)";

    private static final String SQL_CREATE_TABLE_NOTE = "CREATE TABLE note (" +
            "`journey_id` INTEGER," +
            "`description` TEXT," +
            "`longitude` REAL," +
            "`latitude` REAL)";

    private static DbHelper instance;

    private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        db.execSQL(SQL_CREATE_TABLE_JOURNEY);
        db.execSQL(SQL_CREATE_TABLE_NOTE);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static DbHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DbHelper(context, "mydb.db", null, 1);
        }

        return instance;
    }
}
