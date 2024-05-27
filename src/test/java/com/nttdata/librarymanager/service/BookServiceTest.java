package com.nttdata.librarymanager.service;

import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.model.Genre;
import com.nttdata.librarymanager.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    private Book book1;
    private Book book2;

    @Before
    public void setUp() {
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setGenre(Genre.ACTION);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setGenre(Genre.COMEDY);

        this.bookService = new BookService(bookRepository);
    }


    @Test
    public void testGetAll() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAll();

        assertEquals("Book Two", books.get(1).getTitle());
    }

    @Test
    public void testGetById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book book = bookService.getById(1L);
        assertNotNull(book);
        assertEquals("Book One", book.getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        bookService.getById(1L);
    }

    @Test
    public void testAdd() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setGenre(Genre.ROMANCE);

        bookService.add(newBook);

        verify(bookRepository, times(1)).save(newBook);

    }

    @Test
    public void testRemove_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        bookService.remove(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRemove_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        bookService.remove(1L);
    }

    @Test
    public void testGetBookByGenre() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        List<Book> actionBooks = bookService.getBookByGenre().get(Genre.ACTION);
        assertEquals("Book One", actionBooks.get(0).getTitle());
    }

    @Test
    public void testSaveList() {
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        bookService.saveList(books);

        verify(bookRepository, times(1)).saveAll(books);
    }
}
