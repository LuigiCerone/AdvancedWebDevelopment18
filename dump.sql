DROP DATABASE IF EXISTS awd;
CREATE DATABASE awd;

DROP TABLE IF EXISTS credential;
CREATE TABLE `credential;`
(
  id           INT AUTO_INCREMENT
    PRIMARY KEY,
  email        VARCHAR(50)                             NOT NULL,
  salt         VARCHAR(20)                             NOT NULL,
  hashed_psw   VARCHAR(128)                            NOT NULL,
  created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL,
  last_seen    TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  token        VARCHAR(50)                             NULL,
  time_to_live INT                                     NULL,
  user_type    INT                                     NOT NULL,
  user_fk      INT                                     NULL,
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
  socialRegion      VARCHAR(50)            NOT NULL,
  legalAddress      VARCHAR(50)            NOT NULL,
  piva              VARCHAR(50)            NOT NULL,
  lawyer_first_name VARCHAR(50)            NOT NULL,
  lawyer_last_name  VARCHAR(50)            NOT NULL,
  person_first_name VARCHAR(50)            NOT NULL,
  person_last_name  VARCHAR(50)            NOT NULL,
  person_telnumber  INT                    NOT NULL,
  legalForum        VARCHAR(50)            NOT NULL,
  active            TINYINT(1) DEFAULT '0' NOT NULL,
  visible           TINYINT(1) DEFAULT '0' NOT NULL
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS internship_offer;
CREATE TABLE internship
(
  id            INT AUTO_INCREMENT
    PRIMARY KEY,
  place         VARCHAR(50)                             NOT NULL,
  remote        TINYINT(1) DEFAULT '0'                  NOT NULL,
  start_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  end_time      TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
  num_hours     INT                                     NOT NULL,
  goals         VARCHAR(256)                            NULL,
  work_type     VARCHAR(256)                            NULL,
  refund        FLOAT DEFAULT '0'                       NULL,
  facilitations VARCHAR(256)                            NULL
)
  ENGINE = InnoDB;


DROP TABLE IF EXISTS internship_candidacy;
