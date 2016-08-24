package com.pillartechnology.trello;

import java.util.List;
import java.util.Map;

public class TrelloBoard {

    private String id;
    private String name;
    private String desc;
    private String descData;
    private Boolean closed;
    private String idOrganization;
    private Boolean pinned;
    private String url;
    private String shortUrl;
    private Map<String, String> prefs;
    private Map<String, String> labelNames;
    private List<TrelloCard> cards;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescData() {
        return descData;
    }

    public void setDescData(String descData) {
        this.descData = descData;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Map<String, String> getPrefs() {
        return prefs;
    }

    public void setPrefs(Map<String, String> prefs) {
        this.prefs = prefs;
    }

    public Map<String, String> getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(Map<String, String> labelNames) {
        this.labelNames = labelNames;
    }

    public List<TrelloCard> getCards() {
        return cards;
    }

    public void setCards(List<TrelloCard> cards) {
        this.cards = cards;
    }
}
