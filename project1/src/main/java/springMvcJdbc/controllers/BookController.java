package springMvcJdbc.controllers;

import springMvcJdbc.dao.BookDAO;
import springMvcJdbc.dao.PersonDAO;
import springMvcJdbc.models.Book;
import springMvcJdbc.models.Person;
import springMvcJdbc.util.BookValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String allBooks(Model model) {
        model.addAttribute("books", bookDAO.allBooks());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDAO.bookById(id);
        model.addAttribute("book", book);
        Person bookOwner;
        if (book.getOwnerId() == null) bookOwner = null;
        else bookOwner = personDAO.show(book.getOwnerId());
        model.addAttribute("bookOwner", bookOwner);
        model.addAttribute("personList", personDAO.index());
        return "books/showBook";
    }

    @PatchMapping("/{id}/freeBook")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.updateOwner(id, null);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/addOwner")
    public String assignOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.updateOwner(id, person.getId());
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
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.bookById(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/editBook";
        book.setOwnerId(bookDAO.bookById(id).getOwnerId());
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
