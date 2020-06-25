package com.rodrigopeleias.bookstoremanager.repository;

import com.rodrigopeleias.bookstoremanager.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameOrCode(String name, String code);
}
