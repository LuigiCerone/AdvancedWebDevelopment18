CREATE TABLE awd.admin
(
  id         INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50)         NOT NULL,
  last_name  VARCHAR(50)         NOT NULL,
  telnumber  INT(11)             NOT NULL
);
CREATE TABLE awd.candidacy
(
  id                  INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  internship_fk       INT(11)             NOT NULL,
  student_fk          INT(11)             NOT NULL,
  status              INT(11)                      DEFAULT '0'
  COMMENT '0 = pending, 1 = rejected, 2 = accepted',
  n_cfu               INT(11)             NOT NULL,
  first_name_referent VARCHAR(50)         NOT NULL,
  last_name_referent  VARCHAR(50)         NOT NULL,
  email_referent      VARCHAR(50)         NOT NULL,
  start_date          DATE,
  end_date            DATE
);
INSERT INTO awd.candidacy (id, internship_fk, student_fk, status, n_cfu, first_name_referent, last_name_referent, email_referent, start_date, end_date)
VALUES (1, 1, 12, 2, 4, 'Giuseppe', 'Della penna', 'giuseppe.dellapenna@univaq.it', '2018-05-20', '2018-05-30');
CREATE TABLE awd.company
(
  id                INT(11) PRIMARY KEY    NOT NULL,
  social_region     VARCHAR(50)            NOT NULL,
  legal_address     VARCHAR(50)            NOT NULL,
  piva              VARCHAR(50)            NOT NULL,
  lawyer_first_name VARCHAR(50)            NOT NULL,
  lawyer_last_name  VARCHAR(50)            NOT NULL,
  person_first_name VARCHAR(50)            NOT NULL,
  person_last_name  VARCHAR(50)            NOT NULL,
  person_telnumber  INT(11)                NOT NULL,
  legal_forum       VARCHAR(50)            NOT NULL,
  active            TINYINT(1) DEFAULT '0' NOT NULL,
  visible           TINYINT(1) DEFAULT '0' NOT NULL
);
INSERT INTO awd.company (id, social_region, legal_address, piva, lawyer_first_name, lawyer_last_name, person_first_name, person_last_name, person_telnumber, legal_forum, active, visible)
VALUES (13, 'BBBB', 'L''aquila', '2u3t4367428riuufgy487', 'Nome2', 'Cognome2', 'PersonNome', 'PersonCognome', 7488458,
            'Avezzano', 1, 1);
CREATE TABLE awd.credential
(
  id         INT(11) PRIMARY KEY                     NOT NULL AUTO_INCREMENT,
  email      VARCHAR(50)                             NOT NULL,
  salt       VARBINARY(128)                          NOT NULL,
  hashed_psw VARCHAR(256)                            NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL,
  last_seen  TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  token      VARCHAR(50),
  expiry     TIMESTAMP,
  user_type  INT(11)                                 NOT NULL
);
CREATE UNIQUE INDEX `credential;_email_uindex`
  ON awd.credential (email);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (12, 'test@test.com', 0xAD537BAAE1E7C127E112261F4A96992E, 'F77F0FD7B82493E1014F351CB2CD378595E45A99',
   '2018-05-12 18:33:38', '2018-05-12 18:33:38', 'ffe2b4434e8e47429cecd57bcca92778', '2018-05-16 15:47:14', 0);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (13, 'test1@test.com', 0xAD537BAAE1E7C127E112261F4A96992E, 'F77F0FD7B82493E1014F351CB2CD378595E45A99',
   '2018-05-12 18:33:38', '2018-05-12 18:33:38', '055d928ae49d4166ba1a0f54291b8474', '2018-05-17 11:42:56', 1);
INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type) VALUES
  (15, 'test3@test.com', 0x83737F0292D20A7F6A0ED2BB2BBF9CDD, '4E8E16EB6CAB3211987902603C5F6FB2F6C28956',
   '2018-05-17 11:43:08', '2018-05-17 11:43:08', NULL, NULL, 0);
CREATE TABLE awd.internship
(
  id            INT(11) PRIMARY KEY    NOT NULL AUTO_INCREMENT,
  place         VARCHAR(50)            NOT NULL,
  remote        TINYINT(1) DEFAULT '0' NOT NULL,
  start_time    TIME,
  end_time      TIME,
  num_hours     INT(11)                NOT NULL,
  goals         VARCHAR(256),
  work_type     VARCHAR(256),
  refund        FLOAT                           DEFAULT '0',
  facilitations VARCHAR(256),
  company_fk    INT(11)                NOT NULL,
  start_date    DATE                   NOT NULL,
  end_date      DATE                   NOT NULL
);
INSERT INTO awd.internship (id, place, remote, start_time, end_time, num_hours, goals, work_type, refund, facilitations, company_fk, start_date, end_date)
VALUES
  (1, 'Avezzano', 0, '11:00:00', '18:00:00', 10, 'Obiettivo1, obiettivo2', 'Programmazione', 14, '', 13, '2018-05-24',
   '2018-05-31');
CREATE TABLE awd.student
(
  id                       INT(11) PRIMARY KEY    NOT NULL,
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
  handicap                 TINYINT(1) DEFAULT '0' NOT NULL
);
INSERT INTO awd.student (id, first_name, last_name, birth_date, birth_place, birth_place_province, residence_place, residence_place_province, cf, telnumber, university_level, university_course, handicap)
VALUES (12, 'Nome', 'Cognome', '1978-08-17', 'L''Aquila', 'AQ', 'L''Aquila', 'AQ', 'AVOFGOFGIUFGRFORPFGB', 123456789,
            'Laurea Base', 'Informatica', 0);
INSERT INTO awd.student (id, first_name, last_name, birth_date, birth_place, birth_place_province, residence_place, residence_place_province, cf, telnumber, university_level, university_course, handicap)
VALUES (14, 'Nome', 'Cognome', '1978-08-17', 'L''Aquila', 'AQ', 'L''Aquila', 'AQ', 'AVOFGOFGIUFGRFORPFGB', 123456789,
            'Laurea Base', 'Informatica', 0);
INSERT INTO awd.student (id, first_name, last_name, birth_date, birth_place, birth_place_province, residence_place, residence_place_province, cf, telnumber, university_level, university_course, handicap)
VALUES (15, 'Nome', 'Cognome', '1978-08-17', 'L''Aquila', 'AQ', 'L''Aquila', 'AQ', 'AVOFGOFGIUFGRFORPFGB', 123456789,
            'Laurea Base', 'Informatica', 0);