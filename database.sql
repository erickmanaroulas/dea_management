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
	CONSTRAINT student_FK FOREIGN KEY (user_id) REFERENCES dea_user(id) ON DELETE CASCADE ON UPDATE CASCADE
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

CREATE TABLE academy_class (
    id int auto_increment NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    class_type ENUM("DEVELOPER", "DESIGN") NOT NULL,
    instructor_id int NOT NULL,
    CONSTRAINT academy_class_PK PRIMARY KEY (id),
    CONSTRAINT academy_class_FK FOREIGN KEY (instructor_id) REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE project (
    id int auto_increment NOT NULL,
    name varchar(256) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    client varchar(256) NOT NULL,
    product_owner int NOT NULL,
    scrum_master int NOT NULL,
    external_product_manager varchar(256) NOT NULL,
    CONSTRAINT project_PK PRIMARY KEY (id),
    CONSTRAINT project_FK FOREIGN KEY (product_owner) REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT project_FK_1 FOREIGN KEY (scrum_master) REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE student ADD academy_class_id INT NULL;
ALTER TABLE student ADD CONSTRAINT student_FK_1 FOREIGN KEY (academy_class_id) REFERENCES academy_class(id) ON DELETE SET NULL ON UPDATE SET NULL;