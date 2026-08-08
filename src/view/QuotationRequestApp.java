/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.EventType;
import model.QuotationRequest;
import service.QuotationService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class QuotationRequestApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final QuotationService service = new QuotationService();

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("        STORYFIRST PH — EVENT QUOTATION REQUEST          ");
        System.out.println("========================================================");
        System.out.println("Please fill in all required details below.\n");

        QuotationRequest request = collectInput();

        System.out.println("\nSubmitting your request...");
        String result = service.submitRequest(request);
        System.out.println("\n" + result);

        if (result.startsWith("SUCCESS")) {
            System.out.println("\n" + service.generateQuotationPreview(request));
            System.out.println(service.simulateEmailNotification(request));
        }

        System.out.println("\nThank you! You may close this window.");
        scanner.close();
    }

    // Collect all user input from console
    private static QuotationRequest collectInput() {
        QuotationRequest req = new QuotationRequest();

        System.out.print("Full Name *: ");
        req.setFullName(scanner.nextLine().trim());

        System.out.print("Company / Organization (optional): ");
        req.setCompanyOrg(scanner.nextLine().trim());

        System.out.print("Email Address *: ");
        req.setEmail(scanner.nextLine().trim());

        System.out.print("Contact Number *: ");
        req.setContactNumber(scanner.nextLine().trim());

        System.out.print("Event Name *: ");
        req.setEventName(scanner.nextLine().trim());

        // Show menu for Event Type selection
        System.out.println("\n--- Select Event Type ---");
        EventType[] types = EventType.values();
        for (int i = 0; i < types.length; i++) {
            String displayName = types[i].name().replace("_", " ");
            System.out.printf("  %d. %s%n", i + 1, displayName);
        }
        System.out.print("Enter your choice: ");
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= types.length) {
                    req.setEventType(types[choice - 1]);
                    break;
                }
                System.out.printf("Please enter a number between 1 and %d: ", types.length);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }

        req.setEventDate(readDateInput());

        System.out.print("Event Venue *: ");
        req.setEventVenue(scanner.nextLine().trim());

        req.setNumberOfHours(readIntInput("Number of Hours *: "));
        req.setEstimatedGuests(readIntInput("Estimated Number of Guests *: "));

        System.out.print("Services Needed * (e.g. Photography, Video, Styling): ");
        req.setServicesNeeded(scanner.nextLine().trim());

        System.out.print("Additional Notes / Requirements: ");
        req.setAdditionalNotes(scanner.nextLine().trim());

        return req;
    }

    // Read and validate date input
    private static LocalDate readDateInput() {
        while (true) {
            System.out.print("Event Date * (YYYY-MM-DD, e.g. 2026-12-25): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    // Read and validate numeric input
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) return value;
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
