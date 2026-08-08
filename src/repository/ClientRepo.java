
package repository;

import model.Client;
import java.util.Optional;

// Database operations for Client records
public interface ClientRepo {
    int save(Client client);
    Optional<Client> findByEmail(String email);
}