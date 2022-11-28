package springMvcHibernate.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springMvcHibernate.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String author);

    List<Book> findByYear(int year, Pageable pageable);

    List<Book> findByYear(int year);

    List<Book> findByNameLike(String name);
}
