package ru.vitalib.otus.homework.books.dao;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJPA implements BookDao {

    private final EntityManager em;

    public BookDaoJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void delete(long id) {
        Optional.ofNullable(em.find(Book.class, id)).ifPresent(em::remove);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b left join fetch b.author left join fetch b.genre", Book.class)
          .getResultList();
    }
}
