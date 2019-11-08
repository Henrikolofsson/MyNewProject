package se.mau.ai0026.mynewproject.Controller;

import android.content.Context;

import java.util.List;

import androidx.room.Room;
import se.mau.ai0026.mynewproject.Database.Database;
import se.mau.ai0026.mynewproject.Database.DatabaseAccess;
import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.Entities.User;

public class DBAccess {
    private static final String DATABASE_NAME = "MyNewProject";
    private Database database;
    private DatabaseAccess databaseAccess;
    private Controller controller;

    public DBAccess(Context context, Controller controller) {
        database = Room.databaseBuilder(context, Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        databaseAccess = database.databaseAccess();
        this.controller = controller;
    }

    public void addUser(User user) {
        databaseAccess.addUser(user);
    }

    public int getUserId(String name, String password) {
        return databaseAccess.getUserId(name, password);
    }

    public String getName(String name) {
        return databaseAccess.getName(name);
    }

    public List<User> getUsers() {
        return databaseAccess.getUsers();
    }

    public List<DataStepModel> getStepEntries(int userId) {
        return databaseAccess.getStepEntries(userId);
    }

    public void addStepEntry(DataStepModel dataStepModel) {
        databaseAccess.addStepEntry(dataStepModel);
    }

    public void incrementSteps(int userId, String todaysDate) {
        databaseAccess.incrementSteps(userId, todaysDate);
    }

    public int getSteps(int userId, String date) {
        return databaseAccess.getTodaysSteps(userId, date);
    }

    public void resetSteps(int id) {
        databaseAccess.resetSteps(id);
    }

}
