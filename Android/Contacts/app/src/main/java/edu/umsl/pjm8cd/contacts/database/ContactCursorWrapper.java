package edu.umsl.pjm8cd.contacts.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.UUID;

import edu.umsl.pjm8cd.contacts.Contact;
import edu.umsl.pjm8cd.contacts.database.ContactDBSchema.ContactTable;

/**
 * Created by Pat on 4/17/2016.
 */
public class ContactCursorWrapper extends CursorWrapper {
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));
        String firstName = getString(getColumnIndex(ContactTable.Cols.FNAME));
        String lastName = getString(getColumnIndex(ContactTable.Cols.LNAME));
        String email = getString(getColumnIndex(ContactTable.Cols.EMAIL));
        Bitmap pic = null;

        byte[] rawBlob = getBlob(getColumnIndex(ContactTable.Cols.PHOTO));
        if(rawBlob != null) {
            pic = BitmapFactory.decodeByteArray(rawBlob, 0, rawBlob.length);
        }

        Contact c = new Contact(UUID.fromString(uuidString));
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setEmail(email);
        c.setPicture(pic);
        return c;
    }
}
