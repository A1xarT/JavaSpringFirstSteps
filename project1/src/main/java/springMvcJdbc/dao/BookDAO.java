package springMvcJdbc.dao;

import springMvcJdbc.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks() {
        return jdbcTemplate.query("select * from public.book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> allUsedBooks() {
        return jdbcTemplate.query("select * from public.book where owner_id!=null", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book bookById(int id) {
        String sql = "select * from public.book where id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public List<Book> booksByOwnerId(int id) {
        return jdbcTemplate.query("select * from public.book where owner_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void save(Book book) {
        String sql = "insert into public.book (name, author, year, owner_id) values (?,?,?,?)";
        jdbcTemplate.update(sql, book.getName(), book.getAuthor(), book.getYear(), book.getOwnerId());
    }

    public void update(int id, Book book) {
        String sql = "update public.book set name=?,author=?,year=?,owner_id=? where id=?";
        jdbcTemplate.update(sql, book.getName(), book.getAuthor(), book.getYear(), book.getOwnerId(), id);
    }

    public void updateOwner(Integer book_id, Integer newOwnerId) {
        String sql = "update public.book set owner_id=? where id=?";
        jdbcTemplate.update(sql, newOwnerId, book_id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from public.book where id=?", id);
    }
}
