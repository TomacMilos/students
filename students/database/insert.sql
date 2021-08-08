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


insert into student (card_number, first_name, last_name, active) values ('sf1-2014', 'Marko', 'Marković', 1);
insert into student (card_number, first_name, last_name, active) values ('sf2-2014', 'Milan', 'Milanović', 1);
insert into student (card_number, first_name, last_name, active) values ('sf3-2014', 'Ivana', 'Novaković', 1);
insert into student (card_number, first_name, last_name, active) values ('sf4-2014', 'Bojan', 'Bojić' , 1);
insert into student (card_number, first_name, last_name, active) values ('sf5-2014', 'Jelena', 'Marković', 1);
insert into student (card_number, first_name, last_name, active) values ('sf6-2014', 'Zoran', 'Zoranović', 1);
insert into student (card_number, first_name, last_name, active) values ('sf7-2014', 'Milica', 'Petrović', 1);
insert into student (card_number, first_name, last_name, active) values ('sf8-2014', 'Petar', 'Petrović', 1);
insert into student (card_number, first_name, last_name, active) values ('sf9-2014', 'Igor', 'Jovin', 1);

insert into course (name, active) values ('Matematika',1);
insert into course (name, active) values ('Osnove programiranja',1);
insert into course (name, active) values ('Objektno programiranje',1);
insert into course (name, active) values ('TSEO',1);
insert into course (name, active) values ('Sistemski Softver',1);

insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 1, 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 2, 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 3, 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 4, 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 1, 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 2, 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 3, 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 5, 1, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 6, 2, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 1, 4, 1);
insert into enrollment (start_date, end_date, student_id, course_id, active) values ('2021-06-01', '2021-08-02', 1, 5, 1);

insert into teacher (first_name, last_name, teacher_rank, active) values ('Milan', 'Jovanovic', 'Profesor', 1);
insert into teacher (first_name, last_name, teacher_rank, active) values ('Sanja', 'Stanic', 'Asistent', 1);
insert into teacher (first_name, last_name, teacher_rank, active) values ('Nemanja', 'Jankovic', 'Demonstrator', 1);

insert into teaching (course_id, teacher_id) values (1, 1);
insert into teaching (course_id, teacher_id) values (1, 2);
insert into teaching (course_id, teacher_id) values (4, 1);
insert into teaching (course_id, teacher_id) values (2, 2);
insert into teaching (course_id, teacher_id) values (3, 3);

insert into exam_period (name, start_date, end_date, active) values ('Januarski 2021', '2020-07-27', '2020-02-25', 1);
insert into exam_period (name, start_date, end_date, active) values ('Avgust 2022', '2021-07-20', '2021-08-20', 1);
insert into exam_period (name, start_date, end_date, active) values ('Jun 2021', '2020-06-20', '2021-07-15', 1);
insert into exam_period (name, start_date, end_date, active) values ('Oktobarski 2020', '2020-10-05', '2020-10-25', 1);

insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points, active) values (
	1, 1, 1, '2020-02-01 15:00:00', 20, 70, 1);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points, active) values (
	1, 2, 2, '2020-04-19 17:00:00', 15, 55, 1);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points, active) values (
	1, 4, 2, '2020-04-19 09:00:00', 15, 30, 1);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points, active) values (
	2, 1, 1, '2020-02-01 12:00:00', 18, 60, 1);
insert into exam (student_id, course_id, exam_period_id, date, exam_points, lab_points, active) values (
	2, 2, 2, '2020-04-19 20:00:00', 17, 57, 1);

insert into document(naziv, student_id, active) values ('Izvod iz matične knjige rođenih', 1, 1);
insert into document(naziv, student_id, active) values ('Svedočanstvo', 1, 1);
insert into document(naziv, student_id, active) values ('Diploma', 2, 1);
insert into document(naziv, student_id, active) values ('Uverenje o uplati', 3, 1);

insert into payment(date, svrha_uplate, vrednost_uplate, student_id, active) values ('2020-09-24 16:00:00', 'Prijava ispita', 600, 1, 1);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id, active) values ('2020-09-25 14:00:00', 'Prva rata za godinu', 22500, 1, 1);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id, active) values ('2020-09-26 17:00:00', 'Overa semestra', 2500, 2, 1);
insert into payment(date, svrha_uplate, vrednost_uplate, student_id, active) values ('2020-09-27 11:00:00', 'Lopovluk fakulteta', 2000, 3, 1);

INSERT INTO user (password, username, authority_id, active) VALUES ('$2a$10$aMkZ0V2V7F8dTQ1P0IDEDOE9vw8iQMvqy4.rhtRoaJFBepw7k2EyC', 'admin', 1, 1);
INSERT INTO user (password, username, authority_id,teacher_id, active) VALUES ('$2a$10$JSnG08cYFz19DymztcQXkuM0ZD/Fe/UW8kP/Xg.zQ.VaY3mz6q2aC', 'nastavnik', 3, 1, 1);
INSERT INTO user (password, username, authority_id,student_id, active) VALUES ('$2a$10$0IcVThsIiH6zE4E3fgB2Oe/RpO2j4FzKCl3WgR2pBtdputEKkqyvS', 'student', 2, 1, 1);