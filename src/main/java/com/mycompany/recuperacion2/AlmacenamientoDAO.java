/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.recuperacion2;

import java.util.List;

/**
 *
 * @author Fabricio
 */
public interface AlmacenamientoDAO {
    void guardar(Persona persona);
    List<Persona> consultarTodos();
    Persona consultarPorID(int id);
}
