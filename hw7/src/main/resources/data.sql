INSERT INTO AUTHOR(`name`) VALUES ('Веллер Михаил'), ('Толстой Лев');
INSERT INTO GENRE(`name`) VALUES ('Детектив'), ('Драмма');
INSERT INTO BOOK(name, genre_id, author_id) VALUES ('Хочу быть дворником', 1, 1), ('Анна Каренина', 2, 2);
INSERT INTO COMMENT(book_id, text) VALUES (1, 'Норм');
