package com.taskmanagementsystem.security.db.repository;

import com.taskmanagementsystem.security.db.entity.DeactivatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeactivatedTokenRepository extends JpaRepository<DeactivatedToken, UUID> {
}
