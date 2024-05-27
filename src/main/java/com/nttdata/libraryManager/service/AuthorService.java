package com.nttdata.libraryManager.service;
import com.nttdata.libraryManager.repository.AuthorRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }


}
