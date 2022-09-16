TRUNCATE TABLE ers_reimbursements;
TRUNCATE TABLE ers_users;
TRUNCATE TABLE ers_user_roles;
TRUNCATE TABLE ers_reimbursement_types;
TRUNCATE TABLE ers_reimbursement_statuses;


DROP TABLE ers_reimbursements;
DROP TABLE ers_users;
DROP TABLE ers_user_roles;
DROP TABLE ers_reimbursement_types;
DROP TABLE ers_reimbursement_statuses;



CREATE TABLE ers_reimbursement_statuses (
	status_id varchar PRIMARY KEY,
	status varchar UNIQUE
);

CREATE TABLE ers_reimbursement_types(
	type_id varchar PRIMARY KEY,
	"type" varchar UNIQUE
);

CREATE TABLE ers_user_roles(
	role_id varchar PRIMARY KEY,
	"role" varchar UNIQUE
);

CREATE TABLE ers_users(
	user_id serial PRIMARY KEY,
	username varchar UNIQUE NOT NULL,
	email varchar UNIQUE NOT NULL,
	"password" varchar NOT NULL,
	given_name varchar NOT NULL,
	surname varchar NOT NULL,
	is_active boolean DEFAULT FALSE,
	role_id varchar NOT NULL,
	CONSTRAINT fk_ers_user_roles
		FOREIGN KEY (role_id)
		REFERENCES ers_user_roles(role_id)
);

CREATE TABLE ers_reimbursements(
	reimb_id serial PRIMARY KEY,
	amount numeric(6,2) NOT NULL,
	submitted timestamp NOT NULL,
	resolved timestamp,
	description varchar NOT NULL ,
	author_id integer NOT NULL ,
	resolver_id integer,
	status_id varchar NOT NULL ,
	type_id varchar NOT NULL ,
	CONSTRAINT fk_author_user_id
		FOREIGN KEY (author_id)
		REFERENCES ers_users(user_id),
	CONSTRAINT fk_resolver_user_id
		FOREIGN KEY (resolver_id)
		REFERENCES ers_users(user_id),
	CONSTRAINT fk_reimbursement_status_id
		FOREIGN KEY (status_id)
		REFERENCES ers_reimbursement_statuses(status_id),
	CONSTRAINT fk_reimbursement_type_id
		FOREIGN KEY (type_id)
		REFERENCES ers_reimbursement_types(type_id)
);
