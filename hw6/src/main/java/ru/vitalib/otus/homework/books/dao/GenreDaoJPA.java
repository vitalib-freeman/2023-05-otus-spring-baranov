package ru.vitalib.otus.homework.books.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Genre;

@Repository
public class GenreDaoJPA implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoJPA(EntityManager em) {
        this.em = em;
    }


    @Override
    public Genre findByName(String genreName) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name=:genreName", Genre.class);
        query.setParameter("genreName", genreName);
        return query.getSingleResult();
    }
}
