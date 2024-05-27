package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.model.Genre;
import com.nttdata.librarymanager.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) throws EntityNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        throw new EntityNotFoundException("Book id=" + id + " not found");
    }

    public void add(Book book) {
        book.setId(null);
        bookRepository.save(book);
    }

    public void remove(Long id) throws EntityNotFoundException {
        getById(id);
        bookRepository.deleteById(id);
    }

    public Map<Genre, List<Book>> getBookByGenre() {
        return getAll().stream().collect(Collectors.groupingBy(Book::getGenre));
    }

    public void saveList(List<Book> books) {
        bookRepository.saveAll(books);
    }
}
