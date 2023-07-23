package ru.vitalib.otus.homework.books.dao;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.vitalib.otus.homework.books.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_BOOK;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_COMMENT;

@DataJpaTest
@Import(CommentDaoJPA.class)
class CommentDaoJPATest {

  private static final long EXISTING_COMMENT_ID = 1L;
  @Autowired
  TestEntityManager em;

  @Autowired
  CommentDaoJPA commentDaoJPA;

  @Test
  @DisplayName("Find comments by book id")
  public void findCommentForBook() {
    List<Comment> comments = commentDaoJPA.findByBookId(EXISTING_BOOK.getId());

    assertThat(comments)
      .hasSize(1)
      .element(0)
      .matches(c -> c.getId() == EXISTING_COMMENT.getId());
  }

  @Test
  @DisplayName("Update comment")
  public void updateComment() {
    Comment comment = new Comment();
    comment.setText("initial");
    comment.setBook(EXISTING_BOOK);
    em.persist(comment);
    comment.setText("amended");

    commentDaoJPA.save(comment);

    TypedQuery<String> query = em.getEntityManager().createQuery("select c.text from Comment c where c.id = :commentId", String.class);
    query.setParameter("commentId", comment.getId());
    assertThat(query.getSingleResult())
      .isEqualTo("amended");
  }

  @Test
  @DisplayName("Save comment")
  void save() {
    Comment comment = commentDaoJPA.save(new Comment(1, "comment", EXISTING_BOOK));

    assertThat(comment.getId()).isNotEqualTo(0L);
  }

  @Test
  @DisplayName("Delete comment")
  void delete() {
    Comment comment = em.find(Comment.class, EXISTING_COMMENT_ID);

    commentDaoJPA.delete(comment.getId());

    assertThat(em.find(Comment.class, comment.getId()))
        .isEqualTo(null);
  }

  @Test
  @DisplayName("Find comment by id")
  void findById() {
    assertThat(commentDaoJPA.findById(EXISTING_COMMENT_ID))
      .matches(c -> c.getId() == EXISTING_COMMENT.getId())
      .matches(c -> c.getText().equals(EXISTING_COMMENT.getText()))
      .matches(c -> c.getBook().getId() == EXISTING_COMMENT.getBook().getId());
  }
}
