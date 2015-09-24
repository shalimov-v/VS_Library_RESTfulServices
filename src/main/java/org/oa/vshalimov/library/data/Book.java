package org.oa.vshalimov.library.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class Book {

    @XmlElement
    private long id;

    @XmlElement
    private String name;

    @XmlElement
    private String isbn;

    @XmlElement
    private Author author;

    public Book() {
        this.id = 0;
        this.name = null;
        this.isbn = null;
        this.author = null;
    }

    public Book(long id, String name, String isbn, Author author) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public Author getAuthor() {
        return author;
    }
}