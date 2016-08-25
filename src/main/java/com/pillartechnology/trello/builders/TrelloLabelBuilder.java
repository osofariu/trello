package com.pillartechnology.trello.builders;

import com.pillartechnology.trello.entities.TrelloLabel;

public class TrelloLabelBuilder {
    private String id;
    private String idBoard;
    private String name;
    private String color;
    private Integer uses;

    public static TrelloLabelBuilder trelloLabel() {
        return new TrelloLabelBuilder();
    }

    public TrelloLabelBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TrelloLabelBuilder idBoard(String idBoard) {
        this.idBoard = idBoard;
        return this;
    }

    public TrelloLabelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TrelloLabelBuilder color(String color) {
        this.color = color;
        return this;
    }

    public TrelloLabelBuilder uses(Integer uses) {
        this.uses = uses;
        return this;
    }

    public TrelloLabel build() {
        TrelloLabel label = new TrelloLabel();
        label.setId(id);
        label.setIdBoard(idBoard);
        label.setName(name);
        label.setColor(color);
        label.setUses(uses);
        return label;
    }
}
