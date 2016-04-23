package edu.westga.cs6242.budgetingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Represents a MonthlyBudget
 * @author Patrick Dean
 * @version 1
 */
public class MonthlyBudget implements Parcelable {
    private int id;
    private String title;
    private String description;
    private Date dateCreated;
    private Date dateUpdated;
    private int userId;

    public MonthlyBudget() {
        this(0, "", new Date(), new Date(), 0);
    }

    public MonthlyBudget(int id, String title, Date dateCreated, Date dateUpdated, int userId) {
        this.id = id;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.userId = userId;
    }

    protected MonthlyBudget(Parcel in) {
        this(in.readInt(),
                in.readString(),
                new Date(in.readLong()),
                new Date(in.readLong()),
                in.readInt());
    }

    public static final Creator<MonthlyBudget> CREATOR = new Creator<MonthlyBudget>() {
        @Override
        public MonthlyBudget createFromParcel(Parcel in) {
            return new MonthlyBudget(in);
        }

        @Override
        public MonthlyBudget[] newArray(int size) {
            return new MonthlyBudget[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getId()) + " - " +  this.getTitle();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dateCreated.getTime());
        dest.writeLong(dateUpdated.getTime());
        dest.writeInt(userId);
    }
}
