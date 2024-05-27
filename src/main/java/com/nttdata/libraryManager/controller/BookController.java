package com.nttdata.libraryManager.controller;
import com.nttdata.libraryManager.dto.Message;
import com.nttdata.libraryManager.model.Book;
import com.nttdata.libraryManager.service.BookService;
import com.nttdata.libraryManager.util.Global;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Getter
@Setter
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        Global.logger.info("/api/books GET : 200 : ok");
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Long id){
        try{
            Book book =  bookService.getById(id);
            Global.logger.info("/api/books/"+id+" Get : 202 : ok ");
            return new ResponseEntity<>(book,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            Global.logger.info("/api/books/"+id+" GET : 404 : "+e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Message> add(@RequestBody Book book){
        bookService.add(book);
        Global.logger.info("/api/books/ POST : 202 : ok ");
        return new ResponseEntity<>(Message.setMessage("Book was add successfully"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") Long id){
        try{
            bookService.remove(id);
            Global.logger.info("/api/books/"+id+" DELETE : 202 : ok ");
            return new ResponseEntity<>(Message.setMessage("Book was deleted successfully"),HttpStatus.OK);
        }catch (EntityNotFoundException e){
            Global.logger.info("/api/books/"+id+" DELETE : 404 : "+e.getMessage()+" can not delete");
            return new ResponseEntity<>(Message.setMessage("not found can not delete"),HttpStatus.NOT_FOUND);
        }
    }


}
