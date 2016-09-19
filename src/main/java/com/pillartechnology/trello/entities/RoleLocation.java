package com.pillartechnology.trello.entities;

/**
 * Created by Tyler on 9/15/16.
 */
public class RoleLocation {

    String role;
    String location;

    public RoleLocation(String role, String location){
        this.role = role;
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleLocation that = (RoleLocation) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        return location != null ? location.equals(that.location) : that.location == null;

    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
