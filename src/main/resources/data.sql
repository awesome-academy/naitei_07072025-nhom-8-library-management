INSERT INTO authors (name, biography, avatar)
VALUES
    ('J.K. Rowling', 'British author, best known for Harry Potter.', 'rowling.jpg'),
    ('George R.R. Martin', 'American novelist and short-story writer, Game of Thrones.', 'martin.jpg'),
    ('Haruki Murakami', 'Japanese writer, known for Norwegian Wood and Kafka on the Shore.', 'murakami.jpg'),
    ('Dan Brown', 'American author, best known for The Da Vinci Code.', 'brown.jpg'),
    ('Agatha Christie', 'English writer, known as the "Queen of Crime".', 'christie.jpg'),
    ('Stephen King', 'American author of horror, supernatural fiction, suspense, and fantasy novels.', 'king.jpg'),
    ('Paulo Coelho', 'Brazilian lyricist and novelist, best known for The Alchemist.', 'coelho.jpg'),
    ('Nguyễn Nhật Ánh', 'Vietnamese author of children''s and young adult books.', 'anha.jpg'),
    ('Victor Hugo', 'French poet, novelist, and dramatist, Les Misérables.', 'hugo.jpg'),
    ('Leo Tolstoy', 'Russian writer, regarded as one of the greatest authors of all time.', 'tolstoy.jpg');

INSERT INTO genres (name, parent_id)
VALUES
    ('Fiction', NULL),
    ('Non-Fiction', NULL),
    ('Science Fiction', 1),
    ('Fantasy', 1),
    ('Mystery', 1),
    ('Romance', 1),
    ('Biography', 2),
    ('History', 2),
    ('Self-help', 2),
    ('Children', 1);

INSERT INTO publishers (name, address, logo)
VALUES
    ('Penguin Random House', 'New York, USA', 'penguin.png'),
    ('HarperCollins', 'New York, USA', 'harpercollins.png'),
    ('Simon & Schuster', 'New York, USA', 'simon.png'),
    ('Macmillan Publishers', 'London, UK', 'macmillan.png'),
    ('Hachette Livre', 'Paris, France', 'hachette.png'),
    ('Nhà Xuất Bản Trẻ', 'TP. Hồ Chí Minh, Việt Nam', 'nxbtre.png'),
    ('Nhà Xuất Bản Kim Đồng', 'Hà Nội, Việt Nam', 'kimdong.png'),
    ('Shueisha', 'Tokyo, Japan', 'shueisha.png'),
    ('Kodansha', 'Tokyo, Japan', 'kodansha.png'),
    ('Bloomsbury Publishing', 'London, UK', 'bloomsbury.png');

INSERT INTO users (username, password, full_name, email, phone, avatar, role, activation_date)
VALUES
    ('admin1', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'System Admin', 'admin1@example.com', '0900000001', 'admin1.png', 'admin', NOW()),
    ('user1', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Nguyen Van A', 'user1@example.com', '0900000002', 'user1.png', 'user', NOW()),
    ('user2', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Tran Thi B', 'user2@example.com', '0900000003', 'user2.png', 'user', NOW()),
    ('user3', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Le Van C', 'user3@example.com', '0900000004', 'user3.png', 'user', NOW()),
    ('user4', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Pham Thi D', 'user4@example.com', '0900000005', 'user4.png', 'user', NOW()),
    ('user5', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Hoang Van E', 'user5@example.com', '0900000006', 'user5.png', 'user', NOW()),
    ('user6', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Do Thi F', 'user6@example.com', '0900000007', 'user6.png', 'user', NOW()),
    ('user7', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Bui Van G', 'user7@example.com', '0900000008', 'user7.png', 'user', NOW()),
    ('user8', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Nguyen Thi H', 'user8@example.com', '0900000009', 'user8.png', 'user', NOW()),
    ('user9', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Tran Van I', 'user9@example.com', '0900000010', 'user9.png', 'user', NOW()),
    ('user10', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Le Thi J', 'user10@example.com', '0900000011', 'user10.png', 'user', NOW()),
    ('user11', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Pham Van K', 'user11@example.com', '0900000012', 'user11.png', 'user', NOW()),
    ('user12', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Hoang Thi L', 'user12@example.com', '0900000013', 'user12.png', 'user', NOW()),
    ('user13', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Do Van M', 'user13@example.com', '0900000014', 'user13.png', 'user', NOW()),
    ('user14', '$2a$10$QJM4dei3aXbtc4n42Lan5eDepE1EX.sHNU6wPJ4gjWt6CuLYycraW', 'Nguyen Thi N', 'user14@example.com', '0900000015', 'user14.png', 'user', NOW());

INSERT INTO follows (user_id, target_type, target_id, created_at)
VALUES
    (2, 'author', 1, NOW()),   -- user1 follow J.K. Rowling
    (3, 'author', 2, NOW()),   -- user2 follow George R.R. Martin
    (4, 'author', 3, NOW()),   -- user3 follow Haruki Murakami
    (5, 'author', 4, NOW()),   -- user4 follow Dan Brown
    (6, 'author', 5, NOW()),   -- user5 follow Agatha Christie
    (7, 'publisher', 1, NOW()), -- user6 follow Penguin Random House
    (8, 'publisher', 2, NOW()), -- user7 follow HarperCollins
    (9, 'publisher', 6, NOW()), -- user8 follow NXB Trẻ
    (10, 'publisher', 7, NOW()),-- user9 follow NXB Kim Đồng
    (11, 'publisher', 8, NOW());-- user10 follow Shueisha

INSERT INTO books (title, description, author_id, genre_id, publisher_id, published_year, borrow_duration_days, total_quantity, available_quantity)
VALUES
    ('Harry Potter and the Philosopher''s Stone', 'Fantasy novel about a young wizard.', 1, 4, 10, 1997, 14, 10, 10),
    ('A Game of Thrones', 'Epic fantasy novel, first in A Song of Ice and Fire.', 2, 4, 1, 1996, 14, 8, 8),
    ('Norwegian Wood', 'Romantic coming-of-age novel set in Japan.', 3, 6, 8, 1987, 14, 5, 5),
    ('The Da Vinci Code', 'Mystery-detective novel with symbology and history.', 4, 5, 2, 2003, 14, 7, 7),
    ('Murder on the Orient Express', 'Detective novel featuring Hercule Poirot.', 5, 5, 4, 1934, 14, 6, 6),
    ('It', 'Horror novel about a shapeshifting entity.', 6, 1, 3, 1986, 14, 12, 12),
    ('The Alchemist', 'Novel about following dreams and destiny.', 7, 1, 5, 1988, 14, 9, 9),
    ('Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Vietnamese children''s literature classic.', 8, 10, 6, 2007, 14, 15, 15),
    ('Les Misérables', 'Historical novel set in 19th century France.', 9, 8, 4, 1862, 14, 4, 4),
    ('War and Peace', 'Epic historical novel about Napoleonic wars.', 10, 8, 1, 1869, 14, 3, 3),
    ('Kafka on the Shore', 'Magical realism novel by Haruki Murakami.', 3, 4, 9, 2002, 14, 6, 6),
    ('Angels & Demons', 'Thriller novel featuring Robert Langdon.', 4, 5, 2, 2000, 14, 7, 7),
    ('The Shining', 'Horror novel about a haunted hotel.', 6, 1, 3, 1977, 14, 10, 10),
    ('The Little Prince', 'Philosophical children''s book.', 9, 10, 7, 1943, 14, 20, 20),
    ('Anna Karenina', 'Tragic romance novel set in Imperial Russia.', 10, 6, 1, 1878, 14, 5, 5);

INSERT INTO copies (book_id, copy_code)
VALUES
    (1, 'BOOK1-C1'),
    (1, 'BOOK1-C2'),
    (2, 'BOOK2-C1'),
    (2, 'BOOK2-C2'),
    (3, 'BOOK3-C1'),
    (3, 'BOOK3-C2'),
    (4, 'BOOK4-C1'),
    (4, 'BOOK4-C2'),
    (5, 'BOOK5-C1'),
    (6, 'BOOK6-C1'),
    (6, 'BOOK6-C2'),
    (7, 'BOOK7-C1'),
    (8, 'BOOK8-C1'),
    (8, 'BOOK8-C2'),
    (9, 'BOOK9-C1'),
    (10, 'BOOK10-C1'),
    (11, 'BOOK11-C1'),
    (12, 'BOOK12-C1'),
    (13, 'BOOK13-C1'),
    (14, 'BOOK14-C1');

INSERT INTO book_unavailabilities (copy_id, status, issue_description)
VALUES
    (1, 'damaged', 'Torn pages'),
    (2, 'lost', 'Lost by borrower'),
    (3, 'damaged', 'Cover missing'),
    (5, 'damaged', 'Water damage'),
    (8, 'lost', 'Never returned');

INSERT INTO images (book_id, image, is_cover)
VALUES
    (1, 'hp1_cover.jpg', TRUE),
    (1, 'hp1_extra1.jpg', FALSE),
    (2, 'got_cover.jpg', TRUE),
    (3, 'norwegian_cover.jpg', TRUE),
    (4, 'davinci_cover.jpg', TRUE),
    (5, 'orient_cover.jpg', TRUE),
    (6, 'it_cover.jpg', TRUE),
    (7, 'alchemist_cover.jpg', TRUE),
    (8, 'viettuoi_cover.jpg', TRUE),
    (8, 'viettuoi_extra1.jpg', FALSE),
    (9, 'lesmis_cover.jpg', TRUE),
    (10, 'warpeace_cover.jpg', TRUE),
    (11, 'kafka_cover.jpg', TRUE),
    (12, 'angels_cover.jpg', TRUE),
    (13, 'shining_cover.jpg', TRUE);

INSERT INTO book_comments (user_id, book_id, content)
VALUES
    (2, 1, 'Loved this book! A magical start to the series.'),
    (3, 2, 'Epic storytelling, a bit complex but worth it.'),
    (4, 3, 'Beautifully written, very emotional.'),
    (5, 4, 'Fast-paced and thrilling, could not put it down.'),
    (6, 8, 'Childhood memories, very touching.');

INSERT INTO favorites (user_id, book_id)
VALUES
    (2, 1),  -- user1 favorite Harry Potter
    (3, 2),  -- user2 favorite Game of Thrones
    (4, 7),  -- user3 favorite The Alchemist
    (5, 8),  -- user4 favorite Cho Tôi Xin Một Vé Đi Tuổi Thơ
    (6, 13); -- user5 favorite The Shining

INSERT INTO ratings (user_id, book_id, rating, comment)
VALUES
    (2, 1, 5, 'Masterpiece, highly recommended.'),
    (3, 2, 4, 'Very detailed, a bit long but excellent.'),
    (4, 7, 5, 'Inspirational and life-changing.'),
    (5, 8, 4, 'Heartwarming and nostalgic.'),
    (6, 13, 5, 'Scary and thrilling, classic Stephen King.');

INSERT INTO borrow_requests (user_id, from_date, to_date, status, reject_reason)
VALUES
    (2, '2025-08-01', '2025-08-15', 'approved', NULL),
    (3, '2025-08-02', '2025-08-16', 'partially_approved', 'One book not available'),
    (4, '2025-08-03', '2025-08-17', 'pending', NULL),
    (5, '2025-08-04', '2025-08-18', 'rejected', 'Exceeded borrow limit'),
    (6, '2025-08-05', '2025-08-19', 'approved', NULL);

INSERT INTO borrow_request_details (borrow_request_id, copy_id, status, reject_reason)
VALUES
    (1, 1, 'approved', NULL),   -- user1
    (1, 2, 'approved', NULL),
    (2, 3, 'approved', NULL),   -- user2
    (2, 4, 'rejected', 'Book lost'),
    (2, 5, 'approved', NULL),
    (3, 6, 'pending', NULL),    -- user3
    (3, 7, 'pending', NULL),
    (4, 8, 'rejected', 'Borrow request rejected'), -- user4
    (5, 9, 'approved', NULL),   -- user5
    (5, 10, 'approved', NULL);

INSERT INTO borrow_records (borrow_request_detail_id, borrowed_at, due_date, returned_at, status)
VALUES
    (1, '2025-08-01', '2025-08-15', '2025-08-10', 'returned'),
    (2, '2025-08-01', '2025-08-15', NULL, 'borrowed'),
    (3, '2025-08-02', '2025-08-16', NULL, 'overdue'),
    (5, '2025-08-02', '2025-08-16', '2025-08-12', 'returned'),
    (9, '2025-08-05', '2025-08-19', NULL, 'borrowed');
