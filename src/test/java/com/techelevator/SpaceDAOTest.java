package com.techelevator;

import com.techelevator.model.Space;
import com.techelevator.model.jdbc.JDBCSpaceDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpaceDAOTest extends DAOIntegrationTest{

    private JDBCSpaceDAO dao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        dao = new JDBCSpaceDAO(getDataSource());
    }

    @Test
    public void getAllSpacesTest() {
        String sql = "SELECT COUNT(*) from space WHERE venue_id=1";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        int sizeOfSpaces = 0;
        if (result.next()) {
            sizeOfSpaces = result.getInt("count");
        }
        assertEquals(sizeOfSpaces, dao.getAllSpaces(1).size());
    }
}
