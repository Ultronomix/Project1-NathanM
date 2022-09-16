INSERT INTO ERS_REIMBURSEMENT_TYPES (type_id, "type")
VALUES 
	(001, 'lodging'),
	(002, 'travel'),
	(003, 'food'),
	(004, 'other');
	
INSERT INTO ERS_REIMBURSEMENT_STATUSES (status_id, status)
VALUES 
	(001, 'pending'),
	(002, 'denied'),
	(003, 'approved');
	
INSERT INTO ERS_USER_ROLES (role_id, "role")
VALUES 
	(001, 'admin'),
	(002, 'finance manager'),
	(003, 'employee');
	
INSERT INTO ERS_USERS (username, email, password, given_name, surname, role_id)
VALUES 
	('defaultAdmin', 'admin@fakemail.com', 'passw0rd', 'John', 'Doe', 001),
	('defaultFinanceManager', 'financemanager@fakemail.com', 'passw0rd', 'Jane', 'Doe', 002),
	('defaultEmployee', 'employee@fakemail.com', 'passw0rd', 'Bobby', 'Doe', 003);
	
UPDATE ERS_USERS 
SET IS_ACTIVE = TRUE 
WHERE USERNAME = 'defaultAdmin' OR USERNAME = 'defaultFinanceManager' OR USERNAME = 'defaultEmployee';

INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID)
VALUES 
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: pending lodging reimbursement', 3, 1, 1),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: denied lodging reimbursement', 3, 2, 1),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: approved lodging reimbursement', 3, 3, 1),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: pending travel reimbursement', 3, 1, 2),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: denied travel reimbursement', 3, 2, 2),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: approved travel reimbursement', 3, 3, 2),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: pending food reimbursement', 3, 1, 3),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: denied food reimbursement', 3, 2, 3),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: approved food reimbursement', 3, 3, 3),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: pending other reimbursement', 3, 1, 4),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: denied other reimbursement', 3, 2, 4),
	(100.00, '2020-01-01 00:00:01', 'DUMMYDATA: approved other reimbursement', 3, 3, 4);

INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID)
VALUES 
	(200.00, TO_TIMESTAMP('2020-01-01 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), 'DUMMYDATA: testing TimeStamp', 3, 1, 1);
	
INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID)
VALUES 
	(200.00, '2020-01-01 00:00:01', 'DUMMYDATA: to be approved', 3, 1, 1);
	