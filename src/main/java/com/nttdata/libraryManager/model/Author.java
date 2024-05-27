package com.nttdata.libraryManager.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Nationality nationality;
    @OneToMany
    private List<Book> bookList;
}
