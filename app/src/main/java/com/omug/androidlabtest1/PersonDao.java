package com.omug.androidlabtest1;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    List<Person>  getAll();

    /*
     * Insert the object in database
     * @param person, object to be inserted
     */
    @Insert
    long insertPerson(Person person);

    /*
     * update the object in database
     * @param person, object to be updated
     */
    @Update
    void update(Person updPerson);

    /*
     * delete the object from database
     * @param person, object to be deleted
     */
    @Delete
    void delete(Person person);

}
