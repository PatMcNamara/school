package edu.umsl.pjm8cd.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.umsl.pjm8cd.contacts.database.ContactBaseHelper;
import edu.umsl.pjm8cd.contacts.database.ContactDBSchema.ContactTable;

/**
 * Created by Pat on 4/17/2016.
 */
public class DBWrapper {
    private SQLiteDatabase database;

    public DBWrapper(Context context) {
        database = new ContactBaseHelper(context).getWritableDatabase();
    }

    public void addContact(Contact newContact) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.UUID, newContact.getId().toString());
        values.put(ContactTable.Cols.FNAME, newContact.getFirstName());
        values.put(ContactTable.Cols.LNAME, newContact.getLastName());
        values.put(ContactTable.Cols.EMAIL, newContact.getEmail());

        database.insert(ContactTable.NAME, null, values);
    }
}
