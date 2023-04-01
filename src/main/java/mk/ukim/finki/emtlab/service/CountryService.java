package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.model.enumeration.Category;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();

    Country findById(Long id);

    Country create(Country country);




}
