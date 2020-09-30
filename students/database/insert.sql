set foreign_key_checks = 0;

-- delete all rows
truncate table authority;
truncate table user;
truncate table enrollment;
truncate table teaching;
truncate table exam;
truncate table student;
truncate table course;
truncate table teacher;
truncate table exam_period;
truncate table document;
truncate table payment;

set foreign_key_checks = 1;

insert into authority (name) values ('ADMIN');
insert into authority (name) values ('STUDENT');
insert into authority (name) values ('NASTAVNIK');


insert into student (card_number, first_name, last_name) values ('sf1-2014', 'Marko', 'Marković');
insert into student (card_number, first_name, last_name) values ('sf2-2014', 'Milan', 'Milanović');
insert into student (card_number, first_name, last_name) values ('sf3-2014', 'Ivana', 'Novaković');
insert into student (card_number, first_name, last_name) values ('sf4-2014', 'Bojan', 'Bojić');
insert into student (card_number, first_name, last_name) values ('sf5-2014', 'Jelena', 'Marković');
insert into student (card_number, first_name, last_name) values ('sf6-2014', 'Zoran', 'Zoranović');
insert into student (card_number, first_name, last_name) values ('sf7-2014', 'Milica', 'Petrović');
insert into student (card_number, first_name, last_name) values ('sf8-2014', 'Petar', 'Petrović');
insert into student (card_number, first_name, last_name) values ('sf9-2014', 'Igor', 'Jovin');

insert into course (name) values ('Matematika');
insert into course (name) values ('Osnove programiranja');
insert into course (name) values ('Objektno programiranje');
insert into course (name) values ('TSEO');
insert into course (name) values ('Sistemski Softver');

insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 3, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 4, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 1, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 2, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 3, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 5, 1);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 6, 2);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 1, 4);
insert into enrollment (start_date, end_date, student_id, course_id) values ('2020-01-01', '2021-01-01', 1, 5);

insert into teacher (first_name, last_name, teacher_rank) values ('Milan', 'Jovanovic', 'Profesor');
insert into teacher (first_name, last_name, teacher_rank) values ('Sanja', 'Stanic', 'Asistent');
insert into teacher (first_name, last_name, teacher_rank) values ('Nemanja', 'Jankovic', 'Demonstrator');

insert into teaching (course_id, teacher_id) values (1, 1);
insert into teaching (course_id, teacher_id) values (1, 2);
insert into teaching (course_id, teacher_id) values (4, 1);
insert into teaching (course_id, teacher_id) values (2, 2);
insert into teaching (course_id, teacher_id) values (3, 3);

insert into exam_period (name, start_date, end_date) values ('Januarski 2020', '2020-01-27', '2020-02-25');
insert into exam_period (name, start_date, end_date) values ('Aprilski 2020', '2020-04-18', '2020-04-23');
insert into exam_period (name, start_date, end_date) values ('Septembarski 2020', '2020-09-20', '2020-10-05');
insert into exam_period (name, start_date, end_date) values ('Oktobarski 2020', '2020-10-05', '2020-10-25');

insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	1, 1, 1, '2020-02-01 15:00:00', 20, 70);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	1, 2, 2, '2020-04-19 17:00:00', 15, 55);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	1, 4, 2, '2020-04-19 09:00:00', 15, 30);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	2, 1, 1, '2020-02-01 12:00:00', 18, 60);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points) values (
	2, 2, 2, '2020-04-19 20:00:00', 17, 57);
    
insert into document(naziv, student_id) values ('Izvod iz matične knjige rođenih', 1);
insert into document(naziv, student_id) values ('Svedočanstvo', 1);
insert into document(naziv, student_id) values ('Diploma', 2);
insert into document(naziv, student_id) values ('Uverenje o uplati', 3);

insert into payment(date, svrha_uplate, vrednost_uplate, student_id) values ('2020-09-24 16:00:00', 'Prijava ispita', 600, 1);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id) values ('2020-09-25 14:00:00', 'Prva rata za godinu', 22500, 1);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id) values ('2020-09-26 17:00:00', 'Overa semestra', 2500, 2);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id) values ('2020-09-27 11:00:00', 'Lopovluk fakulteta', 2000, 3);

INSERT INTO user (password, username, authority_id) VALUES ('admin', 'admin',1);
INSERT INTO user (password, username, authority_id,teacher_id) VALUES ('nastavnik', 'nastavnik', 3, 1);
INSERT INTO user (password, username, authority_id,student_id) VALUES ('student', 'student', 2, 1);



