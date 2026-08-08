package repository;

import config.DbConnection;
import model.EventType;
import model.QuotationRequest;
import model.QuotationStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuotationRequestRepoImpl implements QuotationRequestRepo {

    @Override
    public boolean save(QuotationRequest request) {
        String sql = "INSERT INTO quotation_requests " +
                     "(client_id, event_name, event_type, event_date, event_venue, " +
                     "number_of_hours, estimated_guests, services_needed, additional_notes, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, request.getClientId());
            stmt.setString(2, request.getEventName());
            stmt.setString(3, request.getEventType().name());
            stmt.setDate(4, Date.valueOf(request.getEventDate()));
            stmt.setString(5, request.getEventVenue());
            stmt.setInt(6, request.getNumberOfHours());
            stmt.setInt(7, request.getEstimatedGuests());
            stmt.setString(8, request.getServicesNeeded());
            stmt.setString(9, request.getAdditionalNotes());
            stmt.setString(10, request.getStatus().name());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Request Save Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<QuotationRequest> getAllRequests() {
        List<QuotationRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM quotation_requests ORDER BY submitted_at DESC";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                QuotationRequest req = new QuotationRequest();
                req.setRequestId(rs.getInt("request_id"));
                req.setClientId(rs.getInt("client_id"));
                req.setEventName(rs.getString("event_name"));
                req.setEventType(EventType.valueOf(rs.getString("event_type")));
                req.setEventDate(rs.getDate("event_date").toLocalDate());
                req.setEventVenue(rs.getString("event_venue"));
                req.setNumberOfHours(rs.getInt("number_of_hours"));
                req.setEstimatedGuests(rs.getInt("estimated_guests"));
                req.setServicesNeeded(rs.getString("services_needed"));
                req.setAdditionalNotes(rs.getString("additional_notes"));
                req.setStatus(QuotationStatus.valueOf(rs.getString("status")));
                list.add(req);
            }
        } catch (SQLException e) {
            System.err.println("Request Fetch Error: " + e.getMessage());
        }
        return list;
    }
}

