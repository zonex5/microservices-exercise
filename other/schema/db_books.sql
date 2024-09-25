CREATE TABLE authors
(
    id            serial
        CONSTRAINT authors_pk
            PRIMARY KEY,
    name          varchar(150) NOT NULL
        CONSTRAINT authors_name_unique
            UNIQUE,
    date_of_birth date         NOT NULL,
    date_of_death date
);

CREATE TABLE books
(
    id        serial
        CONSTRAINT books_pk
            PRIMARY KEY,
    id_author integer      NOT NULL
        CONSTRAINT books_authors_id_fk
            REFERENCES authors
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    title     varchar(500) NOT NULL,
    edition   integer,
    tags      text[],
    CONSTRAINT books_title_unique
        UNIQUE (id_author, title)
);

COMMENT ON COLUMN books.edition IS 'year of edition';

INSERT INTO authors (id, name, date_of_birth, date_of_death)
VALUES (1, 'J.K. Rowling', '1965-07-31', NULL),
       (2, 'George R.R. Martin', '1948-09-20', NULL),
       (3, 'J.R.R. Tolkien', '1892-01-03', '1973-09-02'),
       (4, 'Stephen King', '1947-09-21', NULL),
       (5, 'Agatha Christie', '1890-09-15', '1976-01-12'),
       (6, 'Dan Brown', '1964-06-22', NULL),
       (7, 'Jane Austen', '1775-12-16', '1817-07-18'),
       (8, 'Mark Twain', '1835-11-30', '1910-04-21'),
       (9, 'Ernest Hemingway', '1899-07-21', '1961-07-02'),
       (10, 'F. Scott Fitzgerald', '1896-09-24', '1940-12-21'),
       (11, 'Charles Dickens', '1812-02-07', '1870-06-09'),
       (12, 'Virginia Woolf', '1882-01-25', '1941-03-28'),
       (13, 'Gabriel Garcia Marquez', '1927-03-06', '2014-04-17'),
       (14, 'Harper Lee', '1926-04-28', '2016-02-19'),
       (15, 'Leo Tolstoy', '1828-09-09', '1910-11-20'),
       (16, 'Fyodor Dostoevsky', '1821-11-11', '1881-02-09'),
       (17, 'Oscar Wilde', '1854-10-16', '1900-11-30'),
       (18, 'Mary Shelley', '1797-08-30', '1851-02-01'),
       (19, 'Isaac Asimov', '1920-01-02', '1992-04-06'),
       (20, 'Arthur Conan Doyle', '1859-05-22', '1930-07-07');

-- Вставка книг с явным указанием id
INSERT INTO books (id, id_author, title, edition, tags)
VALUES
-- J.K. Rowling (id_author = 1)
(1, 1, 'Harry Potter and the Philosopher''s Stone', 1997, ARRAY ['Fantasy', 'Adventure']),
(2, 1, 'Harry Potter and the Chamber of Secrets', 1998, ARRAY ['Fantasy', 'Adventure']),
(3, 1, 'Harry Potter and the Prisoner of Azkaban', 1999, ARRAY ['Fantasy', 'Adventure']),
(4, 1, 'Harry Potter and the Goblet of Fire', 2000, ARRAY ['Fantasy', 'Adventure']),
(5, 1, 'Harry Potter and the Order of the Phoenix', 2003, ARRAY ['Fantasy', 'Adventure']),
-- George R.R. Martin (id_author = 2)
(6, 2, 'A Game of Thrones', 1996, ARRAY ['Fantasy', 'Epic']),
(7, 2, 'A Clash of Kings', 1998, ARRAY ['Fantasy', 'Epic']),
(8, 2, 'A Storm of Swords', 2000, ARRAY ['Fantasy', 'Epic']),
(9, 2, 'A Feast for Crows', 2005, ARRAY ['Fantasy', 'Epic']),
(10, 2, 'A Dance with Dragons', 2011, ARRAY ['Fantasy', 'Epic']),
-- J.R.R. Tolkien (id_author = 3)
(11, 3, 'The Hobbit', 1937, ARRAY ['Fantasy', 'Adventure']),
(12, 3, 'The Fellowship of the Ring', 1954, ARRAY ['Fantasy', 'Epic']),
(13, 3, 'The Two Towers', 1954, ARRAY ['Fantasy', 'Epic']),
(14, 3, 'The Return of the King', 1955, ARRAY ['Fantasy', 'Epic']),
(15, 3, 'The Silmarillion', 1977, ARRAY ['Fantasy', 'Mythology']),
-- Stephen King (id_author = 4)
(16, 4, 'The Shining', 1977, ARRAY ['Horror', 'Thriller']),
(17, 4, 'It', 1986, ARRAY ['Horror', 'Thriller']),
(18, 4, 'Misery', 1987, ARRAY ['Horror', 'Thriller']),
(19, 4, 'The Dark Tower I: The Gunslinger', 1982, ARRAY ['Fantasy', 'Horror']),
(20, 4, 'The Dark Tower II: The Drawing of the Three', 1987, ARRAY ['Fantasy', 'Horror']),
-- Agatha Christie (id_author = 5)
(21, 5, 'Murder on the Orient Express', 1934, ARRAY ['Mystery', 'Crime']),
(22, 5, 'And Then There Were None', 1939, ARRAY ['Mystery', 'Crime']),
(23, 5, 'The A.B.C. Murders', 1936, ARRAY ['Mystery', 'Crime']),
(24, 5, 'Death on the Nile', 1937, ARRAY ['Mystery', 'Crime']),
(25, 5, 'The Murder of Roger Ackroyd', 1926, ARRAY ['Mystery', 'Crime']),
-- Dan Brown (id_author = 6)
(26, 6, 'Digital Fortress', 1998, ARRAY ['Thriller', 'Tech']),
(27, 6, 'Angels & Demons', 2000, ARRAY ['Thriller', 'Mystery']),
(28, 6, 'Deception Point', 2001, ARRAY ['Thriller', 'Science Fiction']),
(29, 6, 'The Da Vinci Code', 2003, ARRAY ['Thriller', 'Mystery']),
(30, 6, 'The Lost Symbol', 2009, ARRAY ['Thriller', 'Mystery']),
-- Jane Austen (id_author = 7)
(31, 7, 'Pride and Prejudice', 1813, ARRAY ['Romance', 'Classic']),
(32, 7, 'Sense and Sensibility', 1811, ARRAY ['Romance', 'Classic']),
(33, 7, 'Emma', 1815, ARRAY ['Romance', 'Classic']),
(34, 7, 'Mansfield Park', 1814, ARRAY ['Romance', 'Classic']),
(35, 7, 'Persuasion', 1817, ARRAY ['Romance', 'Classic']),
-- Mark Twain (id_author = 8)
(36, 8, 'Adventures of Huckleberry Finn', 1884, ARRAY ['Adventure', 'Classic']),
(37, 8, 'The Adventures of Tom Sawyer', 1876, ARRAY ['Adventure', 'Classic']),
(38, 8, 'A Connecticut Yankee in King Arthur''s Court', 1889, ARRAY ['Science Fiction', 'Satire']),
(39, 8, 'The Prince and the Pauper', 1881, ARRAY ['Historical', 'Classic']),
(40, 8, 'Life on the Mississippi', 1883, ARRAY ['Autobiography', 'History']),
-- Ernest Hemingway (id_author = 9)
(41, 9, 'The Old Man and the Sea', 1952, ARRAY ['Literary Fiction', 'Classic']),
(42, 9, 'A Farewell to Arms', 1929, ARRAY ['War', 'Romance']),
(43, 9, 'For Whom the Bell Tolls', 1940, ARRAY ['War', 'Literary Fiction']),
(44, 9, 'The Sun Also Rises', 1926, ARRAY ['Literary Fiction', 'Classic']),
(45, 9, 'To Have and Have Not', 1937, ARRAY ['Crime', 'Adventure']),
-- F. Scott Fitzgerald (id_author = 10)
(46, 10, 'The Great Gatsby', 1925, ARRAY ['Literary Fiction', 'Classic']),
(47, 10, 'Tender Is the Night', 1934, ARRAY ['Literary Fiction', 'Classic']),
(48, 10, 'This Side of Paradise', 1920, ARRAY ['Literary Fiction', 'Classic']),
(49, 10, 'The Beautiful and Damned', 1922, ARRAY ['Literary Fiction', 'Classic']),
(50, 10, 'The Last Tycoon', 1941, ARRAY ['Literary Fiction', 'Classic']),
-- Charles Dickens (id_author = 11)
(51, 11, 'A Tale of Two Cities', 1859, ARRAY ['Historical', 'Classic']),
(52, 11, 'Great Expectations', 1861, ARRAY ['Classic', 'Bildungsroman']),
-- Virginia Woolf (id_author = 12)
(53, 12, 'Mrs Dalloway', 1925, ARRAY ['Modernist', 'Literary Fiction']),
(54, 12, 'To the Lighthouse', 1927, ARRAY ['Modernist', 'Literary Fiction']),
-- Gabriel Garcia Marquez (id_author = 13)
(55, 13, 'One Hundred Years of Solitude', 1967, ARRAY ['Magic Realism', 'Classic']),
(56, 13, 'Love in the Time of Cholera', 1985, ARRAY ['Romance', 'Magic Realism']),
-- Harper Lee (id_author = 14)
(57, 14, 'To Kill a Mockingbird', 1960, ARRAY ['Classic', 'Social Issues']),
(58, 14, 'Go Set a Watchman', 2015, ARRAY ['Classic', 'Social Issues']),
-- Leo Tolstoy (id_author = 15)
(59, 15, 'War and Peace', 1869, ARRAY ['Historical', 'Classic']),
(60, 15, 'Anna Karenina', 1877, ARRAY ['Romance', 'Classic']),
-- Fyodor Dostoevsky (id_author = 16)
(61, 16, 'Crime and Punishment', 1866, ARRAY ['Philosophical', 'Psychological']),
(62, 16, 'The Brothers Karamazov', 1880, ARRAY ['Philosophical', 'Classic']),
-- Oscar Wilde (id_author = 17)
(63, 17, 'The Picture of Dorian Gray', 1890, ARRAY ['Philosophical', 'Classic']),
(64, 17, 'The Importance of Being Earnest', 1895, ARRAY ['Comedy', 'Play']),
-- Mary Shelley (id_author = 18)
(65, 18, 'Frankenstein', 1818, ARRAY ['Horror', 'Science Fiction']),
(66, 18, 'The Last Man', 1826, ARRAY ['Science Fiction', 'Dystopian']),
-- Isaac Asimov (id_author = 19)
(67, 19, 'Foundation', 1951, ARRAY ['Science Fiction', 'Classic']),
(68, 19, 'Foundation and Empire', 1952, ARRAY ['Science Fiction', 'Classic']),
-- Arthur Conan Doyle (id_author = 20)
(69, 20, 'A Study in Scarlet', 1887, ARRAY ['Mystery', 'Detective']),
(70, 20, 'The Hound of the Baskervilles', 1902, ARRAY ['Mystery', 'Detective']);

SELECT setval('authors_id_seq', (SELECT MAX(id) FROM authors));

SELECT setval('books_id_seq', (SELECT MAX(id) FROM books));
