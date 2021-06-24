package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Space {

    private int spaceId;
    private long venueId;
    private String spaceName;
    private boolean isAccessible;
    private BigDecimal dailyRate;
    private int maxOccupancy;
    private String openFrom = "";
    private String openTo = "";

    public int getSpaceId() {
        return spaceId;
    }

    public long getVenueId() { return venueId; }

    public String getSpaceName() {
        return spaceName;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public void setVenueId(long venueId) { this.venueId = venueId; }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public void setAccessible(boolean accessible) {
        this.isAccessible = accessible;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }
}
