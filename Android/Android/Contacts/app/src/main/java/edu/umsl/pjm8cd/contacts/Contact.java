package edu.umsl.pjm8cd.contacts;

import java.util.UUID;

/**
 * Created by Pat on 4/17/2016.
 */
public class Contact {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    //TODO photo.

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID id) {
        this.id = id;
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
}
