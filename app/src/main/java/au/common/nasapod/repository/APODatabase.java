package au.common.nasapod.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import au.common.nasapod.model.APODFavourite;
import au.common.nasapod.model.APODModel;

@Database(entities = {APODModel.class, APODFavourite.class}, version = 1)
public abstract class APODatabase extends RoomDatabase {
    public abstract APODao apodDao();

    private static volatile APODatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static APODatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (APODatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            APODatabase.class, "apod_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}