package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.vitalib.otus.homework.books.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_AUTHOR;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_BOOK;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRE;

@DataJpaTest
@Import(BookDaoJPA.class)
class BookDaoJPATest {

    public static final int NON_EXISTING_BOOK_ID = -1;


    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDaoJPA bookDaoJPA;

    @Test
    @DisplayName("Find book by id")
    void findById() {
        assertThat(bookDaoJPA.findById(EXISTING_BOOK.getId()))
          .isNotNull()
          .matches(b -> b.getName().equals("Хочу быть дворником"))
          .matches(b -> b.getAuthor().getName().equals("Веллер Михаил"))
          .matches(b -> b.getGenre().getName().equals("Детектив"));
    }


    @Test
    @DisplayName("Delete book")
    void delete() {
        bookDaoJPA.delete(EXISTING_BOOK.getId());

        assertThat(bookDaoJPA.findById(EXISTING_BOOK.getId())).isNull();
    }

    @Test
    @DisplayName("Update book name")
    void update() {
        Book updatedBook = new Book(EXISTING_BOOK.getId(), "Паранойа", EXISTING_GENRE, EXISTING_AUTHOR);

        bookDaoJPA.save(updatedBook);

        assertThat(em.find(Book.class, EXISTING_BOOK.getId()))
          .matches(b -> b.getName().equals("Паранойа"));
    }

    @Test
    @DisplayName("Get all books with all information")
    void getAll() {
        assertThat(bookDaoJPA.findAll()).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("Save book")
    void save() {
        Book book = new Book("Артиллерист", EXISTING_GENRE, EXISTING_AUTHOR);

        Book savedBook = bookDaoJPA.save(book);

        assertThat(em.find(Book.class, savedBook.getId()))
          .isNotNull().matches(b -> !b.getName().equals(""))
          .matches(b -> b.getAuthor() != null)
          .matches(b -> b.getGenre() != null)
          .matches(b -> b.getName().equals("Артиллерист"));
    }

    @Test
    @DisplayName("Find for non-existing book should return")
    public void getNonExistentBook() {
        assertThat(em.find(Book.class, NON_EXISTING_BOOK_ID)).isEqualTo(null);
    }
}
