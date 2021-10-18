package com.omug.androidlabtest1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PersonDatabase extends RoomDatabase {
    public abstract  PersonDao personDao();

    private static PersonDatabase INSTANCE;


    /*
    Creating instance of database is quite costly so we will apply a Singleton Pattern to create
    and use already instantiated single instance for every database access.
     */

    public static /*synchronized*/ PersonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PersonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PersonDatabase.class,
                            "person_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
