package com.agenda.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agenda.models.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class contains methods that open, close, and query the agenda database.
 */
public class AgendaDataSource {
    
    /**
     * Singleton instance of AgendaDataSource
     */
    private static AgendaDataSource dsInstance = null;
    
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    
    /**
     * Array of all column titles in Events table
     */
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE,
            SQLiteHelper.COLUMN_LOCATION,
            SQLiteHelper.COLUMN_START,
            SQLiteHelper.COLUMN_END,
            SQLiteHelper.COLUMN_DETAILS };
    
    /**
     * Returns an instance of AgendaDataSource if it exists, otherwise creates
     * a new AgendaDataSource object and returns it
     * @param context
     * The Activity that called this method
     * @return
     * An instance of AgendaDataSource
     */
    public static AgendaDataSource getInstance(Context context) {
        if (dsInstance == null) {
            dsInstance = new AgendaDataSource(context.getApplicationContext());
        }
        return dsInstance;
    }
    
    /**
     * Constructor that should never be called by user
     * @param context
     * The Activity that called this method
     */
    private AgendaDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }
    
    /**
     * Opens the Agenda database for writing
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    /**
     * Closes the Agenda database
     */
    public void close() {
        dbHelper.close();
    }
    
    /**
     * Creates new row in database and stores all of the event's details. Then creates
     * an Event object from the details stored in the database and returns it.
     * @param title
     * @param location
     * @param start
     * @param end
     * @param details
     * @return
     * Event object that was created
     */
    public Event createEvent(String title, String location, String start, String end, 
            String details) {
        
        // TODO

        // Put keys (row columns) and values (parameters) into ContentValues object
        ContentValues values = new ContentValues(5);
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_LOCATION, location);
        values.put(SQLiteHelper.COLUMN_START, start);
        values.put(SQLiteHelper.COLUMN_END, end);
        values.put(SQLiteHelper.COLUMN_DETAILS, details);

        // Insert ContentValues into row in events table and obtain row ID
        // HINT: database.insert(...) returns the id of the row you insert
        long id = database.insert(SQLiteHelper.TABLE_EVENTS, null, values);


        // Query database for event row just added using the getEvent(...) method
        // NOTE: You need to write a query to get an event by id at the to-do marker 
        //         in the getEvent(...) method
        Event newEvent = getEvent(id);
        
        return newEvent;
    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        String id_string = Long.toString(id);
        database.delete(SQLiteHelper.TABLE_EVENTS, "? = ?",
                new String[] {SQLiteHelper.COLUMN_ID, id_string});
    }
    
    /**
     * Queries and returns event based on ID
     * @param id
     * ID of event to return
     * @return
     * Event with ID "id"
     */
    public Event getEvent(long id) {
        Cursor cursor;
        String id_string = Long.toString(id);

        // TODO: Create query for single event here
        cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns, SQLiteHelper.COLUMN_ID + " = ?",
                new String[] {id_string}, null, null, null);

        cursor.moveToFirst();
        Event toReturn = cursorToEvent(cursor);
        cursor.close();
        return toReturn;
    }
    
    /**
     * Queries database for all events stored and creates list of Event objects
     * from returned data.
     * @return
     * List of all Event objects in database
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        List<Event> pastEvents = new ArrayList<>();
        
        // Query of all events
        Cursor cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns, null, 
                null, null, null, null);
        
        cursor.moveToFirst();
        
        // Create Event objects for each item in list
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            if (event.getEndTime().before(new Date())) {
                pastEvents.add(event);
            } else {
                events.add(event);
            }
            cursor.moveToNext();
        }
        
        cursor.close();

        for (Event event : pastEvents) {
            deleteEvent(event);
        }
        return events;
    }

    /*
     * Helper method to convert row data into Event
     */
    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        int col;
        
        // TODO: Fill event object with data from Cursor
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
        event.setId(cursor.getLong(col));
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE);
        event.setTitle(cursor.getString(col));
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_LOCATION);
        event.setLocation(cursor.getString(col));
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_START);
        event.setStartTime(cursor.getString(col));
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_END);
        event.setEndTime(cursor.getString(col));
        col = cursor.getColumnIndex(SQLiteHelper.COLUMN_DETAILS);
        event.setDetails(cursor.getString(col));
        
        return event;
    }
}
