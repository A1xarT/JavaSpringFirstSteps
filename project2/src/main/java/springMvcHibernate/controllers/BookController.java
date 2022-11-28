package springMvcHibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvcHibernate.models.Book;
import springMvcHibernate.models.Person;
import springMvcHibernate.services.BookService;
import springMvcHibernate.services.PeopleService;
import springMvcHibernate.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookValidator bookValidator;
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BookController(BookValidator bookValidator, PeopleService peopleService, BookService bookService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String allBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/allBooks";
    }

    @GetMapping(params = {"page", "books_per_page", "sort_by_year"})
    public String allBooksPageParams(@RequestParam int page, @RequestParam(name = "books_per_page") int booksPerPage, @RequestParam(name = "sort_by_year") boolean sorted, Model model) {
        var books = sorted ? bookService.findAllPagedSorted(page, booksPerPage, "year") : bookService.findAllPaged(page, booksPerPage);
        model.addAttribute("books", books);
        return "books/allBooks";
    }

    @GetMapping(params = {"sort_by_year"})
    public String allBooksSortedByYear(@RequestParam(name = "sort_by_year") boolean sortFlag, Model model) {
        model.addAttribute("books", bookService.findAllSortedByYear(sortFlag));
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        Person bookOwner;
        if (book.getOwner() == null) bookOwner = null;
        else bookOwner = book.getOwner();
        model.addAttribute("bookOwner", bookOwner);
        model.addAttribute("personList", peopleService.findAll());
        return "books/showBook";
    }

    @PatchMapping("/{id}/freeBook")
    public String freeBook(@PathVariable("id") int id) {
        bookService.freeBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/addOwner")
    public String assignOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.updateOwner(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/editBook";
        book.setOwner(bookService.findOne(id).getOwner());
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("name") String name, Model model) {
        model.addAttribute("books", bookService.findAllByNameLike(name));
        return "books/search";
    }
}
