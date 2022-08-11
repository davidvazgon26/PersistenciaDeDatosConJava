package com.example.gatos.app;

import javax.swing.*;
import java.io.IOException;

public class Inicio {
    public static void main(String[] args) throws IOException {

        int opcionMenu = -1;
        String[] botones = {
                "1. ver gatos",
                "2. Ver favoritos",
                "3. Salir"
        };

        do {
            //menu principal
            String option = (String) JOptionPane.showInputDialog(null, "Gatitos Java", "Menu Principal",
                    JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

            //validar la opcion seleccionada por el usuario
            for (int i = 0; i < botones.length; i++) {
                if (option.equals(botones[i])) {
                    opcionMenu = i;
                }
            }

            switch (opcionMenu) {
                case 0:
                    GatosService.verGatos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    GatosService.verFavoritos(gato.getApikey());
                    break;
                default:
                    break;

            }
        } while (opcionMenu != 1);

    }
}
