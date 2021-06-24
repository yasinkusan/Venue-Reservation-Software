package com.techelevator.model.jdbc;

import com.techelevator.model.Space;
import com.techelevator.model.SpaceDAO;
import com.techelevator.model.Venue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.math.BigDecimal;
import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCSpaceDAO implements SpaceDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCSpaceDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Space> getAllSpaces(long venueNumber) {
        List<Space> spaceList = new ArrayList<>();
        String sql = "SELECT space.id, venue_id, space.name, is_accessible, to_char(to_date(open_from::text, 'MM'), 'Mon') as \"open_from\", to_char(to_date(open_to::text, 'MM'), 'Mon') as \"open_to\", CAST(daily_rate AS decimal(34, 2)), max_occupancy " +
                "FROM space " +
                "JOIN venue on venue.id=space.venue_id " +
                "WHERE venue.id=?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, venueNumber);
        while (results.next()) {
            Space space = mapRowToSpace(results);
            spaceList.add(space);
        }

        return spaceList;
    }


    @Override
    public List<Space> getAvailableSpaces(long venueNumber, LocalDate startDate, int lengthOfReservation, int numberOfAttendees) {
        LocalDate endDate = startDate.plusDays(lengthOfReservation);
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();

        List<Space> availableSpaces = new ArrayList<>();
        String sql = "SELECT s.id, s.venue_id, s.name, s.is_accessible, s.open_from, s.open_to,CAST(daily_rate as decimal(25,2)), s.max_occupancy\n" +
                "FROM space s \n" +
                "WHERE s.venue_id = ? AND (? >= open_from OR open_from IS NULL) AND (? < open_to OR open_to IS NULL) \n" +
                "AND s.id NOT IN (SELECT space.id FROM space\n" +
                "JOIN reservation r ON r.space_id = space.id\n" +
                "WHERE space.max_occupancy < ?\n" +
                "OR (? <= r.end_date AND ? >= r.start_date)) " +
                "ORDER BY daily_rate DESC\n" +
                "LIMIT 5";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, venueNumber, startMonth, endMonth, numberOfAttendees, startDate, endDate);
        while(results.next()) {
            Space space = mapRowToSpace(results);
            availableSpaces.add(space);
        }

        return availableSpaces;
    }

    private Space mapRowToSpace(SqlRowSet results) {
        Space space = new Space();
        space.setSpaceId(results.getInt("id"));
        space.setVenueId(results.getLong("venue_id"));
        space.setSpaceName(results.getString("name"));
        if (results.getString("open_from")!=null) {
            space.setOpenFrom(results.getString("open_from"));
        }
        if (results.getString("open_to")!=null) {
            space.setOpenTo(results.getString("open_to"));
        }
        space.setDailyRate(results.getBigDecimal("daily_rate"));
        space.setMaxOccupancy(results.getInt("max_occupancy"));
        space.setAccessible(results.getBoolean("is_accessible"));
        return space;
    }
}
