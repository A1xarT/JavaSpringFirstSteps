package springMvcHibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }
}
