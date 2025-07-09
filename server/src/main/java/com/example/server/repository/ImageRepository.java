package com.example.server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}