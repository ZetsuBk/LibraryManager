package com.nttdata.libraryManager.service;

import com.nttdata.libraryManager.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BookService {
    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
}
