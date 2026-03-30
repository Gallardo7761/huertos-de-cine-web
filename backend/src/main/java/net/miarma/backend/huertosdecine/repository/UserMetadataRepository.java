package net.miarma.backend.huertosdecine.repository;

import net.miarma.backend.huertosdecine.model.UserMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMetadataRepository extends JpaRepository<UserMetadata, byte[]> {}