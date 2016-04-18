package edu.umsl.pjm8cd.contacts.database;

/**
 * Created by Pat on 4/17/2016.
 */
public class ContactDBSchema {
    public static final class ContactTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FNAME = "fname";
            public static final String LNAME = "lname";
            public static final String EMAIL = "email";
            public static final String PHOTO = "photo";
        }
    }
}
