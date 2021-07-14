package com.techelevator.model;

import java.util.List;

public class Venue {

    private long venueId;
    private String venueName;
    private String description;
    private String city;
    private String state;
    private List<String> categoryList;

    public long getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }
}
