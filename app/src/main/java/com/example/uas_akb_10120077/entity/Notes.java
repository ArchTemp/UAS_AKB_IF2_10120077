package com.example.uas_akb_10120077.entity;

/**
 * NAMA    : Mohammad Noor Ihsan Akbar
 * NIM     : 10120077
 * Kelas   : IF-2
 * MatKul  : Aplikasi Komputasi Bergerak
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private String date;
    private String title;
    private String category;
    private String content;

    // Default constructor (required by Firebase Realtime Database)
    public Notes() {
        // Default constructor required for Firebase Realtime Database.
        // Do not remove this empty constructor.
    }

    // Constructors
    public Notes(String date, String title, String category, String content) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.content = content;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes>() {
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    private Notes(Parcel in) {
        date = in.readString();
        title = in.readString();
        category = in.readString();
        content = in.readString();
    }
}