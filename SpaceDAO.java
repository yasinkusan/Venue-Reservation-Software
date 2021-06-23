package com.techelevator.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SpaceDAO {

    public List<Space> getAllSpaces(long venueNumber);

    public List<Space> getAvailableSpaces(long venueNumber, LocalDate startDate, int lengthOfReservation, int numberOfAttendees);

}
