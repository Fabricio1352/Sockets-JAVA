/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.recuperacion2;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Fabricio
 */
public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Thread clientThread = new Thread(() -> {
            try (Socket socket = new Socket("localhost", 12345);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Conexión establecida con el servidor.");

                boolean continuar = true;
                while (continuar) {
                    System.out.println("\nMenú:");
                    System.out.println("1. Guardar una persona");
                    System.out.println("2. Consultar persona por ID");
                    System.out.println("3. Consultar todas las personas");
                    System.out.println("4. Salir");
                    System.out.print("Elige una opción: ");
                    int opcion = Integer.parseInt(scanner.nextLine());

                    switch (opcion) {
                        case 1:
                            System.out.print("ID: ");
                            int idGuardar = Integer.parseInt(scanner.nextLine());
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Edad: ");
                            int edad = Integer.parseInt(scanner.nextLine());

                            Persona persona = new Persona(idGuardar, nombre, edad);

                            out.writeObject("guardar");
                            out.writeObject(persona);
                            out.writeObject("texto");

                            String respuestaGuardar = (String) in.readObject();
                            System.out.println("Respuesta del servidor: " + respuestaGuardar);
                            break;

                        case 2:
                            System.out.print("Ingrese el ID de la persona: ");
                            int idConsulta = Integer.parseInt(scanner.nextLine());

                            out.writeObject("consultarPorID");
                            out.writeObject(idConsulta);

                            Object respuestaConsulta = in.readObject();
                            if (respuestaConsulta instanceof Persona) {
                                System.out.println("Persona encontrada: " + respuestaConsulta);
                            } else {
                                System.out.println("No se encontró ninguna persona con el ID proporcionado.");
                            }
                            break;

                        case 3:
                            out.writeObject("consultarTodos");

                            Object respuestaTodos = in.readObject();
                            if (respuestaTodos instanceof List) {
                                List<Persona> personas = (List<Persona>) respuestaTodos;
                                System.out.println("Listado de personas:");
                                for (Persona p : personas) {
                                    System.out.println(p);
                                }
                            } else {
                                System.out.println("No se encontraron registros.");
                            }
                            break;

                        case 4:
                            System.out.println("Finalizando cliente...");
                            continuar = false;
                            break;

                        default:
                            System.out.println("Opción no válida. Intenta de nuevo.");
                    }
                }

            } catch (Exception e) {
                System.err.println("Error en la conexión con el servidor: " + e.getMessage());
                e.printStackTrace();
            }
        });

        clientThread.start(); // Iniciar el hilo
    }
}
