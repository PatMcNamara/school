package edu.umsl.pjm8cd.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.umsl.pjm8cd.contacts.database.ContactDBSchema.ContactTable;

/**
 * Created by Pat on 4/17/2016.
 */
public class ContactBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ContactBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contact.sqlite";

    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ContactTable.NAME + " (" +
                "_id integer primary key autoincrement, " +
                ContactTable.Cols.UUID + " TEXT, " +
                ContactTable.Cols.FNAME + " TEXT, " +
                ContactTable.Cols.LNAME + " TEXT, " +
                ContactTable.Cols.EMAIL + " TEXT, " +
                ContactTable.Cols.PHOTO + " BLOB)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
