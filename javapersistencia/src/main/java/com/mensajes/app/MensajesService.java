package com.mensajes.app;

import java.util.Scanner;

public class MensajesService {

    public static void crearMensaje() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe tu mensaje: ");
        String mensaje = sc.nextLine();

        System.out.println("Autor del mensaje: ");
        String autor = sc.nextLine();

        //Creamos el objeto (registro) y llenamos valores
        Mensajes registro = new Mensajes();
        registro.setMensaje(mensaje);
        registro.setAutor_mensaje(autor);

        //ya se creo el objeto ahora lo mandamos a la capa DAO
        MensajesDAO.CrearMensajeDB(registro);
    }

    public static void listarMensajes() {
        MensajesDAO.leerMensajesDB();
    }

    public static void borrarMensaje() {
        Scanner sc  = new Scanner(System.in);
        System.out.println("Escribe el id del mensaje a borrar: ");
        int id = sc.nextInt();
        MensajesDAO.borrarMensajeDB(id);

    }

    public static void editarMensaje() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Nuevo mensaje:");
        String mensaje = sc.nextLine();

        System.out.println("Id de mensaje a editar:");
        int id = sc.nextInt();


        Mensajes upDateMensaje = new Mensajes();
        upDateMensaje.setId_mensaje(id);
        upDateMensaje.setMensaje(mensaje);

        MensajesDAO.actualizarMensajeDB(upDateMensaje);



    }
}
