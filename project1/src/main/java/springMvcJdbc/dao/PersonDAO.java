package springMvcJdbc.dao;


import springMvcJdbc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from public.person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        String sql = "select * from public.person where id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }
    public Optional<Person> showByName(String name, int id) {
        String sql = "select * from public.person where name=? and id!=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), name, id).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into public.person (name, birthday) values(?,?)", person.getName(), person.getBirthday());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update public.person set name=?,birthday=? where id=?", person.getName(), person.getBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from public.person where id=?", id);
    }
}
