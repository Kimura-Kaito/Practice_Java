DROP TABLE employee_license;
CREATE TABLE employee_license(
id SERIAL PRIMARY KEY,
employee_id INTEGER NOT NULL,
license_id INTEGER NOT NULL,
get_date DATE,
notification_date DATE,
start_date TEXT,
remarks TEXT,
UNIQUE(employee_id, license_id)
);