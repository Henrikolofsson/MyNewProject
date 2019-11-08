package se.mau.ai0026.mynewproject.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.Entities.User;

@Dao
public interface DatabaseAccess {
    @Insert
    void addUser(User... user);

    @Query("SELECT id FROM user_table WHERE name= :name AND password= :password")
    int getUserId(String name, String password);

    @Query("SELECT name FROM user_table WHERE name= :name")
    String getName(String name);

    @Query("SELECT * FROM user_table")
    List<User> getUsers();

    @Query("SELECT * FROM data_step_model WHERE user_id= :user_id")
    List<DataStepModel> getStepEntries(int user_id);

    @Insert
    void addStepEntry(DataStepModel... dataStepModel);

    @Query("UPDATE data_step_model SET steps=steps+1 WHERE user_id= :id AND date =:date")
    void incrementSteps(int id, String date);

    @Query("SELECT steps FROM data_step_model WHERE user_id=:id AND date= :date")
    int getTodaysSteps(int id, String date);

    @Query("UPDATE data_step_model SET steps= 0 WHERE user_id = :id")
    void resetSteps(int id);


}
