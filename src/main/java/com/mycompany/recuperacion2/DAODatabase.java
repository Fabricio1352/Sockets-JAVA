/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.recuperacion2;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Fabricio
 */
public class DAODatabase implements EstrategiaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/recuperacion";
    private static final String USER = "root";
    private static final String PASSWORD = "agiles123!!";

    private static DAODatabase instance;

    private DAODatabase() {
    }

    public static DAODatabase getInstance() {
        if (instance == null) {
            instance = new DAODatabase();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void guardar(Persona persona) {
        String query = "INSERT INTO personas (id, nombre, edad) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nombre = ?, edad = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, persona.getId());
            stmt.setString(2, persona.getNombre());
            stmt.setInt(3, persona.getEdad());
            stmt.setString(4, persona.getNombre());
            stmt.setInt(5, persona.getEdad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Persona> consultarTodos() {
        List<Persona> personas = new ArrayList<>();
        String query = "SELECT * FROM personas";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Persona persona = new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                );
                personas.add(persona);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la base de datos: " + e.getMessage(), e);
        }
        return personas;
    }

    @Override
    public Persona consultarPorID(int id) {
        String query = "SELECT * FROM personas WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("edad")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar por ID en la base de datos: " + e.getMessage(), e);
        }
        return null;
    }

}
