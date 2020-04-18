set foreign_key_checks = 0;

-- delete all rows
truncate table user_authority;
truncate table authority;
truncate table user;
truncate table enrollment;
truncate table teaching;
truncate table exam;
truncate table student;
truncate table course;
truncate table teacher;
truncate table exam_period;

set foreign_key_checks = 1;

insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');

-- password is 'admin' (bcrypt encoded) 
insert into user (username, password) values ('admin', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
-- password is 'user' (bcrypt encoded)
insert into user (username, password) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq');

insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_USER

insert into student (card_number, first_name, last_name) values ('ra1-2014', 'Marko', 'Marković');
insert into student (card_number, first_name, last_name) values ('ra2-2014', 'Milan', 'Milanović');
insert into student (card_number, first_name, last_name) values ('ra3-2014', 'Ivana', 'Novaković');
insert into student (card_number, first_name, last_name) values ('ra4-2014', 'Bojan', 'Bojić');
insert into student (card_number, first_name, last_name) values ('ra5-2014', 'Jelena', 'Marković');
insert into student (card_number, first_name, last_name) values ('ra6-2014', 'Zoran', 'Zoranović');
insert into student (card_number, first_name, last_name) values ('ra7-2014', 'Milica', 'Petrović');
insert into student (card_number, first_name, last_name) values ('ra8-2014', 'Petar', 'Petrović');
insert into student (card_number, first_name, last_name) values ('ra9-2014', 'Igor', 'Jovin');

insert into course (name) values ('Matematika');
insert into course (name) values ('Osnove programiranja');
insert into course (name) values ('Objektno programiranje');

insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 3, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 4, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 1, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 2, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 3, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 5, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2015-01-01', '2015-06-01', 6, 2);

insert into teacher (first_name, last_name, rank) values ('Milan', 'Jovanovic', 'docent');
insert into teacher (first_name, last_name, rank) values ('Sanja', 'Stanic', 'vanredni profesor');
insert into teacher (first_name, last_name, rank) values ('Nemanja', 'Jankovic', 'redovni profesor');

insert into teaching (course_id, teacher_id) values (1, 1);
insert into teaching (course_id, teacher_id) values (1, 2);
insert into teaching (course_id, teacher_id) values (2, 2);
insert into teaching (course_id, teacher_id) values (3, 3);

insert into exam_period (name, start_date, end_date) values ('Januarski 2016', '2016-01-27', '2016-02-25');
insert into exam_period (name, start_date, end_date) values ('Aprilski 2016', '2016-04-18', '2016-04-23');
insert into exam_period (name, start_date, end_date) values ('Junski 2016', '2016-06-15', '2016-07-14');
insert into exam_period (name, start_date, end_date) values ('Septembarski 2016', '2016-08-27', '2016-09-25');

insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	1, 1, 1, '2016-02-01', 20, 70);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	1, 2, 2, '2016-04-19', 15, 55);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	2, 1, 1, '2016-02-01', 18, 60);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	2, 2, 2, '2016-04-19', 17, 57);


