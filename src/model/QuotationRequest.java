
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuotationRequest {
    private Integer requestId;
    private Integer clientId;
    private String eventName;
    private EventType eventType;
    private LocalDate eventDate;
    private String eventVenue;
    private int numberOfHours;
    private int estimatedGuests;
    private String servicesNeeded;
    private String additionalNotes;
    private LocalDateTime submittedAt;
    private QuotationStatus status;

    // Temporary fields — used for form display only
    private String fullName;
    private String companyOrg;
    private String email;
    private String contactNumber;

    public QuotationRequest() {
        this.status = QuotationStatus.PENDING;
    }

    public Integer getRequestId() { 
        return requestId; 
    }
    public void setRequestId(Integer requestId) { 
        this.requestId = requestId; 
    }
    public Integer getClientId() { 
        return clientId; 
    }
    public void setClientId(Integer clientId) { 
        this.clientId = clientId; 
    }
    public String getEventName() { 
        return eventName; 
    }
    public void setEventName(String eventName) { 
        this.eventName = eventName; 
    }
    public EventType getEventType() { 
        return eventType; 
    }
    public void setEventType(EventType eventType) { 
        this.eventType = eventType; 
    }
    public LocalDate getEventDate() { 
        return eventDate; 
    }
    public void setEventDate(LocalDate eventDate) { 
        this.eventDate = eventDate; 
    }
    public String getEventVenue() { 
        return eventVenue; 
    }
    public void setEventVenue(String eventVenue) { 
        this.eventVenue = eventVenue; 
    }
    public int getNumberOfHours() { 
        return numberOfHours; 
    }
    public void setNumberOfHours(int numberOfHours) { 
        this.numberOfHours = numberOfHours; 
    }
    public int getEstimatedGuests() { 
        return estimatedGuests; 
    }
    public void setEstimatedGuests(int estimatedGuests) { 
        this.estimatedGuests = estimatedGuests; 
    }
    public String getServicesNeeded() { 
        return servicesNeeded; 
    }
    public void setServicesNeeded(String servicesNeeded) { 
        this.servicesNeeded = servicesNeeded; 
    }
    public String getAdditionalNotes() { 
        return additionalNotes; 
    }
    public void setAdditionalNotes(String additionalNotes) { 
        this.additionalNotes = additionalNotes; 
    }
    public LocalDateTime getSubmittedAt() { 
        return submittedAt; 
    }
    public void setSubmittedAt(LocalDateTime submittedAt) { 
        this.submittedAt = submittedAt; 
    }
    public QuotationStatus getStatus() { 
        return status; 
    }
    public void setStatus(QuotationStatus status) { 
        this.status = status; 
    }

    public String getFullName() { 
        return fullName; 
    }
    public void setFullName(String fullName) { 
        this.fullName = fullName;
    }
    public String getCompanyOrg() { 
        return companyOrg; 
    }
    public void setCompanyOrg(String companyOrg) { 
        this.companyOrg = companyOrg; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getContactNumber() { 
        return contactNumber; 
    }
    public void setContactNumber(String contactNumber) { 
        this.contactNumber = contactNumber; 
    }
}