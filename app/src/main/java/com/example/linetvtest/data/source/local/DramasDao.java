package com.example.linetvtest.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.linetvtest.data.Drama;

import java.util.List;

/**
 * DAO for the dramas table.
 */
@Dao
public interface DramasDao {

    @Query("SELECT * FROM Dramas")
    List<Drama> getDramas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllDramas(List<Drama> dramas);
}
