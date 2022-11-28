package springMvcHibernate.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMvcHibernate.models.Mood;
import springMvcHibernate.models.Person;
import springMvcHibernate.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        var person = peopleRepository.findById(id).orElse(null);
        if (person != null) Hibernate.initialize(person.getBookList());
        return person;
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        person.setMood(Mood.CALM);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Person> findByIdGreater(int id) {
        var people = peopleRepository.findByIdGreaterThan(id);
        for (var p : people)
            Hibernate.initialize(p.getBookList());
        return people;
    }

    public boolean checkIfNameAvailable(String name, int excludedId) {
        var resultList = peopleRepository.findByName(name);
        return resultList.size() == 0 || resultList.size() == 1 && resultList.get(0).getId() == excludedId;
    }
}
