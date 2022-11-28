package springMvcHibernate.util;

import springMvcHibernate.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springMvcHibernate.services.PeopleService;

import java.time.Year;

@Component
public class BookValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public BookValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (book.getYear() == null) {
            errors.rejectValue("year", "", "Enter book's release year");
        } else if (book.getYear() > Year.now().getValue()) {
            errors.rejectValue("year", "", "The book's release year is wrong");
        }
        var bookOwner = book.getOwner();
        if (bookOwner != null && peopleService.findOne(bookOwner.getId()) == null) {
            errors.rejectValue("ownerId", "", "Such owner does not exist");
        }
    }
}
