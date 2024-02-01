package pl.edu.pb.wi.bankpb.database;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseInitializer {
    private static AppDatabase database;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static synchronized AppDatabase getInstance(final Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .build();
        }
        return database;
    }
}
