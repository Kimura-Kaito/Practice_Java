CREATE TABLE todo
(
    id          SERIAL PRIMARY KEY,
    title       TEXT,
    importance  INTEGER,
    urgency     INTEGER,
    deadline    DATE,
    done        TEXT
);

INSERT INTO todo(title, importance, urgency, deadline, done)
VALUES('todo-1', 0, 0, '2024-01-10', 'N');
INSERT INTO todo(title, importance, urgency, deadline, done)
VALUES('todo-2', 0, 1, '2024-01-11', 'Y');
INSERT INTO todo(title, importance, urgency, deadline, done)
VALUES('todo-3', 1, 0, '2024-01-12', 'N');
INSERT INTO todo(title, importance, urgency, deadline, done)
VALUES('todo-4', 1, 1, '2024-01-13', 'Y');