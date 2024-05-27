package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Author;
import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @Before
    public void setUp() {
        author1 = new Author();
        author1.setId(1L);
        author1.setLastname("Author One");
        author1.setBookList(Collections.singletonList(new Book()));

        author2 = new Author();
        author2.setId(2L);
        author2.setLastname("Author Two");
        author2.setBookList(Arrays.asList(new Book(), new Book()));

        this.authorService = new AuthorService(authorRepository);
    }

    @Test
    public void GetterRepo(){
        authorService.getAuthorRepository();
    }

    @Test
    public void testGetAll() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        List<Author> authors = authorService.getAll();
        assertEquals(2, authors.size());
    }

    @Test
    public void testGetById_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        Author author = authorService.getById(1L);
        assertNotNull(author);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        authorService.getById(1L);
    }

    @Test
    public void testAdd() {
        Author newAuthor = new Author();
        newAuthor.setLastname("New Author");

        authorService.add(newAuthor);

        verify(authorRepository, times(1)).save(newAuthor);
    }

    @Test
    public void testRemove_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        authorService.remove(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRemove_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        authorService.remove(1L);
    }

    @Test
    public void testGetBooksByAuthor() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        Map<Long, List<Book>> booksByAuthor = authorService.getBooksByAuthor();

        assertEquals(2, booksByAuthor.size());
    }
}
