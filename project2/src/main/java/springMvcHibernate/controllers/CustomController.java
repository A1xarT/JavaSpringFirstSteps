package springMvcHibernate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springMvcHibernate.services.BookService;

@Controller
@RequestMapping("/custom")
public class CustomController {
    private final BookService bookService;

    public CustomController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public void test() {

    }
}
