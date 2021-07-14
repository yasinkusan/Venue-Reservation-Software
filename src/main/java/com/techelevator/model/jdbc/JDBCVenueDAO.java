package com.techelevator.model.jdbc;


import com.techelevator.model.Venue;
import com.techelevator.model.VenueDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCVenueDAO implements VenueDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCVenueDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Venue> getAllVenues() {
        List<Venue> venueList = new ArrayList<>();
        String sql = "SELECT venue.id, venue.name, venue.city_id, venue.description, city.name, city.state_abbreviation " +
                "FROM venue " +
                "JOIN city ON city.id=venue.city_id " +
                "ORDER BY venue.name";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Venue venue = mapRowToVenue(results);
            venueList.add(venue);
        }
        return venueList;
    }

    private List<String> getCategories(long id) {
        List<String> categoryList = new ArrayList<>();
        String sql = "SELECT category.name " +
                "FROM category " +
                "JOIN category_venue on category_venue.category_id = category.id " +
                "JOIN venue on venue.id=category_venue.venue_id " +
                "WHERE venue.id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);


        while (results.next()) {
            String category = results.getString("name");
            categoryList.add(category);
        }


        return categoryList;
    }


    private Venue mapRowToVenue(SqlRowSet results) {
        Venue venue = new Venue();
        venue.setVenueId(results.getInt("id"));
        venue.setVenueName(results.getString("name"));
        venue.setDescription(results.getString("description"));
        venue.setCity(results.getString("name"));
        venue.setState(results.getString("state_abbreviation"));
        venue.setCategoryList(getCategories(venue.getVenueId()));


        return venue;
    }


}
