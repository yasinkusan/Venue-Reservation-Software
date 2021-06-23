package com.techelevator.model;

import java.time.LocalDate;

public interface ReservationsDAO {

    public Reservations addReservation(int space_id, int noOfAttendees, LocalDate startDate, int lengthOfStay, String reservedFor);



}
