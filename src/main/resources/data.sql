insert into users(mydtype, email,username, password, first_name, last_name, phone_number,  birth_date, role, active, deleted) values
('users', 'admin1@yahoo.com', 'ad1' ,'123', 'Admin1', 'Admin1', '061-543-543', '1998-01-01', 'ADMIN', 'true' , 'false'),
('users', 'admin2@yahoo.com', 'ad2' ,'123', 'Admin2', 'Admin2', '062-531-546', '1997-02-02', 'ADMIN', 'true', 'false'),
('users', 'm1@yahoo.com', 'm1' ,'123', 'Manager1', 'Manager1', '063-765-432', '1996-03-03', 'MANAGER', 'true', 'false'),
('users', 'm2@yahoo.com', 'm2' ,'123', 'Manager2', 'Manager2', '064-643-785', '1995-04-04', 'MANAGER', 'false', 'false'),
('users', 'viewer1@yahoo.com', 'sp1' ,'123', 'Viewer1', 'Viewer1', '065-742-923', '1994-05-05', 'SPECTATOR', 'true', 'false'),
('users', 'viewer222@yahoo.com', 'sp2' ,'123', 'Viewer2', 'Viewer2', '065-742-923', '1994-05-05', 'SPECTATOR', 'true', 'false');

insert into cinema(id, name, address, email, phone, deleted) values
(1, 'Cinema1', 'Address1', 'c1@yahoo.com', '064-346-443', 'false'),
(2, 'Cinema2', 'Address2', 'c2@yahoo.com', '063-765-743', 'false'),
(3, 'Cinema3', 'Address3', 'c3@yahoo.com', '062-600-743', 'false');

insert into cinema_user(cinema_id, user_username) values
(1, 'm1'),
(2, 'm1'),
(3, 'm2'),
(3, 'm1');

insert into moviehalls(id, label, capacity, cinema_id, deleted) values
(1,'Theater1', 50, 1, 'false'),
(2,'VM12', 20, 2, 'false'),
(3,'BIG ROOM', 20, 3, 'false'),
(4,'Theater2', 40, 1, 'false'),
(5,'Theater3', 10, 1, 'false'),
(6,'Theater5', 50, 2, 'false');


insert into movies(title, description, duration, genre) values
('Movie1', 'Opis1', 60, 'KOMEDIJA'),
('Movie2', 'Opis2', 90, 'KOMEDIJA'),
('Movie3', 'Opis3', 95, 'AKCIJA'),
('Movie4', 'Opis4', 60, 'HOROR'),
('Movie5', 'Opis5', 65, 'TRILER' ),
('Movie7', 'Opis7', 90, 'AKCIJA'),
('Movie8', 'Opis8',80, 'NAUCNA FANTASTIKA');

insert into projections(id, date_time, not_reserved_seats, price, hall_id, movie_id, deleted) values
(1, '2020-06-11T10:00:00', 50, 150, 1, 'Movie1', 'false'),
(2, '2020-10-11T11:30:00', 50, 200, 1, 'Movie1', 'false'),
(3, '2020-10-11T13:00:00', 50, 250, 1, 'Movie2', 'false'),
(4, '2020-11-11T15:00:00', 50, 250, 1, 'Movie2', 'false'),
(5, '2020-11-11T17:00:00', 50, 300, 1, 'Movie2', 'false'),
(6, '2020-11-11T19:00:00', 50, 350, 1, 'Movie3', 'false'),
(7, '2020-11-11T21:30:00', 50, 400, 1, 'Movie3', 'false'),

(8, '2020-09-11T13:30:00', 40, 200, 4, 'Movie4', 'false'),
(9, '2020-09-11T15:00:00', 40, 200, 4, 'Movie4', 'false'),
(10, '2020-09-11T16:30:00', 40, 250, 4, 'Movie4', 'false'),
(11, '2020-09-11T18:00:00', 40, 300, 4, 'Movie7', 'false'),
(12, '2020-09-11T20:00:00', 40, 350, 4, 'Movie8', 'false'),

(13, '2020-07-11T16:30:00', 10, 250, 5, 'Movie5', 'false'),
(14, '2020-07-11T19:00:00', 10, 300, 5, 'Movie5', 'false'),
(15, '2020-06-11T21:30:00', 10, 300, 5, 'Movie4', 'false');


insert into votes(id, vote, movie_id, spectator_id) values
(1, 1, 'Movie5', 'sp1'),
(2, 4, 'Movie1', 'sp1'),
(3, 3, 'Movie1', 'sp2'),
(4, 5, 'Movie3', 'sp2');

insert into tickets(id, deleted, status, projection_id, spectator_id) values
(1, 'false', 'RESERVED', 2, 'sp1'),
(2, 'false', 'BOUGHT',1, 'sp1'),
(3, 'false','RESERVED', 2,  'sp2'),
(4, 'false','RESERVED', 6, 'sp2'),
(5, 'false', 'BOUGHT',13, 'sp1'),
(6, 'false', 'BOUGHT',15,  'sp1'),
(7, 'false', 'BOUGHT',13, 'sp2'),
(8, 'false', 'BOUGHT',15, 'sp2');