package le.minh.hau.ck01;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class},version = 1,exportSchema = false)
public abstract class ConnectDB extends RoomDatabase {
private static ConnectDB database;
private static String DATABASE_NAME = "FoodSQLite";


    public synchronized static ConnectDB getInstance(Context context) {
        if(database==null){
            database = Room.databaseBuilder(context.getApplicationContext(),ConnectDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract FoodDAO foodDAO();
}
