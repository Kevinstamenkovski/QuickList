package com.example.QuickList;

public class User {
    int ID;
    String FULLNAME;
    String EMAIL;
    String USERNAME;

    public User(int ID, String FULLNAME, String EMAIL, String USERNAME) {
        this.ID = ID;
        this.FULLNAME = FULLNAME;
        this.EMAIL = EMAIL;
        this.USERNAME = USERNAME;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", FULLNAME='" + FULLNAME + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getUSERNAME() {
        return USERNAME;
    }
}
