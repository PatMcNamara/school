package edu.umsl.pjm8cd.alarm.alarm;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Pat on 5/1/2016.
 */
public class Alarm {
    private UUID id;
    private String name;
    private int hours;
    private int minutes;
    private boolean running;

    public Alarm(){
        this(UUID.randomUUID());
    }

    public Alarm(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String timerName) {
        this.name = timerName;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getTime() {return hours + ":" + minutes; }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Date getTimeAsDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.SECOND, 0);

        if(Calendar.getInstance().after(c)) { // If timer is set for tomorrow.
            c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }
}
