package ausazkoFitxategiak;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FitxategiEragiketak {
	
	private static final int RECORD_SIZE = 172;
	
	
	/**
	 * Datuak fitxategian gorde
	 * @param fitxategia Datuak gorde nahi diren fitxategia
	 */
    public static void datuakFitxategianGorde(File fitxategia) {
    	
        int kodigoa[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        String izenburua[] = {
            "31 días",
            "El hijo de la novia",
            "El señor de los anillos. La comunidad del anillo.",
            "Mar adentro",
            "Casablanca",
            "El bola",
            "Torrente, el brazo tonto de la ley",
            "Solas",
            "Poseidón",
            "Flags of Our Fathers"
        };
        
        int urtea[] = {2000, 2001, 2001, 2004, 1942, 2000, 1998, 1998, 2005, 2005};
        
        String nazionalitatea[] = {
            "Estados Unidos",
            "España Argentina",
            "Nueva Zelanda",
            "España",
            "Estados Unidos",
            "España",
            "España",
            "España",
            "Estados Unidos",
            "Estados Unidos"
        };
        
        int iraupena[] = {145, 124, 160, 95, 98, 88, 97, 101, 105, 108};
        
        String generoa[] = {
            "suspense",
            "drama",
            "aventuras",
            "drama",
            "romantica",
            "drama",
            "comedia",
            "drama",
            "aventuras",
            "drama"
        };

        LocalDate estreinuData[] = {
            LocalDate.of(2001, 3, 23),
            LocalDate.of(2001, 11, 23),
            LocalDate.of(2001, 12, 19),
            LocalDate.of(2004, 9, 3),
            LocalDate.of(1946, 12, 19),
            LocalDate.of(2000, 10, 20),
            LocalDate.of(1998, 3, 13),
            LocalDate.of(1999, 3, 5),
            LocalDate.of(2005, 6, 25),
            LocalDate.of(2005, 7, 2)
        };
        
        double taquilla[] = {
            1103731.95, 7230415.69, 31263314.97, 19517968.62, 318310.24,
            2998626.52, 10902559.95, 3675149.47, 0, 0
        };

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "rw")) {
            // Erregistro bakoitzaren tamaina finkoa definitzen dugu
            int recordSize = 172;

            for (int i = 0; i < kodigoa.length; i++) {
                // Erregistroa idazteko posizioa kalkulatzen dugu
                long posizioa = i * recordSize;
                file.seek(posizioa);

                // Kodea idazten dugu (int - 4 byte)
                file.writeInt(kodigoa[i]);

                // Izenburua idazten dugu (30 karaktere - 60 byte)
                StringBuffer bufferIzenburua = new StringBuffer(izenburua[i]);
                bufferIzenburua.setLength(30);
                file.writeChars(bufferIzenburua.toString());

                // Urtea idazten dugu (int - 4 byte)
                file.writeInt(urtea[i]);

                // Nazionalitatea idazten dugu (20 karaktere - 40 byte)
                StringBuffer bufferNazionalitatea = new StringBuffer(nazionalitatea[i]);
                bufferNazionalitatea.setLength(20);
                file.writeChars(bufferNazionalitatea.toString());

                // Estreinaldi data idazten dugu (3 int - 12 byte)
                file.writeInt(estreinuData[i].getYear());
                file.writeInt(estreinuData[i].getMonthValue());
                file.writeInt(estreinuData[i].getDayOfMonth());

                // Iraupena idazten dugu (int - 4 byte)
                file.writeInt(iraupena[i]);

                // Generoa idazten dugu (20 karaktere - 40 byte)
                StringBuffer bufferGeneroa = new StringBuffer(generoa[i]);
                bufferGeneroa.setLength(20);
                file.writeChars(bufferGeneroa.toString());

                // Takila idazten dugu (double - 8 byte)
                file.writeDouble(taquilla[i]);
            }
            System.out.println("Datuak ondo idatzi dira fitxategian.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * Fitxategia husteko metodoa
     * @param fitxategia Hustu nahi den fitxategia
     * @param scanner Erabiltzailearen erantzuna irakurtzeko Scanner objektua
     *
     * Metodo honek erabiltzaileari fitxategia hustu nahi duen galdetzen dio. 
     * Erabiltzaileak "bai" erantzuten badu, fitxategiko edukia ezabatzen du, fitxategiaren luzera 0ra jarriz.
     * Bestela, ez du aldaketarik egiten.
     */
    public static void fitxategiaHustu(File fitxategia, Scanner scanner) {
        System.out.println("Seguru zaude fitxategia hustu nahi duzula? (bai/ez)");
        String erantzuna = scanner.nextLine();

        if (erantzuna.equalsIgnoreCase("bai")) {
            try (RandomAccessFile file = new RandomAccessFile(fitxategia, "rw")) {
                file.setLength(0); // Fitxategiaren luzera 0ra jartzen da, edukia ezabatuz
                System.out.println("Fitxategia hustu da.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Fitxategia ez da hustu.");
        }
    }
  
    /**
     * Erregistro bat bilatzeko metodoa
     * @param fitxategia Bilatu nahi den erregistroa duen fitxategia
     * @param scanner Erabiltzailearen kodearen sarrera irakurtzeko Scanner objektua
     *
     * Metodo honek fitxategian bilatzen du emandako kodeari dagokion erregistroa. 
     * Kode hori erabiltzaileak sartzen du, eta metodoak kalkulatzen du kode hori dagokion posizioa fitxategian.
     * Ondoren, kode horren posizioan dagoen erregistroa irakurtzen du eta datuak pantailan bistaratzen ditu.
     */
    public static void erregistroaBilatu(File fitxategia, Scanner scanner) {
        System.out.println("Sartu bilatu nahi duzun erregistroaren kodea: ");
        
        int kodea = 0;
        while (true) {
            try {
                // Lee el código como un entero
                kodea = Integer.parseInt(scanner.nextLine());
                if (kodea <= 0) {
                    System.out.println("Kodea zenbaki positibo bat izan behar da. Saiatu berriro:");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Zenbaki baliogabea. Mesedez, sartu zenbaki bat:");
            }
        }

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "r")) {
            // Kalkulatu erregistroaren posizioa kodearen arabera
            long posizioa = (kodea - 1) * RECORD_SIZE;

            // Egiaztatu posizioa fitxategiaren luzeraren barruan dagoen
            if (posizioa >= file.length()) {
                System.out.println("Errorea: kodeari dagokion erregistroa ez dago fitxategian.");
                return;
            }

            file.seek(posizioa);

            // Kodea irakurri (int - 4 byte)
            int kodigoa = file.readInt();

            // Izenburua irakurri (30 karaktere - 60 byte)
            StringBuffer izenburua = new StringBuffer();
            for (int i = 0; i < 30; i++) {
                char c = file.readChar();
                izenburua.append(c);
            }

            // Urtea irakurri (int - 4 byte)
            int urtea = file.readInt();

            // Nazionalitatea irakurri (20 karaktere - 40 byte)
            StringBuffer nazionalitatea = new StringBuffer();
            for (int i = 0; i < 20; i++) {
                char c = file.readChar();
                nazionalitatea.append(c);
            }

            // Estreinaldi data irakurri (3 int - 12 byte)
            int urteaEstreinua = file.readInt();
            int hilabeteaEstreinua = file.readInt();
            int egunaEstreinua = file.readInt();
            String estreinua = urteaEstreinua + "/" + hilabeteaEstreinua + "/" + egunaEstreinua;

            // Iraupena irakurri (int - 4 byte)
            int iraupena = file.readInt();

            // Generoa irakurri (20 karaktere - 40 byte)
            StringBuffer generoa = new StringBuffer();
            for (int i = 0; i < 20; i++) {
                char c = file.readChar();
                generoa.append(c);
            }

            // Taquilla irakurri (double - 8 byte)
            double taquilla = file.readDouble();

            // Aurkitutako erregistroaren informazioa formateatuta erakusten da
            System.out.print("-".repeat(120) + "\n");
            System.out.printf("%-10s %-25s %-10s %-20s %-10s %-10s %-10s %-10s \n", "Kodigoa", "Izenburua",
                    "Urtea", "Nazionalitatea", "Iraupena", "Estreinua", "Generoa", "Taquilla");
            System.out.print("-".repeat(120) + "\n");
            System.out.printf("%-10s %-25s %-10s %-20s %-10s %-10s %-10s %-10.2f \n", kodigoa, izenburua.toString().trim(),
                    urtea, nazionalitatea.toString().trim(), iraupena, estreinua, generoa.toString().trim(), taquilla);
            System.out.print("-".repeat(120) + "\n");

        } catch (IOException e) {
            System.out.println("Errorea fitxategia irakurtzean: " + e.getMessage());
        }
    }


    
    /**
     * Generoaren arabera bilaketa egiten duen metodoa
     * @param fitxategia Bilatu nahi den erregistroa duen fitxategia
     * @param scanner Erabiltzailearen generoaren sarrera irakurtzeko Scanner objektua
     *
     * Metodo honek fitxategian bilatzen ditu emandako generoarekin bat datozen erregistroak. 
     * Generoa erabiltzaileak sartzen du, eta metodoak fitxategiko erregistro bakoitza irakurtzen du,
     * generoa konparatuz. Bat datorren generoa aurkitzen bada, datuak pantailan bistaratzen dira.
     */
    public static void generogatikBilatu(File fitxategia, Scanner scanner) {
        System.out.println("Sartu bilatu nahi duzun generoa: ");
        String generoa = scanner.nextLine().toLowerCase();

        boolean headerShown = false; // Bandera para controlar la cabecera

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "r")) {
            file.seek(0); // Fitxategiaren hasierara joan
            while (file.getFilePointer() < file.length()) {
                // Erregistroaren datuak irakurri
                int kodigoa = file.readInt();

                // Izenburua irakurri (30 karaktere - 60 byte)
                StringBuffer izenburua = new StringBuffer();
                for (int i = 0; i < 30; i++) {
                    char c = file.readChar();
                    izenburua.append(c);
                }

                // Urtea irakurri (int - 4 byte)
                int urtea = file.readInt();

                // Nazionalitatea irakurri (20 karaktere - 40 byte)
                StringBuffer nazionalitatea = new StringBuffer();
                for (int i = 0; i < 20; i++) {
                    char c = file.readChar();
                    nazionalitatea.append(c);
                }

                // Estreinaldi data irakurri (3 int - 12 byte)
                int urteaEstreinua = file.readInt();
                int hilabeteaEstreinua = file.readInt();
                int egunaEstreinua = file.readInt();

                // Iraupena irakurri (int - 4 byte)
                int iraupena = file.readInt();

                // Generoa irakurri (20 karaktere - 40 byte)
                StringBuffer generoaF = new StringBuffer();
                for (int i = 0; i < 20; i++) {
                    char c = file.readChar();
                    generoaF.append(c);
                }

                // Taquilla irakurri (double - 8 byte)
                double taquilla = file.readDouble();

                // Generoa konparatu eta bat badator, bistaratzen du
                if (generoaF.toString().trim().toLowerCase().contains(generoa)) {
                    if (!headerShown) {
                        // Cabecera se muestra solo una vez
                    	System.out.print(" ".repeat(30) + "Pelikularen generoa: " + generoa + "\n");
                        System.out.print("-".repeat(120) + "\n");
                        System.out.printf("%-10s %-25s %-10s %-20s %-10s %-10s %-10s %-10s \n", "Kodigoa", "Izenburua",
                                "Urtea", "Nazionalitatea", "Iraupena", "Estreinua", "Generoa", "Taquilla");
                        System.out.print("-".repeat(120) + "\n");
                        headerShown = true; // Actualiza la bandera
                    }

                    // Muestra el registro
                    System.out.printf("%-10s %-25s %-10s %-20s %-10s %-10s %-10s %-10.2f \n", kodigoa, izenburua.toString().trim(),
                            urtea, nazionalitatea.toString().trim(), iraupena, urteaEstreinua + "-" + hilabeteaEstreinua + "-" + egunaEstreinua,
                            generoaF.toString().trim(), taquilla);
                }
            }

            if (!headerShown) {
                System.out.println("Ez da generorik aurkitu: " + generoa);
            } else {
                System.out.print("-".repeat(120) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Erregistro bat ezabatzeko metodoa
     * @param fitxategia Ezabatu nahi den erregistroa duen fitxategia
     * @param scanner Erabiltzailearen kodearen sarrera irakurtzeko Scanner objektua
     *
     * Metodo honek fitxategian emandako kodearen arabera erregistro bat ezabatzen du. 
     * Erregistroaren kodea -1 balioarekin markatzen da ezabatutzat jotzeko. Gainerako datuak ez dira fisikoki ezabatzen.
     */
    public static void erregistroaEzabatu(File fitxategia, Scanner scanner) {
        System.out.println("Sartu ezabatu nahi duzun fitxategiaren kodea: ");
        int kodea = scanner.nextInt();

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "rw")) {
            // Kalkulatu erregistroaren posizioa kodearen arabera
            long posizioa = (kodea - 1) * RECORD_SIZE;
            file.seek(posizioa);

            // Kodea ezabatu bezala markatu, datuak mantenduz
            file.writeInt(-1);  // Ezabatutako erregistroaren adierazlea

            System.out.println("Erregistroa ezabatu da.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ezabatutako erregistroak bistaratzen dituen metodoa
     * @param fitxategia Erregistroak dituen fitxategia
     *
     * Metodo honek fitxategian zehar begiratzen du eta ezabatuta bezala markatutako erregistroak 
     * bistaratu egiten ditu. Erregistro bat ezabatutzat jotzen da kodearen balioa -1 denean.
     * Ezabatutako erregistroaren datuak pantailan erakusten dira.
     */
    public static void ezabatutakoErregistroakIkusi(File fitxategia) {
        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "r")) {
            long numRecords = file.length() / RECORD_SIZE;

            System.out.println("Ezabatutako erregistroak:");
            for (int i = 0; i < numRecords; i++) {
                long posizioa = i * RECORD_SIZE;
                file.seek(posizioa);

                int kodea = file.readInt();
                if (kodea == -1) {  // Egiaztatu ea erregistroa ezabatuta dagoen
                    // Ezabatutako erregistroaren datuak irakurri eta bistaratu
                    StringBuffer izenburua = new StringBuffer();
                    for (int j = 0; j < 30; j++) {
                        izenburua.append(file.readChar());
                    }
                    int urtea = file.readInt();
                    
                    StringBuffer nazionalitatea = new StringBuffer();
                    for (int j = 0; j < 20; j++) {
                        nazionalitatea.append(file.readChar());
                    }
                    
                    int urteaEstreinua = file.readInt();
                    int hilabeteaEstreinua = file.readInt();
                    int egunaEstreinua = file.readInt();
                    int iraupena = file.readInt();
                    
                    StringBuffer generoa = new StringBuffer();
                    for (int j = 0; j < 20; j++) {
                        generoa.append(file.readChar());
                    }
                    
                    double taquilla = file.readDouble();

                    // Ezabatutako erregistroaren datuak bistaratu
                    System.out.println("Erregistroa #" + (i + 1) + " ezabatuta dago:");
                    System.out.println("Izenburua: " + izenburua.toString().trim());
                    System.out.println("Urtea: " + urtea);
                    System.out.println("Nazionalitatea: " + nazionalitatea.toString().trim());
                    System.out.println("Estreinu data: " + urteaEstreinua + "/" + hilabeteaEstreinua + "/" + egunaEstreinua);
                    System.out.println("Iraupena: " + iraupena);
                    System.out.println("Generoa: " + generoa.toString().trim());
                    System.out.println("Taquilla: " + taquilla);
                    System.out.println();
                } else {
                    // Hurrengo erregistroa saltatu
                    file.skipBytes(RECORD_SIZE - 4); // 4 byte irakurri dira `file.readInt()`-ekin
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Erregistro berri bat gehitzeko metodoa
     * @param fitxategia Erregistroa gehitu nahi den fitxategia
     * @param scanner Erabiltzailearen datuak irakurtzeko Scanner objektua
     *
     * Metodo honek erabiltzaileari erregistro berri baten datuak sartzeko eskatzen dio eta datu horiek 
     * fitxategian gordetzen ditu. Kode berri bat automatikoki kalkulatzen da eta 
     * gainerako datuak erabiltzaileak sartzen ditu eskuz. 
     * Datuak fitxategian gordetzen dira, kokapen zehatz batean.
     */
    public static void erregistroaGehitu(File fitxategia, Scanner scanner) {
        int kodea = kodeTxikienaAurkitu(fitxategia);

        System.out.println("Libre dagoen kode txikiena: " + kodea);

        System.out.println("Sartu izenburua: ");
        String izenburua = scanner.nextLine();

        int urtea = 0;
        while (true) {
            System.out.println("Sartu urtea: ");
            if (scanner.hasNextInt()) {
                urtea = scanner.nextInt();
                if (urtea > 0) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        System.out.println("Sartu nazionalitatea: ");
        String nazionalitatea = scanner.nextLine();

        int urteaEstreinua = 0, hilabeteaEstreinua = 0, egunaEstreinua = 0;
        while (true) {
            System.out.println("Sartu estreinu data (urtea): ");
            if (scanner.hasNextInt()) {
                urteaEstreinua = scanner.nextInt();
                if (urteaEstreinua > 0) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        while (true) {
            System.out.println("Sartu estreinu data (hilabetea): ");
            if (scanner.hasNextInt()) {
                hilabeteaEstreinua = scanner.nextInt();
                if (hilabeteaEstreinua >= 1 && hilabeteaEstreinua <= 12) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        while (true) {
            System.out.println("Sartu estreinu data (eguna): ");
            if (scanner.hasNextInt()) {
                egunaEstreinua = scanner.nextInt();
                if (egunaEstreinua >= 1 && egunaEstreinua <= 31) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        int iraupena = 0;
        while (true) {
            System.out.println("Sartu iraupena (min): ");
            if (scanner.hasNextInt()) {
                iraupena = scanner.nextInt();
                if (iraupena > 0) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        System.out.println("Sartu generoa ('Suspense', 'Drama', 'Romantica', 'Comedia', 'Aventura', 'Fantasia'): ");
        String generoa = scanner.nextLine();

        double taquilla = 0;
        while (true) {
            System.out.println("Sartu taquilla: ");
            if (scanner.hasNextDouble()) {
                taquilla = scanner.nextDouble();
                if (taquilla >= 0) break;
            }
            System.out.println("Errorea: Sartu balio egokia.");
            scanner.nextLine();
        }
        scanner.nextLine();

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "rw")) {
            long posicion = (kodea - 1) * RECORD_SIZE;
            file.seek(posicion);

            // Erregistroaren kodea idatzi
            file.writeInt(kodea);

            // Izenburua idatzi (30 karaktere - 60 byte)
            StringBuffer bufferIzenburua = new StringBuffer(izenburua);
            bufferIzenburua.setLength(30);
            file.writeChars(bufferIzenburua.toString());

            // Urtea idatzi
            file.writeInt(urtea);

            // Nazionalitatea idatzi (20 karaktere - 40 byte)
            StringBuffer bufferNazionalitatea = new StringBuffer(nazionalitatea);
            bufferNazionalitatea.setLength(20);
            file.writeChars(bufferNazionalitatea.toString());

            // Estreinu data idatzi
            file.writeInt(urteaEstreinua);
            file.writeInt(hilabeteaEstreinua);
            file.writeInt(egunaEstreinua);

            // Iraupena idatzi
            file.writeInt(iraupena);

            // Generoa idatzi (20 karaktere - 40 byte)
            StringBuffer bufferGeneroa = new StringBuffer(generoa);
            bufferGeneroa.setLength(20);
            file.writeChars(bufferGeneroa.toString());

            // Taquilla idatzi
            file.writeDouble(taquilla);

            System.out.println("Erregistroa gehitu da.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kode erabilgarri txikiena aurkitzeko metodo laguntzailea
     * @param fitxategia Kodeak gordetzen dituen fitxategia
     * @return Aurkitu den erabilgarri dagoen kode txikiena; errore baten kasuan, -1
     *
     * Metodo honek fitxategian bilatzen du erabilgarri dagoen kode txikiena. 
     * Fitxategiko erregistro bakoitzeko kodeak irakurtzen ditu eta kode horiek 
     * boolean array batean markatzen ditu erabilitakotzat.
     * Arrayan erabilgarri dagoen lehen kodea aurkitzen du eta itzultzen du.
     */
    
    public static int kodeTxikienaAurkitu(File fitxategia) {
        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "r")) {
            boolean[] kodeErabiliak = new boolean[1000]; // Kodeen gehienezko balioa 1000 dela suposatuz
            while (file.getFilePointer() < file.length()) {
                int kodigoa = file.readInt();
                if (kodigoa > 0 && kodigoa < kodeErabiliak.length) {
                	kodeErabiliak[kodigoa] = true; // Kodea erabilita bezala markatu
                }
                file.skipBytes(RECORD_SIZE - 4); // Hurrengo erregistroa saltatu
            }
            // Erabiltzen ez den lehen kode txikiena aurkitu
            for (int i = 1; i < kodeErabiliak.length; i++) {
                if (!kodeErabiliak[i]) {
                    return i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Errore baten kasuan, -1 itzuli
    }

    /**
     * Erregistro baten datuak aldatzeko metodoa
     * @param fitxategia Erregistroak dituen fitxategia
     * @param scanner Erabiltzailearen sarrerak jasotzeko Scanner objektua
     *
     * Metodo honek erabiltzaileari zein erregistro aldatu nahi duen galdetzen dio, kodearen arabera.
     * Erregistroa aurkitzen du eta haren datuak erakusten dizkio erabiltzaileari. 
     * Ondoren, erabiltzaileak zein datu aldatu nahi duen aukeratzen du eta datu berriak sartzen ditu.
     * Aukeratutako datua fitxategian eguneratzen da.
     */
    public static void erregistroaAldatu(File fitxategia, Scanner scanner) {
        final int RECORD_SIZE = 106; // Erregistroaren tamaina osoa (doitu tamaina errealaren arabera)
        
        System.out.println("Sartu aldatu nahi duzun erregistroaren kodea: ");
        int kodea = scanner.nextInt();
        scanner.nextLine();

        try (RandomAccessFile file = new RandomAccessFile(fitxategia, "rw")) {
            long posizioa = (kodea - 1) * RECORD_SIZE;
            file.seek(posizioa);

            // Erregistroaren datu aktualak irakurri
            int codigoRegistro = file.readInt(); // Erregistroaren kodea

            // Izenburua irakurri
            StringBuffer izenburua = new StringBuffer();
            for (int i = 0; i < 30; i++) {
                izenburua.append(file.readChar());
            }

            // Nazionalitatea irakurri
            StringBuffer nazionalitatea = new StringBuffer();
            for (int i = 0; i < 20; i++) {
                nazionalitatea.append(file.readChar());
            }

            int iraupena = file.readInt(); // Iraupena
            int urteaEstreinua = file.readInt(); // Estreinu urtea
            int hilabeteaEstreinua = file.readInt(); // Estreinu hilabetea
            int egunaEstreinua = file.readInt(); // Estreinu eguna

            // Generoa irakurri
            StringBuffer generoa = new StringBuffer();
            for (int i = 0; i < 20; i++) {
                generoa.append(file.readChar());
            }

            double taquilla = file.readDouble(); // Taquilla

            // Erregistroaren datu aktualak erakutsi
            System.out.println("Erregistroaren kodea: " + codigoRegistro);
            System.out.println("Izenburua: " + izenburua.toString().trim());
            System.out.println("Nazionalitatea: " + nazionalitatea.toString().trim());
            System.out.println("Generoa: " + generoa.toString().trim());
            System.out.println("Taquilla: " + taquilla);
            System.out.println("Iraupena: " + iraupena);
            System.out.println("Estreinu data: " + urteaEstreinua + "-" + hilabeteaEstreinua + "-" + egunaEstreinua);
            System.out.println("Urtea: " + urteaEstreinua);

            // Erabiltzaileari aldatu nahi duen eremua galdetu
            System.out.println("Zer aldatu nahi duzu?");
            System.out.println("1. Izenburua");
            System.out.println("2. Nazionalitatea");
            System.out.println("3. Generoa");
            System.out.println("4. Taquilla");
            System.out.println("5. Iraupena");
            System.out.println("6. Estreinu data");
            System.out.println("7. Urtea");

            int aukera = scanner.nextInt();
            scanner.nextLine();

            // Aukeratutako eremua eguneratzeko logika
            switch (aukera) {
                case 1:
                    System.out.println("Sartu izenburua: ");
                    String izenburuaBerria = scanner.nextLine();
                    file.seek(posizioa + 4); // Izenburuaren posizioa
                    StringBuffer bufferIzenburua = new StringBuffer(izenburuaBerria);
                    bufferIzenburua.setLength(30);
                    file.writeChars(bufferIzenburua.toString());
                    break;
                case 2:
                    System.out.println("Sartu nazionalitatea: ");
                    String nazionalitateaBerria = scanner.nextLine();
                    file.seek(posizioa + 68); // Nazionalitatearen posizioa
                    StringBuffer bufferNazionalitatea = new StringBuffer(nazionalitateaBerria);
                    bufferNazionalitatea.setLength(20);
                    file.writeChars(bufferNazionalitatea.toString());
                    break;
                case 3:
                    System.out.println("Sartu generoa: ");
                    String generoaBerria = scanner.nextLine();
                    file.seek(posizioa + 96); // Generoaren posizioa
                    StringBuffer bufferGeneroa = new StringBuffer(generoaBerria);
                    bufferGeneroa.setLength(20);
                    file.writeChars(bufferGeneroa.toString());
                    break;
                case 4:
                    System.out.println("Sartu taquilla: ");
                    double taquillaNueva = scanner.nextDouble();
                    file.seek(posizioa + 136); // Taquilla-ren posizioa
                    file.writeDouble(taquillaNueva);
                    break;
                case 5:
                    System.out.println("Sartu iraupena: ");
                    int iraupenaBerria = scanner.nextInt();
                    file.seek(posizioa + 144); // Iraupenaren posizioa
                    file.writeInt(iraupenaBerria);
                    break;
                case 6:
                    System.out.println("Sartu estreinu data (urtea): ");
                    int urteaNueva = scanner.nextInt();
                    file.seek(posizioa + 148); // Estreinu urtearen posizioa
                    file.writeInt(urteaNueva);
                    System.out.println("Sartu estreinu data (hilabetea): ");
                    int hilabeteaNueva = scanner.nextInt();
                    file.writeInt(hilabeteaNueva);
                    System.out.println("Sartu estreinu data (eguna): ");
                    int egunaNueva = scanner.nextInt();
                    file.writeInt(egunaNueva);
                    break;
                case 7:
                    System.out.println("Sartu urtea: ");
                    int urtea = scanner.nextInt();
                    file.seek(posizioa + 160); // Urtearen posizioa
                    file.writeInt(urtea);
                    break;
                default:
                    System.out.println("Aukera okerra.");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
