package com.example.linetvtest.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Final model class for a Task.
 */

@Entity(tableName = "dramas")
public final class Drama {

    @PrimaryKey
    @ColumnInfo(name = "dramaid")
    private final int id;


    @ColumnInfo(name = "name")
    @Nullable
    private final String name;

    @ColumnInfo(name = "totalviews")
    private final int totalViews;

    @ColumnInfo(name = "createdat")
    private final Date createdAt;

    @NonNull
    @ColumnInfo(name = "thumb")
    private final String thumb;

    @ColumnInfo(name = "rating")
    private final double rating;

    /**
     * The constructor to build the data
     *
     * @param id            id of the drama
     * @param name          name of the drama
     * @param totalViews    count of the user view
     * @param rating        average count of the user rate
     */
    public Drama(int id, @Nullable String name,
                 int totalViews, Date createdAt,
                 @NonNull String thumb, double rating) {
        this.id = id;
        this.name = name;
        this.totalViews = totalViews;
        this.createdAt = createdAt;
        this.thumb = thumb;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public String getCreatedAtString() {
        // FIXME
        return "";
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getThumb() {
        return thumb;
    }

    public double getRating() {
        return rating;
    }
}
