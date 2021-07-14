package com.techelevator.model.jdbc;

import com.techelevator.model.Reservations;
import com.techelevator.model.ReservationsDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;

public class JDBCReservationsDAO implements ReservationsDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCReservationsDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Reservations addReservation(int space_id, int noOfAttendees, LocalDate startDate, int lengthOfStay, String reservedFor) {
        LocalDate endDate = startDate.plusDays(lengthOfStay);
        String sql = "INSERT INTO reservation (space_id, number_of_attendees, start_date, end_date, reserved_for)\n" +
                "VALUES(?, ?, ?, ?, ?) RETURNING reservation_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, space_id, noOfAttendees, startDate, endDate, reservedFor);
        Reservations reservation = null;
        if (result.next()) {
            reservation = new Reservations();
            reservation.setReservationId(result.getInt("reservation_id"));
            reservation.setSpaceId(space_id);
            reservation.setNumberOfAttendees(noOfAttendees);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setReservationName(reservedFor);
        }
        return reservation;
    }
}
