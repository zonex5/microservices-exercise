create table sales
(
    id         serial
        constraint sales_pk
            primary key,
    id_library integer                 not null,
    id_book    integer                 not null,
    quantity   integer   default 0     not null,
    sale_date  timestamp default now() not null
);

INSERT INTO sales (id, id_library, id_book, quantity, sale_date)
VALUES
-- New York Public Library (id_library = 1)
(1, 1, 1, 2, '2024-01-15 10:30:00'),   -- 'Harry Potter and the Philosopher''s Stone'
(2, 1, 6, 1, '2024-02-20 14:45:00'),   -- 'A Game of Thrones'
(3, 1, 11, 3, '2024-03-10 09:20:00'),  -- 'The Hobbit'
(4, 1, 16, 1, '2024-04-05 16:10:00'),  -- 'The Shining'
(5, 1, 21, 2, '2024-05-22 11:00:00'),  -- 'Murder on the Orient Express'
(6, 1, 26, 1, '2024-06-18 13:30:00'),  -- 'Digital Fortress'
(7, 1, 31, 2, '2024-07-25 15:15:00'),  -- 'Pride and Prejudice'
(8, 1, 36, 1, '2024-08-30 10:50:00'),  -- 'Adventures of Huckleberry Finn'
(9, 1, 41, 1, '2024-09-12 12:40:00'),  -- 'The Old Man and the Sea'
(10, 1, 46, 2, '2024-10-05 14:25:00'), -- 'The Great Gatsby'

-- British Library (id_library = 2)
(11, 2, 2, 1, '2024-01-20 10:00:00'),  -- 'Harry Potter and the Chamber of Secrets'
(12, 2, 7, 2, '2024-02-25 13:30:00'),  -- 'A Clash of Kings'
(13, 2, 12, 1, '2024-03-15 09:45:00'), -- 'The Fellowship of the Ring'
(14, 2, 17, 1, '2024-04-22 16:00:00'), -- 'It'
(15, 2, 22, 2, '2024-05-30 11:20:00'), -- 'And Then There Were None'
(16, 2, 27, 1, '2024-06-25 14:10:00'), -- 'Angels & Demons'
(17, 2, 32, 1, '2024-07-18 10:30:00'), -- 'Sense and Sensibility'
(18, 2, 37, 2, '2024-08-22 13:50:00'), -- 'The Adventures of Tom Sawyer'
(19, 2, 42, 1, '2024-09-28 15:40:00'), -- 'A Farewell to Arms'
(20, 2, 47, 1, '2024-10-12 12:15:00'), -- 'Tender Is the Night'

-- Library of Congress (id_library = 3)
(21, 3, 3, 2, '2024-01-10 09:00:00'),  -- 'Harry Potter and the Prisoner of Azkaban'
(22, 3, 8, 1, '2024-02-14 14:30:00'),  -- 'A Storm of Swords'
(23, 3, 13, 1, '2024-03-20 10:15:00'), -- 'The Two Towers'
(24, 3, 18, 1, '2024-04-25 17:00:00'), -- 'Misery'
(25, 3, 23, 2, '2024-05-30 12:45:00'), -- 'The A.B.C. Murders'
(26, 3, 28, 1, '2024-06-15 15:25:00'), -- 'Deception Point'
(27, 3, 33, 1, '2024-07-20 11:10:00'), -- 'Emma'
(28, 3, 38, 2, '2024-08-25 14:55:00'), -- 'A Connecticut Yankee in King Arthur''s Court'
(29, 3, 43, 1, '2024-09-30 16:40:00'), -- 'For Whom the Bell Tolls'
(30, 3, 48, 1, '2024-10-18 13:35:00'), -- 'This Side of Paradise'

-- Boston Public Library (id_library = 4)
(31, 4, 4, 1, '2024-01-05 10:20:00'),  -- 'Harry Potter and the Goblet of Fire'
(32, 4, 9, 2, '2024-02-10 13:45:00'),  -- 'A Feast for Crows'
(33, 4, 14, 1, '2024-03-18 09:30:00'), -- 'The Return of the King'
(34, 4, 19, 1, '2024-04-22 16:15:00'), -- 'The Dark Tower I: The Gunslinger'
(35, 4, 24, 2, '2024-05-28 11:50:00'), -- 'Death on the Nile'
(36, 4, 29, 1, '2024-06-22 14:05:00'), -- 'The Da Vinci Code'
(37, 4, 34, 1, '2024-07-25 10:40:00'), -- 'Mansfield Park'
(38, 4, 39, 2, '2024-08-30 13:15:00'), -- 'The Prince and the Pauper'
(39, 4, 44, 1, '2024-09-15 15:55:00'), -- 'The Sun Also Rises'
(40, 4, 49, 1, '2024-10-20 12:30:00'), -- 'The Beautiful and Damned'

-- Los Angeles Public Library (id_library = 5)
(41, 5, 5, 2, '2024-01-12 10:10:00'),  -- 'Harry Potter and the Order of the Phoenix'
(42, 5, 10, 1, '2024-02-18 14:40:00'), -- 'A Dance with Dragons'
(43, 5, 15, 1, '2024-03-25 09:25:00'), -- 'The Silmarillion'
(44, 5, 20, 1, '2024-04-30 16:35:00'), -- 'The Dark Tower II: The Drawing of the Three'
(45, 5, 25, 2, '2024-05-05 12:20:00'), -- 'The Murder of Roger Ackroyd'
(46, 5, 30, 1, '2024-06-10 14:55:00'), -- 'The Lost Symbol'
(47, 5, 35, 1, '2024-07-15 11:30:00'), -- 'Persuasion'
(48, 5, 40, 2, '2024-08-20 13:50:00'), -- 'Life on the Mississippi'
(49, 5, 45, 1, '2024-09-25 15:10:00'), -- 'To Have and Have Not'
(50, 5, 50, 1, '2024-10-30 12:45:00'); -- 'The Last Tycoon'

SELECT setval('sales_id_seq', (SELECT MAX(id) FROM sales));