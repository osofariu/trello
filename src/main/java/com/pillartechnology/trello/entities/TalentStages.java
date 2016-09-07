package com.pillartechnology.trello.entities;

import java.util.Set;

public class TalentStages {
    private Set<String> kataListIds;
    private Set<String> pairingListIds;
    private Set<String> leadershipListIds;
    private Set<String> vettedListIds;
    private Set<String> offerPendingListIds;

    public Set<String> getKataListIds() {
        return kataListIds;
    }

    public void setKataListIds(Set<String> kataListIds) {
        this.kataListIds = kataListIds;
    }

    public Set<String> getPairingListIds() {
        return pairingListIds;
    }

    public void setPairingListIds(Set<String> pairingListIds) {
        this.pairingListIds = pairingListIds;
    }

    public Set<String> getLeadershipListIds() {
        return leadershipListIds;
    }

    public void setLeadershipListIds(Set<String> leadershipListIds) {
        this.leadershipListIds = leadershipListIds;
    }

    public Set<String> getVettedListIds() {
        return vettedListIds;
    }

    public void setVettedListIds(Set<String> vettedListIds) {
        this.vettedListIds = vettedListIds;
    }

    public Set<String> getOfferPendingListIds() {
        return offerPendingListIds;
    }

    public void setOfferPendingListIds(Set<String> offerPendingListIds) {
        this.offerPendingListIds = offerPendingListIds;
    }
}
