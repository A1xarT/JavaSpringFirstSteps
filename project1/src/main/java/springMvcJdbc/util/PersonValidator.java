package springMvcJdbc.util;

import springMvcJdbc.dao.PersonDAO;
import springMvcJdbc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // name unique check
        if (personDAO.showByName(person.getName(), person.getId()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
        // birthday check
        if (person.getBirthday() == null) {
            errors.rejectValue("birthday", "", "Please, enter your birth date");
        } else if (person.getBirthday().after(Date.valueOf(LocalDate.now()))) {
            errors.rejectValue("birthday", "", "Please, enter your real birth date");
        }
    }
}
