DROP DATABASE IF EXISTS awd;
CREATE DATABASE awd;
USE awd;

DROP TABLE IF EXISTS credential;
CREATE TABLE credential
(
  id         INT AUTO_INCREMENT
    PRIMARY KEY,
  email      VARCHAR(50)                             NOT NULL,
  salt       VARBINARY(128)                          NOT NULL,
  hashed_psw VARCHAR(256)                            NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL,
  last_seen  TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  token      VARCHAR(50)                             NULL,
  expiry     TIMESTAMP                               NULL,
  user_type  INT                                     NOT NULL,
  CONSTRAINT `credential;_email_uindex`
  UNIQUE (email)
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
  id         INT AUTO_INCREMENT
    PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  telnumber  INT         NOT NULL,
  FOREIGN KEY (id) REFERENCES credential (id)
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS student;
CREATE TABLE student
(
  id                       INT                    NOT NULL
    PRIMARY KEY,
  first_name               VARCHAR(50)            NOT NULL,
  last_name                VARCHAR(50)            NOT NULL,
  birth_date               DATE                   NOT NULL,
  birth_place              VARCHAR(50)            NOT NULL,
  birth_place_province     VARCHAR(2)             NOT NULL,
  residence_place          VARCHAR(50)            NOT NULL,
  residence_place_province VARCHAR(2)             NOT NULL,
  cf                       VARCHAR(30)            NOT NULL,
  telnumber                INT(20)                NOT NULL,
  university_level         VARCHAR(30)            NOT NULL,
  university_course        VARCHAR(50)            NOT NULL,
  handicap                 TINYINT(1) DEFAULT '0' NOT NULL,
  FOREIGN KEY (id) REFERENCES credential (id)
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS company;
CREATE TABLE company
(
  id                INT                    NOT NULL
    PRIMARY KEY,
  social_region     VARCHAR(50)            NOT NULL,
  legal_address     VARCHAR(50)            NOT NULL,
  piva              VARCHAR(50)            NOT NULL,
  lawyer_first_name VARCHAR(50)            NOT NULL,
  lawyer_last_name  VARCHAR(50)            NOT NULL,
  person_first_name VARCHAR(50)            NOT NULL,
  person_last_name  VARCHAR(50)            NOT NULL,
  person_telnumber  INT                    NOT NULL,
  legal_forum       VARCHAR(50)            NOT NULL,
  active            TINYINT(1) DEFAULT '0' NOT NULL,
  visible           TINYINT(1) DEFAULT '0' NOT NULL,
  FOREIGN KEY (id) REFERENCES credential (id)
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS internship;
CREATE TABLE internship
(
  id            INT AUTO_INCREMENT
    PRIMARY KEY,
  place         VARCHAR(50)            NOT NULL,
  remote        TINYINT(1) DEFAULT '0' NOT NULL,
  start_time    TIME                   NULL,
  end_time      TIME                   NULL,
  num_hours     INT                    NOT NULL,
  goals         VARCHAR(256)           NULL,
  work_type     VARCHAR(256)           NULL,
  refund        FLOAT DEFAULT '0'      NULL,
  facilitations VARCHAR(256)           NULL,
  company_fk    INT                    NOT NULL,
  start_date    DATE                   NOT NULL,
  end_date      DATE                   NOT NULL,
  FOREIGN KEY (company_fk) REFERENCES company (id)

)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS candidacy;
CREATE TABLE candidacy
(
  id                  INT AUTO_INCREMENT
    PRIMARY KEY,
  internship_fk       INT             NOT NULL,
  student_fk          INT             NOT NULL,
  status              INT DEFAULT '0' NULL
  COMMENT '0 = pending, 1 = rejected, 2 = accepted',
  n_cfu               INT             NOT NULL,
  first_name_referent VARCHAR(50)     NOT NULL,
  last_name_referent  VARCHAR(50)     NOT NULL,
  email_referent      VARCHAR(50)     NOT NULL,
  start_date          DATE            NULL,
  end_date            DATE            NULL,
  FOREIGN KEY (internship_fk) REFERENCES internship (id),
  FOREIGN KEY (student_fk) REFERENCES student (id)
)
  ENGINE = InnoDB;


INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (4, 'test@test.com', 0x878B4AAC25AC56ED86FEE69B805F454A, '1933748E1E6AED2DEEFE31D6C9685BAA49828BFF',
   '2018-06-14 18:00:07', '2018-06-14 18:12:57', '7fef5f032c8e4b4eb4eab448a6e21a56', '2018-06-14 18:42:57', 0);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (5, 'test1@test.com', 0x878B4AAC25AC56ED86FEE69B805F454A, '1933748E1E6AED2DEEFE31D6C9685BAA49828BFF',
   '2018-06-14 18:00:07', '2018-06-14 18:03:07', NULL, NULL, 1);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (6, 'test2@test.com', 0x878B4AAC25AC56ED86FEE69B805F454A, '1933748E1E6AED2DEEFE31D6C9685BAA49828BFF',
   '2018-06-14 18:00:07', '2018-06-14 18:00:07', NULL, NULL, 0);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (7, 'test4@test.com', 0x878B4AAC25AC56ED86FEE69B805F454A, '1933748E1E6AED2DEEFE31D6C9685BAA49828BFF',
   '2018-06-14 18:00:07', '2018-06-14 18:00:07', NULL, NULL, 1);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (8, 'test12@test.com', 0xC230ACFD65CDB7FBE377208AFFA9B09C, '723161FBA797DBE939DA52DB231D7269599945C6',
   '2018-06-14 18:02:37', '2018-06-14 18:02:37', NULL, NULL, 0);

INSERT INTO awd.company (id, social_region, legal_address, piva, lawyer_first_name, lawyer_last_name, person_first_name, person_last_name, person_telnumber, legal_forum, active, visible)
VALUES
  (5, 'Programmign4u', 'Avezzano', 'fjedfkjh857654thvfbu', 'Franco', 'Verdi', 'Luigi', 'Cerone', 345679954, 'Avezzano',
      1, 1);
INSERT INTO awd.company (id, social_region, legal_address, piva, lawyer_first_name, lawyer_last_name, person_first_name, person_last_name, person_telnumber, legal_forum, active, visible)
VALUES (7, 'BoringCompany', 'L''aquila', 'fhrihgfirgf366576546', 'Giuseppe', 'Rossi', 'Danilo', 'Rosati', 347567895,
           'L''Aquila', 1, 1);

INSERT INTO awd.student (id, first_name, last_name, birth_date, birth_place, birth_place_province, residence_place, residence_place_province, cf, telnumber, university_level, university_course, handicap)
VALUES (4, 'Andrea', 'Paris', '1996-05-17', 'Avezzano', 'AQ', 'Avezzano', 'AQ', 'AVOFGOFGIUFGRFORPFGB', 123456789,
           'Laurea Base', 'Informatica', 0);
INSERT INTO awd.student (id, first_name, last_name, birth_date, birth_place, birth_place_province, residence_place, residence_place_province, cf, telnumber, university_level, university_course, handicap)
VALUES
  (6, 'Alessandro', 'Taglieri', '1996-04-19', 'Avezzano', 'AQ', 'Avezzano', 'AQ', 'AVOFGOFGIUFGRFORPFGB', 123456789,
      'Laurea Base', 'Informatica', 0);


INSERT INTO awd.internship (id, place, remote, start_time, end_time, num_hours, goals, work_type, refund, facilitations, company_fk, start_date, end_date)
VALUES
  (1, 'L''Aquila', 0, '09:00:00', '18:00:00', 120, 'Imparare Djando, Implementare backend python', 'Programmazione',
      450, 'Mensa', 5, '2018-07-01', '2018-09-30');
INSERT INTO awd.internship (id, place, remote, start_time, end_time, num_hours, goals, work_type, refund, facilitations, company_fk, start_date, end_date)
VALUES (2, 'Avezzano', 0, '09:00:00', '18:00:00', 60,
           'Sviluppare mobile app ibrida con ReactJS, Implementare backend con NodeJS', 'Programmazione', 600, '', 5,
        '2018-07-01', '2018-07-31');
INSERT INTO awd.internship (id, place, remote, start_time, end_time, num_hours, goals, work_type, refund, facilitations, company_fk, start_date, end_date)
VALUES (3, 'L''Aquila', 0, '09:00:00', '18:00:00', 100, 'Imparare Spring, Implementare gestionale stipendi',
           'Programmazione', 450, 'Mensa', 5, '2018-06-01', '2018-07-12');

INSERT INTO awd.candidacy (id, internship_fk, student_fk, status, n_cfu, first_name_referent, last_name_referent, email_referent, start_date, end_date)
VALUES (1, 1, 4, 2, 5, 'Giuseppe ', 'Della Penna', 'giuseppe.dellapenna@univaq.it', '2018-07-01', '2018-08-31');
INSERT INTO awd.candidacy (id, internship_fk, student_fk, status, n_cfu, first_name_referent, last_name_referent, email_referent, start_date, end_date)
VALUES (2, 2, 6, 0, 6, 'Claudio ', 'Arbib', 'claudio.arbib@univaq.it', '2018-07-01', '2018-07-31');








