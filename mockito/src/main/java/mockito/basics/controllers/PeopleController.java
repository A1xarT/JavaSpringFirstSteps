package mockito.basics.controllers;

import mockito.basics.models.Person;
import mockito.basics.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/search")
    public List<Person> getPeopleByName(@RequestParam(name = "name") Optional<String> name) {
        if (name.isEmpty()) return List.of();
        return peopleService.findAllByName(name.get());
    }

}
