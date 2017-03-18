CREATE TABLE Address (
  id      INT AUTO_INCREMENT PRIMARY KEY,
  country VARCHAR(100) NULL,
  city    VARCHAR(100) NULL,

);

CREATE TABLE Sex (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE User (
  id            INT AUTO_INCREMENT PRIMARY KEY,
  first_name    VARCHAR(50)  NOT NULL,
  last_name     VARCHAR(50)  NOT NULL,
  date_of_birth DATE         NULL,
  sex_id        VARCHAR(20)  NOT NULL,
  next_trip     VARCHAR(100) NULL,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  address_id    INT          NULL,

  FOREIGN KEY (address_id) REFERENCES Address (id),
  FOREIGN KEY (sex_id) REFERENCES Sex (id)
);

CREATE TABLE Friends (
  id      INT AUTO_INCREMENT PRIMARY KEY,
  first_user_id INT NOT NULL,
  second_user_id   INT NOT NULL,
  status  INT NOT NULL,

  FOREIGN KEY (first_user_id) REFERENCES User(id),
  FOREIGN KEY (second_user_id) REFERENCES User(id)
);

CREATE TABLE Message (
  id      INT AUTO_INCREMENT PRIMARY KEY,
  first_user_id INT NOT NULL,
  second_user_id   INT NOT NULL,
  message VARCHAR(2000) NOT NULL,
  date DATE NOT NULL,
  time TIME NOT NULL,
  status  INT NOT NULL,

  FOREIGN KEY (first_user_id) REFERENCES User(id),
  FOREIGN KEY (second_user_id) REFERENCES User(id)
);

CREATE TABLE News(
  id      INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  news_message VARCHAR(2000) NOT NULL,
  date DATE NOT NULL,
  time TIME NOT NULL,

  FOREIGN KEY (user_id) REFERENCES User(id),
);
