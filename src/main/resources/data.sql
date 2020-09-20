insert into users(mydtype, email,username, password, first_name, last_name, phone_number,  birth_date, role, active) values
('users', 'admin1@yahoo.com', 'ad1' ,'123', 'Admin1', 'Admin1', '061-543-543', '1998-01-01', 'ADMIN', 'true'),
('users', 'admin2@yahoo.com', 'ad2' ,'123', 'Admin2', 'Admin2', '062-531-546', '1997-02-02', 'ADMIN', 'true'),
('users', 'm1@yahoo.com', 'm1' ,'123', 'Manager1', 'Manager1', '063-765-432', '1996-03-03', 'MANAGER', 'true'),
('users', 'm2@yahoo.com', 'm2' ,'123', 'Manager2', 'Manager2', '064-643-785', '1995-04-04', 'MANAGER', 'true'),
('users', 'viewer1@yahoo.com', 'sp1' ,'123', 'Viewer1', 'Viewer1', '065-742-923', '1994-05-05', 'SPECTATOR', 'true');

insert into cinema(id, name, address, email, phone) values
(1, 'Cinema1', 'Address1', 'c1@yahoo.com', '064-346-443'),
(2, 'Cinema2', 'Address2', 'c2@yahoo.com', '063-765-743'),
(3, 'Cinema3', 'Address3', 'c3@yahoo.com', '062-600-743');

insert into cinema_user(cinema_id, user_username) values
(1, 'm1'),
(2, 'm1'),
(3, 'm2'),
(3, 'm1');

insert into moviehalls(id, label, capacity, cinema_id) values
(1,'Theater1', 50, 1),
(2,'VM12', 20, 2),
(3,'BIG ROOM', 20, 3),
(4,'Theater2', 40, 1),
(5,'Theater3', 10, 1);