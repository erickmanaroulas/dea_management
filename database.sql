CREATE TABLE dea_user (
	id INTEGER auto_increment NOT NULL,
	name varchar(256) NOT NULL,
	email varchar(256) NOT NULL,
	password varchar(100) NOT NULL,
	linkedin varchar(128) NULL,
	CONSTRAINT user_PK PRIMARY KEY (id)
);

CREATE TABLE student (
	id INTEGER auto_increment NOT NULL,
	university varchar(128) NULL,
	graduation varchar(128) NULL,
	enrollment_date DATE NULL,
	finish_date DATE NULL,
	user_id INTEGER NOT NULL,
	CONSTRAINT student_PK PRIMARY KEY (id),
	CONSTRAINT student_FK FOREIGN KEY (user_id) REFERENCES dea_management.`user`(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `position` (
	id INT auto_increment NOT NULL,
	description varchar(128) NOT NULL,
	seniority varchar(128) NOT NULL,
	CONSTRAINT position_PK PRIMARY KEY (id)
);

CREATE TABLE employee (
	id INT auto_increment NOT NULL,
	position_id INT NOT NULL,
	employee_type ENUM("INSTRUCTOR","DEVELOPER","RESIDENT","DESIGNER","QA_ANALYST","ADMINISTRATIVE","PL","DTL") NOT NULL,
	user_id INT NOT NULL,
	CONSTRAINT employee_PK PRIMARY KEY (id),
	CONSTRAINT employee_FK FOREIGN KEY (user_id) REFERENCES dea_user(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT employee_FK_1 FOREIGN KEY (position_id) REFERENCES `position`(id)
);