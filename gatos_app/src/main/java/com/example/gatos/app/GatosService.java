package com.example.gatos.app;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GatosService {
    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();

//        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        //Cortar los [ ] del json
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);

        //crear un objeto Gson
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);

        //redimensionar la imagen por si acaso
        Image image = null;
        try {
//            URL url = new URL(gatos.getUrl());
//            image = ImageIO.read(url);
//
//            ImageIcon fondoGato = new ImageIcon(image);
            URL url = new URL(gatos.getUrl());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon fondoGato = new ImageIcon(bufferedImage);

            if (fondoGato.getIconWidth() > 800 || fondoGato.getIconHeight() > 400) {
                //redimensionar
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + " 1. Ver otra imagen \n"
                    + " 2. Favorito \n"
                    + " 3. Volver \n";

            String[] botones = {"ver otra imagen", "favorito", "volver"};
            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE,
                    fondoGato, botones, botones[0]);

            int seleccion = -1;

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }

            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;

            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void favoritoGato(Gatos gato) {

        try {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\"" + gato.getId() + "\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("x-api-key", "1f3e12f1-9ae6-4eff-aca8-1c4f82663b3e ")
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void verFavoritos(String apikey) throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET",null)
                .addHeader("x-api-key", "1f3e12f1-9ae6-4eff-aca8-1c4f82663b3e")
                .build();
        Response response = client.newCall(request).execute();


        // guardamos el string con la respuesta
        String elJson = response.body().string();


        //creamos el objeto gson
        Gson gson = new Gson();
        GatosFav[] gatosArray = gson.fromJson(elJson, GatosFav[].class);

        //System.out.println(gatosArray);

        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
            int indice = aleatorio - 1;

            GatosFav gatofav = gatosArray[indice];



            //redimensionar en caso de necesitar
            Image image;
            try {
                URL url = new URL(gatofav.image.getUrl());
//                image = ImageIO.read(url);
//                ImageIcon fondoGato = new ImageIcon(image);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.addRequestProperty("User-Agent", "");
                BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
                ImageIcon fondoGato = new ImageIcon(bufferedImage);


                if (fondoGato.getIconWidth() > 800) {
                    //redimensionamos
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n"
                        + " 1. ver otra imagen \n"
                        + " 2. Eliminar Favorito \n"
                        + " 3. Volver \n";

                String[] botones = {"ver otra imagen", "eliminar favorito", "volver"};
                String id_gato = gatofav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;
                //validamos que opcion selecciona el usuario
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;
                    }
                }

                switch (seleccion) {
                    case 0:
                        verFavoritos(apikey);
                        break;
                    case 1:
                        borrarFavorito(gatofav);
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
                System.err.println(e);
            }

        }

    }

    public static void borrarFavorito(GatosFav gatofav) {
        try {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites/"+gatofav.getId()+"")
                .method("DELETE", body)
                .addHeader("x-api-key", gatofav.getApikey())
                .build();

            Response response = client.newCall(request).execute();
            System.out.println("Gato "+ gatofav.getId()+" eliminado");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
