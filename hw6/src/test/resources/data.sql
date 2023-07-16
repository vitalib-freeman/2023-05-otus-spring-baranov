INSERT INTO AUTHOR(`name`) values ('Веллер Михаил');
INSERT INTO GENRE(`name`) values ('Детектив');
INSERT INTO BOOK(name, genre_id, author_id) values ('Хочу быть дворником', 1, 1);
INSERT INTO COMMENT(book_id, text) VALUES (1, 'Норм');
