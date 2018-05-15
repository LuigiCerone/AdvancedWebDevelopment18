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
  user_fk    INT                                     NULL,
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
  telnumber  INT         NOT NULL
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS student;
CREATE TABLE student
(
  id                       INT AUTO_INCREMENT
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
  handicap                 TINYINT(1) DEFAULT '0' NOT NULL
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS company;
CREATE TABLE company
(
  id                INT AUTO_INCREMENT
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
  visible           TINYINT(1) DEFAULT '0' NOT NULL
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
  end_date      DATE                   NOT NULL
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
  end_date            DATE            NULL
)
  ENGINE = InnoDB;


INSERT INTO awd.credential (id, email, salt, hashed_psw, created_at, last_seen, token, expiry, user_type, user_fk)
VALUES (3, 'luigi@test.com', 0x5A6B70271B17AA7294433190586554BE, '319975C41A84B847137021A2388DF24444F52BB7',
        '2018-05-11 19:35:29', '2018-05-11 17:31:59', 'd76e6a788ee94012a024457ede936491', '2018-05-11 20:05:29', 0, 1);