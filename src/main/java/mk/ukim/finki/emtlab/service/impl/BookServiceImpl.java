package mk.ukim.finki.emtlab.service.impl;

import mk.ukim.finki.emtlab.model.Author;
import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.model.enumeration.Category;
import mk.ukim.finki.emtlab.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.emtlab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Page<Book> findAllWithPagination(Pageable pageable){
        return this.bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.of(bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id)));
    }

    @Override
    public Optional<Book> saveFromDto(BookDto bookDto) {
        Long authorId= bookDto.getAuthorId();
        Author author = authorRepository.findById(authorId).orElseThrow(()->new AuthorNotFoundException(authorId));
        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies());
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Book create(String name, Category category, Long authorId, Integer availableCopies) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(()->new AuthorNotFoundException(authorId));
        Book book = new Book(name, category, author, availableCopies);
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> editBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        Long authorId= bookDto.getAuthorId();
        Author author = authorRepository.findById(authorId).orElseThrow(()->new AuthorNotFoundException(authorId));
        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setAuthor(author);
        book.setAvailableCopies(bookDto.getAvailableCopies());
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        bookRepository.delete(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> taken(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        book.setAvailableCopies(book.getAvailableCopies()-1);
        return Optional.of(bookRepository.save(book));
    }
}
