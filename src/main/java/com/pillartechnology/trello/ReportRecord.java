package com.pillartechnology.trello;

public class ReportRecord {
    private String location = "";
    private String role = "";
    private String name = "";
    private String listName = "";
    private Boolean stageKata = false;
    private Boolean stageLeadership = false;
    private Boolean stageHired = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListName() {return listName;}

    public void setListName(String listName) {this.listName = listName;}

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
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", name, location, role, listName, getStage());
    }

    private String getStage() {
        if (stageKata) return "kata";
        if (stageLeadership) return "leadership";
        if (stageHired) return "hired";
        return "";
    }

    public Boolean getStageLeadership() {
        return stageLeadership;
    }

    public void setStageLeadership(Boolean stageLeadership) {
        this.stageLeadership = stageLeadership;
    }

    public Boolean getStageHired() {
        return stageHired;
    }

    public void setStageHired(Boolean stageHired) {
        this.stageHired = stageHired;
    }
}
