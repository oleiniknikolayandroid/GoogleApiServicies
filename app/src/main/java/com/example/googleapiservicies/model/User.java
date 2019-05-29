package com.example.googleapiservicies.model;



import android.os.Build;

import java.util.Objects;

public class User {
    public String mName;
    public String nSurnme;
    public String mUrl;
    public String mEmail;

    public User() {}

    public User(String mName, String mSurname, String mUrl, String mEmail) {
        this.mName = mName;
        this.nSurnme = mSurname;
        this.mUrl = mUrl;
        this.mEmail = mEmail;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getnSurnme() {
        return nSurnme;
    }

    public void setnSurnme(String nSurnme) {
        this.nSurnme = nSurnme;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(mName, user.mName) &&
                    Objects.equals(nSurnme, user.nSurnme) &&
                    Objects.equals(mUrl, user.mUrl) &&
                    Objects.equals(mEmail, user.mEmail);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(mName, nSurnme, mUrl, mEmail);
        }
        return -1;
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", nSurnme='" + nSurnme + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
