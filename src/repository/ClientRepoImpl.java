package repository;

import config.DbConnection;
import model.Client;
import java.sql.*;
import java.util.Optional;

public class ClientRepoImpl implements ClientRepo {

    @Override
    public int save(Client client) {
        String sql = "INSERT INTO clients (full_name, company_org, email, contact_number) " +
                     "VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE client_id=LAST_INSERT_ID(client_id)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getFullName());
            stmt.setString(2, client.getCompanyOrg());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getContactNumber());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Client Save Error: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("client_id"));
                client.setFullName(rs.getString("full_name"));
                client.setCompanyOrg(rs.getString("company_org"));
                client.setEmail(rs.getString("email"));
                client.setContactNumber(rs.getString("contact_number"));
                return Optional.of(client);
            }
        } catch (SQLException e) {
            System.err.println("Client Find Error: " + e.getMessage());
        }
        return Optional.empty();
    }
}