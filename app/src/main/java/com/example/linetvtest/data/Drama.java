package com.example.linetvtest.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Final model class for a Drama.
 */

@Entity(tableName = "dramas")
public final class Drama {

    @PrimaryKey
    @ColumnInfo(name = "dramaid")
    @SerializedName("drama_id")
    private final int id;


    @ColumnInfo(name = "name")
    @Nullable
    @SerializedName("name")
    private final String name;

    @ColumnInfo(name = "totalviews")
    @SerializedName("total_views")
    private final int totalViews;

    @ColumnInfo(name = "createdat")
    @SerializedName("created_at")
    private final Date createdAt;

    @NonNull
    @ColumnInfo(name = "thumb")
    @SerializedName("thumb")
    private final String thumb;

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    private final double rating;

    /**
     * The constructor to build the data
     *
     * @param id            id of the drama
     * @param name          name of the drama
     * @param totalViews    count of the user hit
     * @param createdAt     time which the drama created
     * @param thumb         url of the drama pic
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

    @NonNull
    public String getThumb() {
        return thumb;
    }

    public double getRating() {
        return rating;
    }
}
