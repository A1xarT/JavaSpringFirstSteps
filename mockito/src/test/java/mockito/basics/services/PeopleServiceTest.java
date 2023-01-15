package mockito.basics.services;

import mockito.basics.models.Person;
import mockito.basics.repositories.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {
    @Mock
    private PeopleRepository peopleRepository;
    @InjectMocks
    private PeopleService peopleService;
    private List<Person> personList;

    @BeforeEach
    void configure() {
        Person p1 = new Person("name1", 12), p2 = new Person("name2", 19),
                p3 = new Person("name3", 22), p4 = new Person("name4", 40),
                p5 = new Person("name1", 22), p6 = new Person("name4", 33);
        personList = List.of(p1, p2, p3, p4, p5, p6);
        Mockito.when(peopleRepository.findAll()).thenReturn(personList);
    }

    @Test
    void findAllShouldEqualsMocked() {
        var listFromService = peopleService.findAll();
        Assertions.assertArrayEquals(listFromService.toArray(), personList.toArray());
    }
}