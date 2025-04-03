package org.saxion.devuurtoren.classes;

public class Team {

    private String schoolName;
    private String schoolAddress;

    public Team(String schoolName, String schoolAddress) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }
}
