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
public class GestorEstrategia {

    private EstrategiaDAO estrategiaDAO;

    public GestorEstrategia(String tipo) {
        this.estrategiaDAO = AlmacenFactory.getAlmacenamiento(tipo);
    }

    public void setEstrategiaDAO(EstrategiaDAO estrategiaDAO) {
        this.estrategiaDAO = estrategiaDAO;
    }

    public void guardar(Persona persona) {
        estrategiaDAO.guardar(persona);
    }

    public List<Persona> consultarTodos() {
        return estrategiaDAO.consultarTodos();
    }

    public Persona consultarPorID(int id) {
        return estrategiaDAO.consultarPorID(id);

    }
}
