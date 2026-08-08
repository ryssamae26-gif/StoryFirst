
package model;

import java.time.LocalDateTime;

public class Client {
    private Integer clientId;
    private String fullName;
    private String companyOrg;
    private String email;
    private String contactNumber;
    private LocalDateTime createdAt;

    public Client() {}

    public Integer getClientId() {
        return clientId; 
    }
    public void setClientId(Integer clientId) { 
        this.clientId = clientId; 
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
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
