package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Author;
import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.repository.AuthorRepository;
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
public class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) throws EntityNotFoundException {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new EntityNotFoundException("Author id=" + id + " not found");
    }

    public void add(Author author) {
        author.setId(null);
        authorRepository.save(author);
    }

    public void remove(Long id) throws EntityNotFoundException {
        getById(id);
        authorRepository.deleteById(id);
    }

    public Map<Long, List<Book>> getBooksByAuthor() {
        return getAll().stream().collect(Collectors.toMap(
                Author::getId,
                Author::getBookList
        ));
    }


}
