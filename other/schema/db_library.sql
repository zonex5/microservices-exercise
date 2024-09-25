CREATE TABLE libraries
(
    id      serial
        CONSTRAINT libraries_pk
            PRIMARY KEY,
    name    varchar(150) NOT NULL
        CONSTRAINT libraries_name_unique
            UNIQUE,
    address varchar(250)
);

CREATE TABLE stock
(
    id         serial
        CONSTRAINT stock_pk
            PRIMARY KEY,
    id_library integer                 NOT NULL
        CONSTRAINT stock_libraries_id_fk
            REFERENCES libraries
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    id_book    integer                 NOT NULL,
    quantity   integer       DEFAULT 0 NOT NULL,
    price      numeric(9, 2) DEFAULT 0 NOT NULL,
    CONSTRAINT stock_unique_library_book
        UNIQUE (id_library, id_book)
);

CREATE OR REPLACE VIEW library_stock_view(id, id_library, name, address, id_book, quantity, price) AS
SELECT DISTINCT s.id,
                l.id AS id_library,
                l.name,
                l.address,
                s.id_book,
                s.quantity,
                s.price
FROM stock s
         JOIN libraries l ON l.id = s.id_library;

INSERT INTO libraries (id, name, address)
VALUES (1, 'New York Public Library', '476 5th Ave, New York, NY 10018, USA'),
       (2, 'British Library', '96 Euston Rd, London NW1 2DB, United Kingdom'),
       (3, 'Library of Congress', '101 Independence Ave SE, Washington, DC 20540, USA'),
       (4, 'Boston Public Library', '700 Boylston St, Boston, MA 02116, USA'),
       (5, 'Los Angeles Public Library', '630 W 5th St, Los Angeles, CA 90071, USA');

INSERT INTO stock (id, id_library, id_book, quantity, price)
VALUES
-- New York Public Library (id_library = 1)
(1, 1, 1, 5, 25.99),   -- 'Harry Potter and the Philosopher''s Stone'
(2, 1, 2, 3, 22.50),   -- 'Harry Potter and the Chamber of Secrets'
(3, 1, 3, 4, 24.75),   -- 'Harry Potter and the Prisoner of Azkaban'
(4, 1, 4, 2, 27.00),   -- 'Harry Potter and the Goblet of Fire'
(5, 1, 5, 6, 26.50),   -- 'Harry Potter and the Order of the Phoenix'
(6, 1, 6, 7, 19.99),   -- 'A Game of Thrones'
(7, 1, 7, 5, 18.75),   -- 'A Clash of Kings'
(8, 1, 8, 4, 20.00),   -- 'A Storm of Swords'
(9, 1, 9, 3, 22.00),   -- 'A Feast for Crows'
(10, 1, 10, 2, 23.50), -- 'A Dance with Dragons'

-- British Library (id_library = 2)
(11, 2, 11, 5, 15.99), -- 'The Hobbit'
(12, 2, 12, 3, 18.50), -- 'The Fellowship of the Ring'
(13, 2, 13, 4, 19.75), -- 'The Two Towers'
(14, 2, 14, 2, 20.00), -- 'The Return of the King'
(15, 2, 15, 6, 17.50), -- 'The Silmarillion'
(16, 2, 16, 7, 14.99), -- 'The Shining'
(17, 2, 17, 5, 16.75), -- 'It'
(18, 2, 18, 4, 15.00), -- 'Misery'
(19, 2, 19, 3, 18.25), -- 'The Dark Tower I: The Gunslinger'
(20, 2, 20, 2, 19.50), -- 'The Dark Tower II: The Drawing of the Three'

-- Library of Congress (id_library = 3)
(21, 3, 21, 5, 20.99), -- 'Murder on the Orient Express'
(22, 3, 22, 3, 19.50), -- 'And Then There Were None'
(23, 3, 23, 4, 18.75), -- 'The A.B.C. Murders'
(24, 3, 24, 2, 17.50), -- 'Death on the Nile'
(25, 3, 25, 6, 16.00), -- 'The Murder of Roger Ackroyd'
(26, 3, 26, 5, 14.99), -- 'Digital Fortress'
(27, 3, 27, 3, 15.50), -- 'Angels & Demons'
(28, 3, 28, 4, 16.25), -- 'Deception Point'
(29, 3, 29, 2, 17.00), -- 'The Da Vinci Code'
(30, 3, 30, 3, 18.00), -- 'The Lost Symbol'

-- Boston Public Library (id_library = 4)
(31, 4, 31, 5, 13.99), -- 'Pride and Prejudice'
(32, 4, 32, 3, 12.50), -- 'Sense and Sensibility'
(33, 4, 33, 4, 14.75), -- 'Emma'
(34, 4, 34, 2, 13.00), -- 'Mansfield Park'
(35, 4, 35, 6, 15.50), -- 'Persuasion'
(36, 4, 36, 5, 11.99), -- 'Adventures of Huckleberry Finn'
(37, 4, 37, 3, 12.50), -- 'The Adventures of Tom Sawyer'
(38, 4, 38, 4, 14.00), -- 'A Connecticut Yankee in King Arthur''s Court'
(39, 4, 39, 2, 13.75), -- 'The Prince and the Pauper'
(40, 4, 40, 3, 14.50), -- 'Life on the Mississippi'

-- Los Angeles Public Library (id_library = 5)
(41, 5, 41, 5, 19.99), -- 'The Old Man and the Sea'
(42, 5, 42, 3, 18.50), -- 'A Farewell to Arms'
(43, 5, 43, 4, 20.75), -- 'For Whom the Bell Tolls'
(44, 5, 44, 2, 17.00), -- 'The Sun Also Rises'
(45, 5, 45, 6, 19.50), -- 'To Have and Have Not'
(46, 5, 46, 5, 22.99), -- 'The Great Gatsby'
(47, 5, 47, 3, 21.50), -- 'Tender Is the Night'
(48, 5, 48, 4, 20.75), -- 'This Side of Paradise'
(49, 5, 49, 2, 19.00), -- 'The Beautiful and Damned'
(50, 5, 50, 3, 21.25), -- 'The Last Tycoon'

-- Library of Congress (id_library = 3)
(51, 3, 55, 4, 23.00), -- 'One Hundred Years of Solitude'
(52, 3, 56, 5, 24.50), -- 'Love in the Time of Cholera'

-- Boston Public Library (id_library = 4)
(53, 4, 57, 6, 19.99), -- 'To Kill a Mockingbird'
(54, 4, 58, 3, 18.75), -- 'Go Set a Watchman'

-- Los Angeles Public Library (id_library = 5)
(55, 5, 59, 4, 25.00), -- 'War and Peace'
(56, 5, 60, 2, 22.50), -- 'Anna Karenina'
(57, 5, 61, 5, 20.00), -- 'Crime and Punishment'
(58, 5, 62, 3, 21.75), -- 'The Brothers Karamazov'
(59, 5, 63, 4, 19.50), -- 'The Picture of Dorian Gray'
(60, 5, 64, 2, 18.25); -- 'The Importance of Being Earnest'

SELECT setval('libraries_id_seq', (SELECT MAX(id) FROM libraries));

SELECT setval('stock_id_seq', (SELECT MAX(id) FROM stock));