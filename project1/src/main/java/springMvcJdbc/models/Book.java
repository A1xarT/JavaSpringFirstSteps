package springMvcJdbc.models;

import javax.validation.constraints.NotBlank;

public class Book {
    private Integer id;
    @NotBlank(message = "Name shouldn't be empty")
    private String name;
    @NotBlank(message = "Author name shouldn't be empty")
    private String author;
    private Integer year;
    private Integer ownerId;

    public Book() {
    }

    public Book(int id, String name, String author, Integer year, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
