package com.rodrigopeleias.bookstoremanager.books.repository;

import com.rodrigopeleias.bookstoremanager.books.entity.Book;
import com.rodrigopeleias.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User user);

    Optional<Book> findByIdAndUser(Long id, User user);

    List<Book> findAllByUser(User user);
}
