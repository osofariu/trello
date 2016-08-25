package com.pillartechnology.trello;

public class TrelloReportRecord {
    private String location = "";
    private String role = "";
    private String name = "";
    private Boolean stageKata = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStageKata() {
        return stageKata;
    }

    public void setStageKata(Boolean stageKata) {
        this.stageKata = stageKata;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", name, location, role);
    }


}
