package springMvcHibernate.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Transient
    private int maxRentDays = 10;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotBlank(message = "Name shouldn't be empty")
    private String name;
    @Column(name = "author")
    @NotBlank(message = "Author name shouldn't be empty")
    private String author;
    @Column(name = "year")
    private Integer year;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    public Book() {
    }

    public Book(String name, String author, Integer year, Person owner) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public int getMaxRentDays() {
        return maxRentDays;
    }

    public void setMaxRentDays(int maxRentDays) {
        this.maxRentDays = maxRentDays;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean checkRent() {
        return ChronoUnit.DAYS.between(getTakenAt().toInstant(), Instant.now()) <= this.maxRentDays;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name='" + name + '\'' + ", author='" + author + '\'' + ", year=" + year + ", owner=" + ((owner != null) ? owner.getName() : "") + '}';
    }

    public void copy(Book book) {
        this.setName(book.getName());
        this.setOwner(book.getOwner());
        this.setYear(book.getYear());
        this.setAuthor(book.getAuthor());
        this.setTakenAt(book.getTakenAt());
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        if (owner != null) setTakenAt(Date.from(Instant.now()));
        else setTakenAt(null);
        this.owner = owner;
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
}
