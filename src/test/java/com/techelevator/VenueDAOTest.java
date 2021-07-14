package com.techelevator;

import com.techelevator.model.jdbc.JDBCSpaceDAO;
import com.techelevator.model.jdbc.JDBCVenueDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static org.junit.Assert.assertEquals;

public class VenueDAOTest extends DAOIntegrationTest{

    private JDBCVenueDAO dao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        dao = new JDBCVenueDAO(getDataSource());
    }

    @Test
    public void getAllVenuesTest() {
        String sql = "SELECT COUNT(*) from venue";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        int sizeOfVenues = 0;
        if (result.next()) {
            sizeOfVenues = result.getInt("count");
        }
        assertEquals(sizeOfVenues, dao.getAllVenues().size());
    }
}
