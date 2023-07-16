package ru.vitalib.otus.homework.books.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Author;

@Repository
public class AuthorDaoJPA implements AuthorDao {
    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author findByName(String authorName) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :authorName", Author.class);
        query.setParameter("authorName", authorName);
        return query.getSingleResult();
    }
}
