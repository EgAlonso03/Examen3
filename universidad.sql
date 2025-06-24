DROP DATABASE IF EXISTS universidad;
CREATE DATABASE universidad;
USE universidad;

CREATE TABLE inscripciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    estudiante INT,
    curso VARCHAR(20),
    calificacion DECIMAL(5,2)
);

INSERT INTO inscripciones (id, estudiante, curso, calificacion) VALUES
(1, 1001, 'Matematicas', 85.50),
(2, 1002, 'Español', 92.00),
(3, 1003, 'Historia', 76.25),
(4, 1001, 'Geografía', 88.75),
(5, 1004, 'Ingles', 90.00);