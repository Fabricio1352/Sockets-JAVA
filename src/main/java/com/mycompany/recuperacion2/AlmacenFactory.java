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
    public static AlmacenamientoDAO getAlmacenamiento(String tipo) {
        switch (tipo.toLowerCase()) {
            case "texto":
                return new AlmacenamientoTexto();
            case "bd":
                return new AlmacenamientoDB();
            default:
                throw new IllegalArgumentException("Tipo de almacenamiento no soportado");
        }
    }
}