package com.example.server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.server.entity.Platform;

public interface PlatformRepository extends JpaRepository<Platform, UUID>, JpaSpecificationExecutor<Platform> {
}