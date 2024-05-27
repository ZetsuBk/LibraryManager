package com.nttdata.libraryManager.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Date publishYear;
    private String ISBN;
}