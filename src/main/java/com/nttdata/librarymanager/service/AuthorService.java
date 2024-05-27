package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Author;
import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A service class to authors.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Constructs an instance of AuthorService.
     * @param authorRepository The repository for author data.
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves all authors.
     * @return A list of authors.
     */
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves an author by ID.
     * @param id The ID of the author to retrieve.
     * @return The author.
     * @throws EntityNotFoundException If the author with the given ID is not found.
     */
    public Author getById(Long id) throws EntityNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    /**
     * Adds a new author.
     * @param author The author to add.
     * @return The added author.
     */
    public Author add(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Removes an author by ID.
     * @param id The ID of the author to remove.
     * @throws EntityNotFoundException If the author with the given ID is not found.
     */
    public void remove(Long id) throws EntityNotFoundException {
        getById(id);
        authorRepository.deleteById(id);
    }

    /**
     * Retrieves a mapping of author IDs to their book lists.
     * @return A map where the key is the author ID and the value is a list of books authored by that author.
     */
    public Map<Long, List<Book>> getBooksByAuthor() {
        return authorRepository.findAll().stream()
                .collect(Collectors.toMap(Author::getId, Author::getBookList));
    }
}
