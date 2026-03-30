package net.miarma.backend.huertosdecine.repository;

import net.miarma.backend.huertosdecine.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, byte[]> {
    boolean existsByUsernameAndStatus(String username, Byte status);
}
