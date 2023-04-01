package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.model.enumeration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Page<Book> findAllWithPagination(Pageable pageable);

    Optional<Book> saveFromDto(BookDto bookDto);

    Book create(String name, Category category, Long authorId, Integer availableCopies);

    Optional<Book> editBook(Long id, BookDto bookDto);

    Optional<Book> deleteBook(Long id);

    Optional<Book> taken(Long id);

}
