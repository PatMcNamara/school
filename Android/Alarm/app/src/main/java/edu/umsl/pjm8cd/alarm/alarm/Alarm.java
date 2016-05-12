package edu.umsl.pjm8cd.alarm.alarm;

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
}
