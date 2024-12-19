package ausazkoFitxategiak;

import java.io.File;
import java.time.LocalDate;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class FitxategiKudeatzailea {

    /**
     * Fitxategi bat sortzeko metodoa
     * @param scanner Erabiltzailearen sarrerak jasotzeko Scanner objektua
     * @return Sortutako edo erabilitako fitxategia; errore kasuan, null itzultzen du
     *
     * Metodo honek erabiltzaileari fitxategiaren helbidea eta izena sartzeko eskatzen dio.
     * Helbidea ematen ez bada, helbide lehenetsi bat erabiltzen du.
     * Fitxategia existitzen ez bada, direktorioa eta fitxategia sortzen saiatzen da.
     * Fitxategia existitzen bada, erabiltzaileari fitxategi hori erabiltzea nahi duen galdetzen dio.
     */
    public static File fitxategiaSortu(Scanner scanner) {
        String fitxategiIzena;
        String fitxategiHelbidea;
        File fitxategia = null;

        // Fitxategiaren helbidea eskatzen da erabiltzaileari
        System.out.println("Sartu fitxategiaren helbidea (HUTSIK = C:\\Users\\Administrador\\Desktop\\FROGAK): ");
        fitxategiHelbidea = scanner.nextLine();
        if (fitxategiHelbidea.equals("")) {
            fitxategiHelbidea = "C:\\Users\\Administrador\\Desktop\\FROGAK";
        }

        // Fitxategiaren direktorioa existitzen ez bada, sortzen da
        File direktorioa = new File(fitxategiHelbidea);
        if (!direktorioa.exists()) {
            if (direktorioa.mkdirs()) {
                System.out.println("Direktorioa sortuta: " + direktorioa.getAbsolutePath());
            } else {
                System.out.println("Errorea direktorioa sortzerakoan.");
                return null; // Ezin bada sortu, amaitzen da
            }
        }

        // Fitxategiaren izena eskatzen da
        System.out.println("Sartu fitxategiaren izena:");
        fitxategiIzena = scanner.nextLine();
        fitxategiIzena = fitxategiIzena + ".txt";

        // Fitxategia sortzen eta aldagaian gordetzen da
        fitxategia = new File(fitxategiHelbidea, fitxategiIzena);

        // Fitxategia existitzen den ala ez egiaztatzen da
        if (fitxategia.exists()) {
            System.out.println("Fitxategia existitzen da: " + fitxategia.getAbsolutePath());
            System.out.println("Erabili fitxategi hau? (bai/ez):");
            String erantzuna = scanner.nextLine();

            if (erantzuna.equalsIgnoreCase("bai")) {
                System.out.println("Fitxategia erabiliko da: " + fitxategia.getAbsolutePath());
                return fitxategia; // Fitxategi existentea erabiltzen bada, amaitzen da
            } else {
                System.out.println("Beste fitxategi bat sortuko da.");
            }
        }

        // Fitxategia existitzen ez bada sortzen da
        try {
            if (!fitxategia.exists()) {
                System.out.println("Ez da existitzen. Fitxategia sortzen saiatzen...");
                if (fitxategia.createNewFile()) {
                    System.out.println("Fitxategia sortu da: " + fitxategia.getAbsolutePath());
                } else {
                    System.out.println("Ezin izan da fitxategia sortu.");
                }
            }
        } catch (IOException e) {
            System.err.println("Errorea fitxategia sortzean: " + e.getMessage());
            e.printStackTrace(); // Errorea ikusteko
        }
        return fitxategia;
    }
    
}
