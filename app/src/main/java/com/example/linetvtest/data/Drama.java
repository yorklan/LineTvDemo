package com.example.linetvtest.data;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Final model class for a Drama.
 */

@Entity(tableName = "dramas")
public final class Drama implements Parcelable {

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

    protected Drama(Parcel in) {
        id = in.readInt();
        name = in.readString();
        totalViews = in.readInt();
        createdAt = new Date(in.readLong());
        thumb = Objects.requireNonNull(in.readString());
        rating = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(totalViews);
        dest.writeLong(createdAt.getTime());
        dest.writeString(thumb);
        dest.writeDouble(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drama> CREATOR = new Creator<Drama>() {
        @Override
        public Drama createFromParcel(Parcel in) {
            return new Drama(in);
        }

        @Override
        public Drama[] newArray(int size) {
            return new Drama[size];
        }
    };

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

    public String getCreatedAtStringSimple() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy / MM / dd", Locale.getDefault());
        return simpleDateFormat.format(createdAt);
    }

    public String getCreatedAtString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        return simpleDateFormat.format(createdAt);
    }

    public String getTotalViewsString() {
        return String.valueOf(totalViews);
    }

    public String getRatingStringDF2() {
        DecimalFormat df2 = new DecimalFormat("#.##");
        return df2.format(rating);
    }

    public String getRatingString() {
        return Double.toString(rating);
    }
}
