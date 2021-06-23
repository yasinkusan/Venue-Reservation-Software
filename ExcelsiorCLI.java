package com.techelevator;

import javax.sql.DataSource;
import com.techelevator.model.*;
import com.techelevator.model.jdbc.JDBCReservationsDAO;
import com.techelevator.model.jdbc.JDBCSpaceDAO;
import com.techelevator.model.jdbc.JDBCVenueDAO;
import com.techelevator.view.UIMenu;
import org.apache.commons.dbcp2.BasicDataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExcelsiorCLI {
    private UIMenu menu;
    private VenueDAO venueDAO;
    private SpaceDAO spaceDAO;
    private ReservationsDAO reservationsDAO;

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
        application.run();
    }

    public ExcelsiorCLI(DataSource datasource) {
        venueDAO = new JDBCVenueDAO(datasource);
        spaceDAO = new JDBCSpaceDAO(datasource);
        reservationsDAO = new JDBCReservationsDAO(datasource);
        menu = new UIMenu();
    }

    public void run() {
        menu.printWelcome();
        boolean mainMenuRunning = true;
        while (mainMenuRunning) {
            String choice = menu.printMainMenu();
            if (choice.equals("1")) {
                handleVenueSelection();
            } else if (choice.equalsIgnoreCase("Q")) {
                mainMenuRunning = false;
            } else {
                menu.printInvalidSelection();
            }
        }
    }

    private void handleVenueSelection() {
        boolean venueSubMenuIsRunning = true;
        while (venueSubMenuIsRunning) {
            List<Venue> listOfVenues = venueDAO.getAllVenues();
            listVenues(listOfVenues);
            String userChoice = menu.getVenueSelection();
            if (userChoice.equalsIgnoreCase("R")) {
                venueSubMenuIsRunning = false;
            }
//            else if () { if it is another letter or not one if the venues
//            }
            else if (Integer.parseInt(userChoice) <= venueDAO.getAllVenues().size()) {
                printVenueDetails(Integer.parseInt(userChoice));
                handleSpaceDetails();
            } else {
                menu.printInvalidSelection();
            }
        }
    }

    private void printVenueDetails(int userChoice) {
        System.out.println(venueDAO.getAllVenues().get(userChoice - 1).getVenueName());
        System.out.println("Location: " + venueDAO.getAllVenues().get(userChoice - 1).getCity() + ", " + venueDAO.getAllVenues().get(userChoice - 1).getState());
        System.out.println("Categories: " + venueDAO.getAllVenues().get(userChoice - 1).getCategoryList() + "\n");
        System.out.println("Description: " + venueDAO.getAllVenues().get(userChoice - 1).getDescription() + "\n");
    }

    private void handleSpaceDetails() {
        boolean handleSpaceDetailsRunning = true;
        while (handleSpaceDetailsRunning) {
            String spaceChoice = menu.getSpaceSelection();
            if (spaceChoice.equalsIgnoreCase("R")) {
                handleSpaceDetailsRunning = false;
            }
//            else if () { if it is another letter or not one if the venues
//            }
//            else if (!spaceChoice.equalsIgnoreCase("R") && (Integer.parseInt(spaceChoice)<=0 || Integer.parseInt(spaceChoice)>listVenueSpaces(Integer.parseInt(spaceChoice)).size())) {
//                menu.printInvalidSelection();
//            }
            else if (spaceChoice.equalsIgnoreCase("1")) {
                printVenueSpaces(listVenueSpaces(Integer.parseInt(spaceChoice)));
                String userResponse = menu.askUserToReserveASpace();
                if (userResponse.equalsIgnoreCase("R")) {
                    handleSpaceDetailsRunning=false;
                }
                if (userResponse.equals("1")) {
                    reserveASpace(Integer.parseInt(spaceChoice));
//                handleSpaceDetailsRunning=false;
                } else if (!userResponse.equalsIgnoreCase("R") && !userResponse.equals("1")) {
                    menu.printInvalidSelection();
                }
            }
        }
    }

    private void reserveASpace(long venueId) {
        LocalDate startDate = LocalDate.parse(menu.askUserForStartDate());
        int lengthOfStay = Integer.parseInt(menu.askUserLengthOfReservation());
        int numberOfAttendees = Integer.parseInt(menu.asUserNumberOfAttendees());
        menu.availableSpacesFound();
        printAvailableSpaces(venueId, startDate, lengthOfStay, numberOfAttendees);
        int spaceId = Integer.parseInt(menu.askUserSpaceToReserve());
        String reservedFor = menu.askUserReservationName();
        int reservationId = reservationsDAO.addReservation(spaceId, numberOfAttendees, startDate, lengthOfStay, reservedFor).getReservationId();
        printConfirmation(reservationId, venueId, spaceId, reservedFor, numberOfAttendees, startDate, lengthOfStay);
    }

    private List<Space> listVenueSpaces(int venueId) {
        List<Space> spaces;
        spaces = spaceDAO.getAllSpaces(venueId);
        return spaces;
    }

    private void printAvailableSpaces(long venueId, LocalDate startDate, int lengthOfStay, int numberOfAttendees) {
        List<Space> availableSpaces = spaceDAO.getAvailableSpaces(venueId, startDate, lengthOfStay, numberOfAttendees);
        System.out.printf("%-8s %-20s %-20s %-12s %-15s %s\n", "Space #", "Space Name", "Daily Rate", "Max Occup.", "Accessible?", "Total Cost");
        for (Space space : availableSpaces) {
//            printSpaceDetails(space);
            System.out.printf("%-8s %-20s $%-19s %-12d %-15s $%.2f\n", space.getSpaceId(), space.getSpaceName(), space.getDailyRate(), space.getMaxOccupancy(), space.isAccessible(), space.getDailyRate().multiply(BigDecimal.valueOf(lengthOfStay)));
        }
        System.out.println();

    }

    private void listVenues(List<Venue> listOfVenues) {
        System.out.printf("\n");
        int optionNumber = 1;
        for (Venue venue : listOfVenues) {
            System.out.println(Integer.toString(optionNumber) + ") " + venue.getVenueName());
            optionNumber += 1;
        }
        System.out.println("R) Return to Previous Screen\n");
    }

    private void printVenueSpaces(List<Space> spaceList) {
        System.out.printf("%-40s %-25s %-28s %-31s %-35s \n", "   Name", "Open", "Close", "Daily Rate", "Max. Occupancy");
        int optionNumber = 1;
        for (Space space : spaceList) {
//            printSpaceDetails(space);
            System.out.printf("#%s %-37s %-25s %-28s %-31.2f %-35d \n", Integer.toString(optionNumber), space.getSpaceName(), space.getOpenFrom(), space.getOpenTo(), space.getDailyRate(), space.getMaxOccupancy());
            optionNumber += 1;
        }
        System.out.println();
    }

    private void printConfirmation(int reservationId, long venueId, int spaceId, String reservedFor, int numberOfAttendees, LocalDate startDate, int lengthOfStay) {
        System.out.println("Thanks for submitting your reservation! The details for your event are listed below:\n");
        System.out.printf("%15s %s\n", "Confirmation #:", reservationId);
        System.out.printf("%15s %s\n", "Venue:", venueId);
        System.out.printf("%15s %s\n", "Space:", spaceId);
        System.out.printf("%15s %s\n", "Reserved For:", reservedFor);
        System.out.printf("%15s %s\n", "Attendees:", numberOfAttendees);
        System.out.printf("%15s %s\n", "Arrival Date:", startDate);
        System.out.printf("%15s %s\n", "Depart Date:", startDate.plusDays(lengthOfStay));
        System.out.printf("%15s %s\n\n", "Total Cost:", lengthOfStay*100);
    }
}


