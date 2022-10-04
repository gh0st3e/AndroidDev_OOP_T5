package denisleonov.fit.bstu.by.lab5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "scheduleStore.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "Schedule";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ROOM = "room";
    public static final String COLUMN_TEACH = "teach";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_WEEK = "week";

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, SCHEMA);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Schedule " +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME  + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_ROOM + " TEXT,"
                + COLUMN_TEACH + " TEXT,"
                + COLUMN_DAY + " TEXT,"
                + COLUMN_WEEK + " INTEGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
