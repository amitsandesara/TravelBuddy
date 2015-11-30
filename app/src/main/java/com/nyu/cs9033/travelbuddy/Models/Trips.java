package com.nyu.cs9033.travelbuddy.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
@ParseClassName("Trips")
public class Trips extends ParseObject implements Parcelable {
	
	// Member fields should exist here, what else do you need for a trip?
	// Please add additional fields
	//private String trip_Name;
	private String trip_Location;
	private String trip_Date;
	private String trip_Details;
	private String friends;
	private String createdBy;
	private byte[] destination;
    private static final String TAG = "Trips";


	public String getTrip_Location() {
		return trip_Location;
	}

	public void setTrip_Location(String trip_Location) {
		this.trip_Location = trip_Location;
	}

	public String getTrip_Details() {
		return trip_Details;
	}

	public void setTrip_Details(String trip_Details) {
		this.trip_Details = trip_Details;
	}

	public String getTrip_Date() {
		return trip_Date;
	}

	public void setTrip_Date(String trip_Date) {
		this.trip_Date = trip_Date;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

	/**
	 * Parcelable creator. Do not modify this function.
	 */
	public static final Creator<Trips> CREATOR = new Creator<Trips>() {
		public Trips createFromParcel(Parcel p) {
			return new Trips(p);
		}

		public Trips[] newArray(int size) {
			return new Trips[size];
		}
	};
	
	/**
	 * Create a Trips model object from a Parcel. This
	 * function is called via the Parcelable creator.
	 * 
	 * @param p The Parcel used to populate the
	 * Model fields.
	 */

	public Trips(){
	}

	public Trips(Parcel p) {
		//trip_Name = p.readString();
		trip_Location = p.readString();
		trip_Details = p.readString();
		trip_Date = p.readString();
		friends = p.readString();
		createdBy = p.readString();
		// TODO - fill in here
	}
	
	/**
	 * Create a Trips model object from arguments
	 *
	 * @param name  Add arbitrary number of arguments to
	 * instantiate Trips class based on member variables.
	 */
	public Trips(String trip_Location, String trip_Details, String trip_Date, String friends, String createdBy) {
		//this.trip_Name = trip_Name;
		this.trip_Location = trip_Location;
		this.trip_Details = trip_Details;
		this.trip_Date = trip_Date;
		this.friends = friends;
		this.createdBy = createdBy;

		// TODO - fill in here, please note you must have more arguments here
	}

	/**
	 * Serialize Trips object by using writeToParcel.
	 * This function is automatically called by the
	 * system when the object is serialized.
	 * 
	 * @param dest Parcel object that gets written on 
	 * serialization. Use functions to write out the
	 * object stored via your member variables. 
	 * 
	 * @param flags Additional flags about how the object 
	 * should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE.
	 * In our case, you should be just passing 0.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		applyDefaultValues();

		//dest.writeString(trip_Name);
		dest.writeString(trip_Location);
		dest.writeString(trip_Details);
		dest.writeString(trip_Date);
		dest.writeString(friends);
		dest.writeString(createdBy);
		// TODO - fill in here 
	}

	private void applyDefaultValues() {
		if(trip_Details == null) {
			trip_Details = "";
		}
		if (trip_Location == null){
			trip_Location = "";
		}
		if (friends == null){
			friends = "";
		}
		if (trip_Date == null){
			trip_Date = "";
		}
        if (createdBy == null)
        {
            createdBy = "";
        }
	}

	/**
	 * Feel free to add additional functions as necessary below.
	 */
	
	/**
	 * Do not implement
	 */
	@Override
	public int describeContents() {
		// Do not implement!
		return 0;
	}


	public byte[] getDestination() {
		return destination;
	}


}
