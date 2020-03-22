package com.example.linetvtest.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.linetvtest.data.Drama;


/**
 * The Room Database that contains the Drama table.
 */
@Database(entities = {Drama.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LinetvTestDatabase extends RoomDatabase {

    private static LinetvTestDatabase INSTANCE;

    public abstract DramasDao dramasDao();

    private static final Object lock = new Object();

    public static LinetvTestDatabase getInstance(Context context) {
        synchronized (lock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LinetvTestDatabase.class, "Dramas.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
