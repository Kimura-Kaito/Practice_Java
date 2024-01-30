DROP TABLE license;
CREATE TABLE license(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL,
level INTEGER NOT NULL,
higher_license_id INTEGER[]
);