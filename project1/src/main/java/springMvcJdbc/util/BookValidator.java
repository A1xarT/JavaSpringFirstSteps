package springMvcJdbc.util;

import springMvcJdbc.dao.PersonDAO;
import springMvcJdbc.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class BookValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired

    public BookValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
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
        Integer bookOwnerId = book.getOwnerId();
        if (bookOwnerId != null && personDAO.show(bookOwnerId) == null) {
            errors.rejectValue("ownerId", "", "Such owner does not exist");
        }
    }
}
