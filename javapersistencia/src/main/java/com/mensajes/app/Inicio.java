package com.mensajes.app;

import java.sql.Connection;
import java.util.Scanner;

public class Inicio {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int opcion = 0;

        do{
            System.out.println("______________________");
            System.out.println("Aplicacion de mensajes");
            System.out.println("1. Crear mensaje");
            System.out.println("2. listar mensajes");
            System.out.println("3. Editar mensaje");
            System.out.println("4. Eliminar mensaje");
            System.out.println("5. Salir");

            opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    MensajesService.crearMensaje();
                    break;
                case 2:
                    MensajesService.listarMensajes();
                    break;
                case 3:
                    MensajesService.editarMensaje();
                    break;
                case 4:
                    MensajesService.borrarMensaje();
                    break;
                default:
                    break;
            }

        }while(opcion !=5);

//        Conexion conexion = new Conexion();
//
//        try (Connection cnx = conexion.getConnection()) {
//
//        } catch (Exception e) {
//            System.err.println(e);
//        }


    }
}


//Aqui esta nuestro menu el cual muestra las opciones, al seleccionar una va a la capa de servicios
// (MensajesService), que a su vez se conecta con la capa DAO que es la que se conecta y ejecuta las instrucciones SQL