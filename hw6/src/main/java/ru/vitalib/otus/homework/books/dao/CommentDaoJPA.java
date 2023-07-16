package ru.vitalib.otus.homework.books.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Comment;

import java.util.List;
import java.util.Optional;


@Repository
public class CommentDaoJPA implements CommentDao {
    private final EntityManager em;

    public CommentDaoJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void delete(long commentId) {
        Optional.ofNullable(em.find(Comment.class, commentId))
          .ifPresent(em::remove);
    }

    @Override
    public Comment findById(Long commentId) {

        return em.find(Comment.class, commentId);
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery(
          "select c from Comment c where c.book.id = :bookId order by c.id desc",
          Comment.class
        );
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
