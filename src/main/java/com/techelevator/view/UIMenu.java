package com.techelevator.view;

import com.techelevator.model.Venue;


import java.util.List;
import java.util.Scanner;

public class UIMenu {

    private Scanner scanner;

    public UIMenu() {
        scanner = new Scanner(System.in);

    }

    public void printWelcome() {
        System.out.println("Welcome to Excelsior Venues!\n");

    }

    public String printMainMenu() {

        System.out.println("What would you like to do?\n");
        System.out.println("1) List Venues");
        System.out.println("Q) Quit\n");

        return scanner.nextLine();
    }

    public void printInvalidSelection() {
        System.out.println("Invalid selection.\n");
    }

    public String getVenueSelection() {
        System.out.println("Which venue would you like to view?\n");
        return scanner.nextLine();
    }

    public String getSpaceSelection() {
        System.out.println("What would you like to do next?");
        System.out.println("1) View Spaces");
        System.out.println("R) Return to previous screen");
        return scanner.nextLine();
    }

    public String askUserToReserveASpace() {
        System.out.println("What would you like to do next?\n");
        System.out.println("1) Reserve a Space");
        System.out.println("R) Return to Previous Screen\n");
        return scanner.nextLine();
    }

    public String askUserForStartDate() {
        System.out.println("When do you need the space?");
        return scanner.nextLine();
    }

    public String askUserLengthOfReservation() {
        System.out.println("How many days will you need the space?");
        return scanner.nextLine();
    }

    public String asUserNumberOfAttendees() {
        System.out.println("How many people will be in attendance?");
        return scanner.nextLine();
    }

    public void availableSpacesFound() {
        System.out.println("The following spaces are available based on your needs:\n");
    }

    public String askUserSpaceToReserve() {
        System.out.println("Which space would you like to reserve (enter 0 to cancel)?");
        return scanner.nextLine();
    }

    public String askUserReservationName() {
        System.out.println("Who is this reservation for?");
        return scanner.nextLine();
    }
}
