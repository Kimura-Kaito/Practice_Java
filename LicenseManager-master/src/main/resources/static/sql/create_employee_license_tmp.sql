DROP TABLE el_tmp;
CREATE TABLE el_tmp(
id SERIAL PRIMARY KEY,
employee_id INTEGER NOT NULL,
license_id INTEGER NOT NULL,
get_date DATE,
notification_date DATE,
start_date TEXT,
remarks TEXT,
UNIQUE(employee_id, license_id)
);