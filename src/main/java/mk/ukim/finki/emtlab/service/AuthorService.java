package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author create(Author author);
}
