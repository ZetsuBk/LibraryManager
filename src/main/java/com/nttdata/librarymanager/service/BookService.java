package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.model.Genre;
import com.nttdata.librarymanager.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service class to handle operations related to books.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Constructs an instance of BookService.
     * @param bookRepository The repository for book data.
     */
    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books.
     * @return A list of books.
     */
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by ID.
     * @param id The ID of the book to retrieve.
     * @return The book.
     * @throws EntityNotFoundException If the book with the given ID is not found.
     */
    public Book getById(Long id) throws EntityNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        throw new EntityNotFoundException("Book id=" + id + " not found");
    }

    /**
     * Adds a new book.
     * @param book The book to add.
     */
    public void add(Book book) {
        book.setId(null);
        bookRepository.save(book);
    }

    /**
     * Removes a book by ID.
     * @param id The ID of the book to remove.
     * @throws EntityNotFoundException If the book with the given ID is not found.
     */
    public void remove(Long id) throws EntityNotFoundException {
        getById(id);
        bookRepository.deleteById(id);
    }

    /**
     * Retrieves a mapping of books grouped by genre.
     * @return A map where the key is the genre and the value is a list of books belonging to that genre.
     */
    public Map<Genre, List<Book>> getBookByGenre() {
        return getAll().stream().collect(Collectors.groupingBy(Book::getGenre));
    }

    /**
     * Saves a list of books.
     * @param books The list of books to save.
     */
    public void saveList(List<Book> books) {
        bookRepository.saveAll(books);
    }
}
