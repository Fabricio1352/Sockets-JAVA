package com.mycompany.recuperacion2;




import java.io.*;
import java.net.*;
/**
 *
 * @author Fabricio
 */
public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor en ejecución...");

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                new Thread(new ClienteHandler(cliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket cliente;

    public ClienteHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream())) {

            System.out.println("Atendiendo al cliente: " + cliente.getInetAddress().getHostAddress());

            //String tipoAlmacenamiento = "bd"; // Cambiar a "bd"  "texto"
            GestorEstrategia g = new GestorEstrategia("texto");
            //g.setEstrategiaDAO(AlmacenFactory.getAlmacenamiento("texto"));
            //EstrategiaDAO almacenamiento = AlmacenFactory.getAlmacenamiento(tipoAlmacenamiento);

            while (true) {
                try {
                    String accion = (String) in.readObject();

                    switch (accion) {
                        case "guardar":
                            Persona persona = (Persona) in.readObject();
                            //almacenamiento.guardar(persona);
                            g.guardar(persona);
                            out.writeObject("Datos guardados exitosamente");
                            break;

                        case "consultarPorID":
                            int idConsulta = (Integer) in.readObject();
                            Persona personaConsultada = g.consultarPorID(idConsulta);//almacenamiento.consultarPorID(idConsulta);
                            if (personaConsultada != null) {
                                out.writeObject(personaConsultada);
                            } else {
                                out.writeObject("No se encontro ninguna persona con ese ID.");
                            }
                            break;

                        case "consultarTodos":
                            out.writeObject(g.consultarTodos());//almacenamiento.consultarTodos());
                            break;

                        default:
                            out.writeObject("Accion no reconocida.");
                    }
                } catch (EOFException e) {
                    System.out.println("Cliente desconectado: " + cliente.getInetAddress().getHostAddress());
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al manejar al cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
