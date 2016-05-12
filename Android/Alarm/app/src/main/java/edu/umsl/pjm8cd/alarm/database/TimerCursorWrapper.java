package edu.umsl.pjm8cd.alarm.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.umsl.pjm8cd.alarm.Alarm;
import edu.umsl.pjm8cd.alarm.database.TimerDBSchema.TimerTable;

/**
 * Created by Pat on 5/1/2016.
 */
public class TimerCursorWrapper extends CursorWrapper {
    public TimerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getContact() {
        String uuidString = getString(getColumnIndex(TimerTable.Cols.UUID));
        String name = getString(getColumnIndex(TimerTable.Cols.NAME));
        int min = getInt(getColumnIndex(TimerTable.Cols.MIN));
        int hours = getInt(getColumnIndex(TimerTable.Cols.HOURS));

        Alarm c = new Alarm(UUID.fromString(uuidString));
        c.setName(name);
        c.setHours(hours);
        c.setMinutes(min);
        return c;
    }
}
