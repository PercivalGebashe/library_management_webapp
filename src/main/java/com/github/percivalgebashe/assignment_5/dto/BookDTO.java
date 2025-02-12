package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.entity.Author;
import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.entity.Genre;
import com.github.percivalgebashe.assignment_5.entity.Publisher;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class BookDTO {
    private Long book_id;

    private Set<Author> authors;

    private String title;

    private Date publishedDate;

    private Set<Publisher> publishers;

    private String description;

    private String isbn;

    private Set<Genre> genres;

    public Book toBookEntity() {
        Book book = new Book();
        book.setId(book_id);
        book.setAuthors(authors);
        book.setTitle(title);
        book.setPublishedDate(publishedDate);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setGenres(genres);
        return book;
    }
}
