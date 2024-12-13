/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.recuperacion2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabricio
 */
public class DAOTexto implements EstrategiaDAO {

    private static final String FILE_PATH = "personas.txt";

    private static DAOTexto instance;

    private DAOTexto() {
    }

    public static DAOTexto getInstance() {
        if (instance == null) {
            instance = new DAOTexto();
        }
        return instance;
    }

    @Override
    public void guardar(Persona persona) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(persona.getId() + "," + persona.getNombre() + "," + persona.getEdad());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Persona> consultarTodos() {
        List<Persona> personas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                Persona persona = new Persona(
                        Integer.parseInt(datos[0]),
                        datos[1],
                        Integer.parseInt(datos[2])
                );
                personas.add(persona);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }
        return personas;
    }

    @Override
    public Persona consultarPorID(int id) {
        return consultarTodos().stream()
                .filter(persona -> persona.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
