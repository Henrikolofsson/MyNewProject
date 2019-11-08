package se.mau.ai0026.mynewproject.Database;

import androidx.room.RoomDatabase;
import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.Entities.User;

@androidx.room.Database(entities = {User.class, DataStepModel.class}, version = 10, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DatabaseAccess databaseAccess();

}
