package com.pillartechnology.trello.entities;

import com.pillartechnology.trello.ReportRecord;

import java.util.*;

public class TrelloCard {

    private static final Set<String> locations = new HashSet<String>(Arrays.asList("OVR", "IHR", "GLR"));
    private static final Set<String> roles = new HashSet<String>(Arrays.asList("Apprentice", "Journeyman", "Craftsman", "Delivery Lead", "Delivery Manager", "Delivery Executive", "Experience Architect", "Experience UI Designer", "Executive Consultant"));

    private String id;
    private String name;
    private String idList;
    private List<String> idMembers;
    private List<TrelloLabel> labels = new ArrayList<TrelloLabel>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public List<String> getIdMembers() {
        return idMembers;
    }

    public void setIdMembers(List<String> idMembers) {
        this.idMembers = idMembers;
    }

    public List<TrelloLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<TrelloLabel> labels) {
        this.labels = labels;
    }

    public ReportRecord makeRecord() {
        ReportRecord rec = new ReportRecord();

        rec.setName(name);

        for (TrelloLabel label : labels) {
            if (locations.contains(label.getName())) {
                rec.setLocation(label.getName());
                continue;
            }

            if (roles.contains(label.getName())) {
                rec.setRole(label.getName());
            }
        }

        return rec;
    }
}
