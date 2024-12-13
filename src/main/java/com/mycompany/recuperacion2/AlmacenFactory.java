/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.recuperacion2;

/**
 *
 * @author Fabricio
 */
public class AlmacenFactory {
    public static EstrategiaDAO getAlmacenamiento(String tipo) {
        switch (tipo.toLowerCase()) {
            case "texto":
                return DAOTexto.getInstance();
            case "bd":
                return DAODatabase.getInstance();
            default:
                throw new IllegalArgumentException("Tipo de almacenamiento no soportado");
        }
    }
}