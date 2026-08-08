package service;

import model.Client;
import model.QuotationRequest;
import repository.ClientRepo;
import repository.ClientRepoImpl;
import repository.QuotationRequestRepo;
import repository.QuotationRequestRepoImpl;
import java.util.List;

public class QuotationService {
    private final QuotationRequestRepo requestRepo = new QuotationRequestRepoImpl();
    private final ClientRepo clientRepo = new ClientRepoImpl();

    public String submitRequest(QuotationRequest request) {
        String validationError = validateRequest(request);
        if (validationError != null) return validationError;

        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setCompanyOrg(request.getCompanyOrg());
        client.setEmail(request.getEmail());
        client.setContactNumber(request.getContactNumber());

        int clientId = clientRepo.save(client);
        if (clientId == -1) return "FAILED: Could not save client information.";

        request.setClientId(clientId);
        boolean saved = requestRepo.save(request);

        if (saved) {
            return "SUCCESS! Your request has been saved.\nClient Reference ID: " + clientId;
        }
        return "FAILED: Could not save your request. Please try again.";
    }

    private String validateRequest(QuotationRequest request) {
        if (isBlank(request.getFullName())) return "Error: Full Name is required";
        if (request.getEmail() == null || !request.getEmail().contains("@")) return "Error: Valid Email Address is required";
        if (request.getContactNumber() == null || request.getContactNumber().length() < 7) return "Error: Valid Contact Number is required";
        if (isBlank(request.getEventName())) return "Error: Event Name is required";
        if (request.getEventType() == null) return "Error: Event Type is required";
        if (request.getEventDate() == null) return "Error: Event Date is required";
        if (isBlank(request.getEventVenue())) return "Error: Event Venue is required";
        if (request.getNumberOfHours() <= 0) return "Error: Number of Hours must be greater than zero";
        if (request.getEstimatedGuests() <= 0) return "Error: Estimated Number of Guests must be greater than zero";
        if (isBlank(request.getServicesNeeded())) return "Error: Services Needed is required";
        return null;
    }

    public String generateQuotationPreview(QuotationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append("           STORYFIRST PH - QUOTATION REQUEST            \n");
        sb.append("========================================================\n\n");
        sb.append("CLIENT INFORMATION\n");
        sb.append("Full Name      : ").append(request.getFullName()).append("\n");
        sb.append("Company/Org    : ").append(orNA(request.getCompanyOrg())).append("\n");
        sb.append("Email          : ").append(request.getEmail()).append("\n");
        sb.append("Contact Number : ").append(request.getContactNumber()).append("\n");
        sb.append("Client ID      : ").append(request.getClientId()).append("\n\n");
        sb.append("EVENT DETAILS\n");
        sb.append("Event Name     : ").append(request.getEventName()).append("\n");
        sb.append("Event Type     : ").append(request.getEventType().name().replace("_", " ")).append("\n");
        sb.append("Event Date     : ").append(request.getEventDate()).append("\n");
        sb.append("Venue          : ").append(request.getEventVenue()).append("\n");
        sb.append("Duration       : ").append(request.getNumberOfHours()).append(" hours\n");
        sb.append("Est. Guests    : ").append(request.getEstimatedGuests()).append("\n\n");
        sb.append("SERVICES REQUESTED\n");
        sb.append(request.getServicesNeeded()).append("\n\n");
        sb.append("ADDITIONAL NOTES\n");
        sb.append(orNA(request.getAdditionalNotes())).append("\n\n");
        sb.append("========================================================\n");
        sb.append("Status: PENDING - We will contact you within 24 hours.\n");
        sb.append("========================================================\n");
        return sb.toString();
    }

    public String simulateEmailNotification(QuotationRequest request) {
        return "\n[SIMULATED EMAIL NOTIFICATION]\n"
                + "To: " + request.getEmail() + "\n"
                + "Subject: Quotation Request Received - " + request.getEventName() + "\n\n"
                + "Dear " + request.getFullName() + ",\n"
                + "Thank you for choosing StoryFirst PH! We have received your\n"
                + "quotation request for \"" + request.getEventName() + "\".\n\n"
                + "Your Client Reference ID: " + request.getClientId() + "\n\n"
                + "Our team will review your details and prepare a formal price\n"
                + "quotation within 24 hours. We will contact you shortly.\n\n"
                + "- StoryFirst PH Team\n";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String orNA(String value) {
        return isBlank(value) ? "N/A" : value;
    }

    public List<QuotationRequest> getAllRequests() {
        return requestRepo.getAllRequests();
    }
}
