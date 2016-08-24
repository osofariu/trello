package com.pillartechnology.trello;

public class TrelloLabel {

    private String id;
    private String idBoard;
    private String name;
    private String color;
    private Integer uses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getUses() {
        return uses;
    }

    public void setUses(Integer uses) {
        this.uses = uses;
    }

    public static TrelloLabel createLabel(String labelName) {
        TrelloLabel l = new TrelloLabel();
        l.setName(labelName);
        return l;
    }
}
