package com.nttdata.librarymanager.controller;

import com.nttdata.librarymanager.dto.Message;
import com.nttdata.librarymanager.model.Author;
import com.nttdata.librarymanager.service.AuthorService;
import com.nttdata.librarymanager.util.Global;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nttdata.librarymanager.util.Global.message;

@RestController
@RequestMapping(Global.APIAUTHOR)
@Getter
@Setter
public class AuthorController {

    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        Global.logger.info(Global.APIAUTHOR + " GET : 200 : ok");
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Long id) {
        try {
            Author author = authorService.getById(id);
            Global.logger.info("{} {} Get : 202 : ok ", Global.APIAUTHOR, id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} GET : 404 : {} ", Global.APIAUTHOR, id, e.getMessage());
            return new ResponseEntity<>(new Author(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Message> add(@RequestBody Author author) {
        authorService.add(author);
        Global.logger.info(Global.APIAUTHOR + " POST : 202 : ok ");
        return new ResponseEntity<>(message.setMessage("Author was add successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") Long id) {
        try {
            authorService.remove(id);
            Global.logger.info("{} {} DELETE : 202 : ok ", Global.APIAUTHOR, id);
            return new ResponseEntity<>(message.setMessage("Author was deleted successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} DELETE : 404 : {} can not delete", Global.APIAUTHOR, id, e.getMessage());
            return new ResponseEntity<>(message.setMessage("can not delete"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Message> update(@RequestBody Author authorBody) {
        final Long id = authorBody.getId();
        try {
            authorService.getById(id);
            authorService.add(authorBody);
            Global.logger.info("{} {} Get : 202 : ok ", Global.APIAUTHOR, id);
            return new ResponseEntity<>(message.setMessage("author was updated successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Global.logger.info("{} {} GET : 404 : {} ", Global.APIAUTHOR, id, e.getMessage());
            return new ResponseEntity<>(message.setMessage("can not update"), HttpStatus.NOT_FOUND);
        }
    }

}
