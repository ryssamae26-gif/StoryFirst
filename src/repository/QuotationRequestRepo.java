
package repository;

import model.QuotationRequest;
import java.util.List;

// Database operations for Quotation Requests
public interface QuotationRequestRepo {
    boolean save(QuotationRequest request);
    List<QuotationRequest> getAllRequests();
}