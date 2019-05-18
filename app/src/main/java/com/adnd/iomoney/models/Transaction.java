package com.adnd.iomoney.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.adnd.iomoney.BR;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "transactions",
        foreignKeys = @ForeignKey(
                entity = Account.class,
                parentColumns = "id",
                childColumns = "account_id"
        ),
        indices = {@Index("account_id")}
)
public class Transaction extends BaseObservable {

    private int account_id;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;

    private float value;

    private String tags;

    private boolean hasLocation;

    private String locationLabel;

    private long lat;

    private long lon;

    private Date date;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd\nMMM");
        return sdf.format(date);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isHasLocation() {
        return hasLocation;
    }

    public void setHasLocation(boolean hasLocation) {
        this.hasLocation = hasLocation;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    @Bindable
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (this.date == null || this.date.getTime() != date.getTime()) {
            this.date = date;

            notifyPropertyChanged(BR.date);
        }
    }
}
