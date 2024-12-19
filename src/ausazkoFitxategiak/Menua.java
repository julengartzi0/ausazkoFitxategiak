package ausazkoFitxategiak;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Menua {

    private static Scanner scanner = new Scanner(System.in);
    private static File fitxategia;

    public static void main(String[] args) throws IOException {
        // Fitxategia sortu edo ireki
        fitxategia = FitxategiKudeatzailea.fitxategiaSortu(scanner);

        // Fitxategia sortu bada, menua hasiko da; bestela, errorea emango du
        if (fitxategia != null) {
            menuaHasi();
        } else {
            System.out.println("Errorea fitxategia sortzean. Ezin da jarraitu.");
        }
    }

    // Menu nagusia erakusten duen metodoa
    private static void menuaHasi() throws IOException {

        while (true) {
            // Menu aukerak bistaratzen ditu
            System.out.println("\nMenua:");
            System.out.println("1. Datuak fitxategian gorde");
            System.out.println("2. Fitxategia hustu");
            System.out.println("3. Erregistroa bilatu");
            System.out.println("4. Generoko pelikulak bilatu ('Suspense', 'Drama', 'Romantica', 'Comedia', 'Aventura', 'Fantasia')");
            System.out.println("5. Erregistroa ezabatu");
            System.out.println("6. Ezabatutako erregistroak ikusi");
            System.out.println("7. Erregistroa gehitu");
            System.out.println("8. Erregistroa aldatu");
            System.out.println("Zure aukera sartu: ");

            int aukera = scanner.nextInt();
            scanner.nextLine();

            // Erabiltzaileak aukeratutako menuko aukeraren arabera metodoa deitzen du
            switch (aukera) {
                case 1:
                    FitxategiEragiketak.datuakFitxategianGorde(fitxategia);
                    break;
                case 2:
                    FitxategiEragiketak.fitxategiaHustu(fitxategia, scanner);
                    break;
                case 3:
                    FitxategiEragiketak.erregistroaBilatu(fitxategia, scanner);
                    break;
                case 4:
                    FitxategiEragiketak.generogatikBilatu(fitxategia, scanner);
                    break;
                case 5:
                    FitxategiEragiketak.erregistroaEzabatu(fitxategia, scanner);
                    break;
                case 6:
                    FitxategiEragiketak.ezabatutakoErregistroakIkusi(fitxategia);
                    break;
                case 7:
                    FitxategiEragiketak.erregistroaGehitu(fitxategia, scanner);
                    break;
                case 8:
                    FitxategiEragiketak.erregistroaAldatu(fitxategia, scanner);
                    break;
                default:
                    System.out.println("Aukera okerra.");
            }
        }
    }
}
