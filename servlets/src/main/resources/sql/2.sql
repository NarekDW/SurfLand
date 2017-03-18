INSERT INTO Sex(id, name) VALUES (1, 'Male');
INSERT INTO Sex(id, name) VALUES (2, 'Female');

INSERT INTO Address(country, city) VALUES ('США', 'Лос-Анджелос');
INSERT INTO Address(country, city) VALUES ('США', 'Бостон');
INSERT INTO Address(country, city) VALUES ('Бразилия', 'Рио-де-Жанейро');
INSERT INTO Address(country, city) VALUES ('Испания', 'Барселона');
INSERT INTO Address(country, city) VALUES ('Испания', 'Мадрид');


INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password)
VALUES ('Кейли',
        'Слейтер',
        '1980-03-22',
        '1',
        'Бали',
        'test1@m',
        'qwerty');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Борис',
        'Иванов',
        '1981-03-22',
        '1',
        'Филипины',
        'test5@m',
        'qwerty',
        '2');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Генадий',
        'Петров',
        '1984-03-22',
        '1',
        'Бали',
        'test6@m',
        'qwerty',
        '2');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Мик',
        'Фэннинг',
        '1988-11-13',
        '1',
        'Таити',
        'test2@m',
        'qwerty2',
        '3');


INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Том',
        'Каррен',
        '1990-01-18',
        '1',
        'Таити',
        'test3@m',
        'qwerty3',
        '4');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Авраам',
        'Линкольн',
        '1995-01-18',
        '1',
        'Филипины',
        'test4@m',
        'qwerty3',
        '4');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Нарек',
        'Карапетян',
        '1998-01-18',
        '1',
        'Филипины',
        'test7@m',
        'qwerty3',
        '5');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Анастасия',
        'Иванова',
        '2000-01-18',
        '2',
        'Бали',
        'test8@m',
        'qwerty3',
        '1');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Таисия',
        'Петрова',
        '2005-01-18',
        '2',
        'Филипины',
        'test9@m',
        'qwerty3',
        '2');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Алекса',
        'Александрова',
        '2006-01-18',
        '2',
        'Таити',
        'test10@m',
        'qwerty3',
        '4');

INSERT INTO User (first_name, last_name, date_of_birth, sex_id, next_trip, email, password, address_id)
VALUES ('Юлия',
        'Петрова',
        '2007-01-18',
        '2',
        'Бали',
        'test11@m',
        'qwerty3',
        '5');


CREATE TABLE Roles (
  email VARCHAR(255) NOT NULL,
  role  VARCHAR(15)  NOT NULL,
  PRIMARY KEY (email, role),
  FOREIGN KEY (email) REFERENCES User (email)
);

INSERT INTO Roles (email, role) VALUES ('test7@m', 'user');
INSERT INTO Roles (email, role) VALUES ('test6@m', 'admin');
INSERT INTO Roles (email, role) VALUES ('test5@m', 'user');
INSERT INTO Roles (email, role) VALUES ('test4@m', 'user');