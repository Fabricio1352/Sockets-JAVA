-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS recuperacion;

-- Usar la base de datos
USE recuperacion;

-- Crear la tabla personas
CREATE TABLE IF NOT EXISTS personas (
    id INT PRIMARY KEY,           -- Identificador único para cada persona
    nombre VARCHAR(100) NOT NULL, -- Nombre de la persona
    edad INT NOT NULL             -- Edad de la persona
);
