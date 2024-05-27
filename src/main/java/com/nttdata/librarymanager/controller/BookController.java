package com.nttdata.librarymanager.controller;

import com.nttdata.librarymanager.dto.Message;
import com.nttdata.librarymanager.model.Book;
import com.nttdata.librarymanager.model.Genre;
import com.nttdata.librarymanager.service.AuthorService;
import com.nttdata.librarymanager.service.BookService;
import com.nttdata.librarymanager.util.Global;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.nttdata.librarymanager.util.Global.message;

@RestController
@RequestMapping(Global.APIBOOK)
@Getter
@Setter
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;

    BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        Global.logger.info(Global.APIBOOK + " GET : 200 : ok");
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Long id) {
        try {
            Book book = bookService.getById(id);
            Global.logger.info("{} {} Get : 202 : ok ", Global.APIBOOK, id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} GET : 404 : {} ", Global.APIBOOK, id, e.getMessage());
            return new ResponseEntity<>(new Book(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Message> add(@RequestBody Book book) {
        bookService.add(book);
        Global.logger.info(Global.APIBOOK + " POST : 202 : ok ");
        return new ResponseEntity<>(message.setMessage("Book was add successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") Long id) {
        try {
            bookService.remove(id);
            Global.logger.info("{} {} DELETE : 202 : ok ", Global.APIBOOK, id);
            return new ResponseEntity<>(message.setMessage("Book was deleted successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} DELETE : 404 : {} can not delete", Global.APIBOOK, id, e.getMessage());
            return new ResponseEntity<>(message.setMessage("can not delete"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Message> update(@RequestBody Book bookBody) {
        final Long id = bookBody.getId();
        try {
            bookService.getById(id);
            bookService.add(bookBody);
            Global.logger.info("{} {} Get : 202 : ok ", Global.APIBOOK, id);
            return new ResponseEntity<>(message.setMessage("book was updated successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} GET : 404 : {} ", Global.APIBOOK, id, e.getMessage());
            return new ResponseEntity<>(message.setMessage("can not update"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by/author")
    public ResponseEntity<Map<Long, List<Book>>> filterByAuthor() {
        Global.logger.info("{} Get : 202 : ok ", Global.APIBOOK);
        return new ResponseEntity<>(authorService.getBooksByAuthor(), HttpStatus.OK);
    }

    @GetMapping("/by/genre")
    public ResponseEntity<Map<Genre, List<Book>>> filterByGenre() {
        Global.logger.info("{} Get : 202 : ok ", Global.APIBOOK);
        return new ResponseEntity<>(bookService.getBookByGenre(), HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<Message> readJson(@RequestBody List<Book> books) {
        bookService.saveList(books);
        return new ResponseEntity<>(message.setMessage("json file was add successfully "), HttpStatus.OK);
    }
}
