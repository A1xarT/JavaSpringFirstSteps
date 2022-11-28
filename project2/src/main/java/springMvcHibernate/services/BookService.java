package springMvcHibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMvcHibernate.models.Book;
import springMvcHibernate.models.Person;
import springMvcHibernate.repositories.BookRepository;
import springMvcHibernate.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void updateOwner(int id, Person owner) {
        var book = this.findOne(id);
        var person = peopleRepository.findById(owner.getId());
        if (book != null && person.isPresent()) book.setOwner(person.get());
    }

    @Transactional
    public void freeBook(int id) {
        var book = this.findOne(id);
        if (book != null) book.setOwner(null);
    }

    public List<Book> findByAuthor(String name) {
        return bookRepository.findByAuthor(name);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByYear(int year, Pageable pageable) {
        return bookRepository.findByYear(year, pageable);
    }

    public List<Book> findAllPaged(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> findAllPagedSorted(int page, int itemsPerPage, String field) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(field))).getContent();
    }

    public List<Book> findAllSortedByYear(boolean sortFlag) {
        if (sortFlag) return bookRepository.findAll(Sort.by("year").ascending());
        return bookRepository.findAll();
    }

    public List<Book> findAllByNameLike(String name) {
        if (name == null || name.length() == 0) return new ArrayList<>();
        return bookRepository.findByNameLike(name + "%");
    }

    public List<Book> findByYear(int year) {
        return bookRepository.findByYear(year);
    }
}
