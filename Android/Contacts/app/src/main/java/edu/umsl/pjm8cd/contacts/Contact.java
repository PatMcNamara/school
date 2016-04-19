package edu.umsl.pjm8cd.contacts;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Pat on 4/17/2016.
 */
public class Contact {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Bitmap picture;

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getFullName() {
        return getLastName() + ", " + getFirstName();
    }

    public boolean hasNoName() {
        return firstName.equals("") && firstName.equals("");
    }
}
