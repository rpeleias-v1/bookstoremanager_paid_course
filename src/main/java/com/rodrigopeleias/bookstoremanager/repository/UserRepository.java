package com.rodrigopeleias.bookstoremanager.repository;

import com.rodrigopeleias.bookstoremanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
