package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Reservations {

    private int reservationId;
    private int spaceId;
    private int numberOfAttendees;
    private LocalDate startDate;
    private int lengthOfReservation;
    private LocalDate endDate;
    private String reservationName;
    private BigDecimal totalCost;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getLengthOfReservation() {
        return lengthOfReservation;
    }

    public void setLengthOfReservation(int lengthOfReservation) {
        this.lengthOfReservation = lengthOfReservation;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
