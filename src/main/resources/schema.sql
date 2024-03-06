DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS pet_store;


CREATE TABLE pet_store (
	store_id int NOT NULL AUTO_INCREMENT,
	store_name varchar(256) NOT NULL,
	store_address varchar(256) NOT NULL,
	store_city varchar(256) NOT NULL,
	store_state varchar(256) NOT NULL,
	store_zip int NOT NULL,
	store_phone int NOT NULL,
	employee_id int NULL,
	customer_id int NULL,
	PRIMARY KEY (store_id),
	FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE,
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
	);
	
CREATE TABLE employee (
	employee_id int NOT NULL AUTO_INCREMENT,
	employee_first_name varchar(256) NOT NULL,
	employee_last_name varchar(256) NOT NULL,
	employee_phone int NOT NULL,
	employee_email varchar(256) NOT NULL,
	store_id int NULL,
	PRIMARY KEY (employee_id),
	FOREIGN KEY (store_id) REFERENCES pet_store (store_id) ON DELETE CASCADE
);

CREATE TABLE customer (
	customer_id int NOT NULL AUTO_INCREMENT,
	customer_first_name varchar(256) NOT NULL,
	customer_last_name varchar(256) NOT NULL,
	customer_email varchar(256) NOT NULL,
	store_id int NULL,
	PRIMARY KEY (customer_id),
	FOREIGN KEY (store_id) REFERENCES pet_store (store_id) ON DELETE CASCADE
);