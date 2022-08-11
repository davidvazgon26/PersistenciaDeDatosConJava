package com.mensajes.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection getConnection(){
        Connection conection = null;
        try{
            conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mensajes_app","dbuser", "Qwerty78.");
//        Validando si se realizo la conexion con exito
//            if(conection != null){
//                System.out.println("Conexion exitosa");
//            }

        }catch(SQLException e){
            System.err.println(e);
        }
//        Devolvemos la conexion a la BD para nuestras operaciones
        return conection;

    }
}
