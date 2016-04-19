package edu.umsl.pjm8cd.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.umsl.pjm8cd.contacts.database.ContactBaseHelper;
import edu.umsl.pjm8cd.contacts.database.ContactCursorWrapper;
import edu.umsl.pjm8cd.contacts.database.ContactDBSchema.ContactTable;

/**
 * Created by Pat on 4/15/2016.
 *
 * Wrapper allowing singleton database access.
 */
public class DBWrapper {
    private static DBWrapper wrapper;

    private SQLiteDatabase database;

    public static DBWrapper get(Context context) {
        if (wrapper == null) {
            wrapper = new DBWrapper(context);
        }
        return wrapper;
    }

    public DBWrapper(Context context) {
        database = new ContactBaseHelper(context).getWritableDatabase();
    }

    // Updates the contact if it is already in the database or adds it if it is not.
    public void updateOrAddContact(Contact contact) {
        if(getContactFromUUID(contact.getId()) == null) { // New contact
            addNewContact(contact);
        } else { // Update Contact
            ContentValues values = getContentValues(contact);
            database.update(ContactTable.NAME, values, ContactTable.Cols.UUID + " = '" + contact.getId().toString() + "'", null);
        }
    }

    private ContentValues getContentValues(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.UUID, contact.getId().toString());
        values.put(ContactTable.Cols.FNAME, contact.getFirstName());
        values.put(ContactTable.Cols.LNAME, contact.getLastName());
        values.put(ContactTable.Cols.EMAIL, contact.getEmail());

        if(contact.getPicture() == null) {
            values.put(ContactTable.Cols.PHOTO, (Byte) null);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            contact.getPicture().compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(ContactTable.Cols.PHOTO, bArray);
        }
        return values;
    }

    private void addNewContact(Contact newContact) {
        database.insert(ContactTable.NAME, null, getContentValues(newContact));
    }

    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                ContactTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new ContactCursorWrapper(cursor);
    }


    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        ContactCursorWrapper cursor = queryContacts(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contacts.add(cursor.getContact());
            cursor.moveToNext();
        }
        cursor.close();

        return contacts;
    }

    /* Returns the contact in the database associated with the given UUID or null if the contact doesn't exist */
    public Contact getContactFromUUID(UUID id) {
        ContactCursorWrapper wrap = queryContacts(ContactTable.Cols.UUID + "='" + id.toString() + "'", null);
        if(wrap.getCount() > 1) { // If there are more then one contact with the same UUID something went very wrong.
            Log.wtf("DBWRAPPER", "Getting the id " + id.toString() + " just returned " + wrap.getCount() + " result");
            return null;
        }
        wrap.moveToFirst();
        return wrap.getContact();
    }
}
