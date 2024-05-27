package com.nttdata.libraryManager.controller;

import com.nttdata.libraryManager.dto.Message;
import com.nttdata.libraryManager.model.Author;
import com.nttdata.libraryManager.service.AuthorService;
import com.nttdata.libraryManager.util.Global;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Getter
@Setter
public class AuthorController {

    private final AuthorService authorService;

    AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
        Global.logger.info("/api/authors GET : 200 : ok");
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Long id){
        try{
            Author author =  authorService.getById(id);
            Global.logger.info("/api/authors/"+id+" Get : 202 : ok ");
            return new ResponseEntity<>(author,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            Global.logger.info("/api/authors/"+id+" GET : 404 : "+e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Message> add(@RequestBody Author author){
        authorService.add(author);
        Global.logger.info("/api/authors/ POST : 202 : ok ");
        return new ResponseEntity<>(Message.setMessage("Author was add successfully"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") Long id){
        try{
            authorService.remove(id);
            Global.logger.info("/api/authors/"+id+" DELETE : 202 : ok ");
            return new ResponseEntity<>(Message.setMessage("Author was deleted successfully"),HttpStatus.OK);
        }catch (EntityNotFoundException e){
            Global.logger.info("/api/authors/"+id+" DELETE : 404 : "+e.getMessage()+" can not delete");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


}
