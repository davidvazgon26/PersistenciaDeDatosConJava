package com.mensajes.app;

//Aqui hacemos las conexiones a la BD una por metodo para practicar, pero se puede usar
// el metodo singleton para tener una sola conexion y mejorar nuestro rendimiento

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MensajesDAO {

    public static void CrearMensajeDB(Mensajes mensaje) {
//        separamos la logica de "Conexion" en el archivo con el mismo nombre
        Conexion db_connect = new Conexion();

//        try catch para validar conexion a BD
        try (Connection conexion = db_connect.getConnection()) {
            PreparedStatement ps = null;
            try {
                String query = "INSERT INTO mensajes(mensaje, autor_mensaje) values (?,?)";
                ps = conexion.prepareStatement(query);
                ps.setString(1, mensaje.getMensaje());
                ps.setString(2, mensaje.getAutor_mensaje());
                ps.executeUpdate();
                System.out.println("Mensaje creado correctamente");
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void leerMensajesDB() {
        Conexion db_connect = new Conexion();

//        PreparedStatement y ResultSet son metodos para preparar la sentencia SQL y devolver el resultado
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection conexion = db_connect.getConnection()) {
            String query = "SELECT * FROM mensajes";
            ps = conexion.prepareStatement(query);
            rs = ps.executeQuery(); //Ejecutamos la consulta y guardamos el resultado en rs

            //Mientras rs tenga datos, obtenlos...
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_mensaje") + ", Mensaje: " + rs.getString("mensaje")
                        + ", Autor: " + rs.getString("autor_mensaje") + ", Fecha de publicacion: " + rs.getString("fecha_mensaje"));
            }

        } catch (SQLException e) {
            System.err.println("No se encontraron datos");
            System.err.println(e);
        }

    }

    public static void borrarMensajeDB(int id_mensaje) {

        Conexion db_connect = new Conexion();

        try (Connection conexion = db_connect.getConnection()) {
            PreparedStatement ps = null;
            try {
                String query = "DELETE FROM mensajes  WHERE id_mensaje =?";
                ps = conexion.prepareStatement(query);
                ps.setInt(1, id_mensaje);
                ps.executeUpdate();
                System.out.println("Mensaje borrado");
            } catch (SQLException e) {
                System.err.println(e);
                System.err.println("No se pudo borrar el mensaje");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public static void actualizarMensajeDB(Mensajes mensaje) {

        //        separamos la logica de "Conexion" en el archivo con el mismo nombre
        Conexion db_connect = new Conexion();

//        try catch para validar conexion a BD
        try (Connection conexion = db_connect.getConnection()) {
            PreparedStatement ps = null;
            try {
                String query = "UPDATE mensajes SET mensaje=? WHERE id_mensaje = ?";
                ps = conexion.prepareStatement(query);
                ps.setString(1, mensaje.getMensaje());
                ps.setInt(2, mensaje.getId_mensaje());
                ps.executeUpdate();
                System.out.println("Mensaje actualizado correctamente");
            } catch (SQLException ex) {
                System.err.println(ex);
                System.err.println("Mensaje no actualizado");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

    }
}
