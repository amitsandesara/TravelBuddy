package com.nyu.cs9033.travelbuddy.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	
	// Member fields should exist here, what else do you need for a person?
	// Please add additional fields
	private String name;
	private String current_Location;
	private String friends;

    public String getName() {
        return name;
    }

    public void setName(String fname) {
        this.name = name;
    }

    public String getCurrent_Location() {
        return current_Location;
    }

    public void setCurrent_Location(String current_Location) {
        this.current_Location = current_Location;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    /**
	 * Parcelable creator. Do not modify this function.
	 */
	public static final Creator<Person> CREATOR = new Creator<Person>() {
		public Person createFromParcel(Parcel p) {
			return new Person(p);
		}

		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
	
	/**
	 * Create a Person model object from a Parcel. This
	 * function is called via the Parcelable creator.
	 * 
	 * @param p The Parcel used to populate the
	 * Model fields.
	 */

    public Person() {

    }

	public Person(Parcel p) {
		name = p.readString();
        current_Location = p.readString();
        friends = p.readString();
		// TODO - fill in here
		
	}
	
	/**
	 * Create a Person model object from arguments
	 * 
	 * @param name Add arbitrary number of arguments to
	 * instantiate Person class based on member variables.
	 */
	public Person(String name, String current_Location, String friends) {
		this.name = name;
        this.current_Location = current_Location;
        this.friends = friends;

		// TODO - fill in here, please note you must have more arguments here
	}

	/**
	 * Serialize Person object by using writeToParcel.  
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

        dest.writeString(name);
        dest.writeString(current_Location);
        dest.writeString(friends);
		// TODO - fill in here
	}

    private void applyDefaultValues() {
        if (friends == null) {
            friends = "";
        }
        if (current_Location == null){
            current_Location = "";
        }
        if (name == null){
            name = "";
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

}
