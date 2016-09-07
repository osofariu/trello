package com.pillartechnology.trello;

public class ReportRecord {
    public static final String STAGE_KATA = "Kata";
    public static final String STAGE_PAIRING = "Pairing";
    public static final String STAGE_LEADERSHIP = "Leadership";
    public static final String STAGE_VETTED = "Fully Vetted";
    public static final String STAGE_OFFER = "Offer Pending";

    private String location = "";
    private String role = "";
    private String name = "";
    private String idList = "";
    private String listName = "";
    private String stage = "";
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

    public String getIdList() {return idList;}

    public void setIdList(String idList) {this.idList = idList;}

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

    public void setStage(String stage){
        this.stage = stage;
    }

    public String getStage(){
        return stage;
    }

    @Override
    public String toString() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", name, location, role, listName, stage);
    }
}
