package pl.parser.nbp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {


    public static void main(String[] args) throws IOException {

        String dataPoczątkowa = "2013-01-28";
        String dataKońcowa = "2013-01-31";
        String kodWaluty = "EUR";



        InputStream inputStream = new URL("http://www.nbp.pl/kursy/xml/dir" + zwrocRokDaty(dataPoczątkowa) + ".txt").openStream();


        List<String> listaKursow = zwrocListeZkodamiKursowWalut(inputStream);

        List<String> lista2 = zwrocListeZkodamiKursowWalutZprzedziału(listaKursow, dataPoczątkowa, dataKońcowa);

        List<InputStream> inputStreamList = ZwrocListeInputStreamow(lista2);

        List<Tabela_kursow> tabelaKursowList = zwrocListeZmapowanychKursowList(inputStreamList);
        
        System.out.println(zaokrąglijDoCzwategoMiejsca(liczSredniąKupna(tabelaKursowList, kodWaluty)));

        System.out.println(zaokrąglijDoCzwategoMiejsca(liczOdchylenieStandardoweKursuSprzedazy(tabelaKursowList,kodWaluty)));


    }
    /**
    Zwraca listę Stringow  np [c001z130102, c002z130103, c003z130104, c004z130107....c251z131231] z roku branego ze streama w argumencie zaczynających się na
     c, c to typ tabeli w którym znajdują sie informację jak kursy walut z danego dnia

     */

    public static List<String> zwrocListeZkodamiKursowWalut(InputStream stream) {
        String line;
        List<String> ulrs = new ArrayList<String>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.startsWith("c")) {
                ulrs.add(line);
            }
        }
        return ulrs;
    }
    /** Zwraca liste z kodami walut z przedziału od daty początkowej do koncowej
     * Korzysta z metody zwracajacej index z podanej daty
     * przy danych wejsciowych
     * data początkowa = "2013-01-28"
     * String dataKońcowa = "2013-01-31'
     * zwraca Liste [c019z130128, c020z130129, c021z130130, c022z130131]

    */

    public static List<String> zwrocListeZkodamiKursowWalutZprzedziału(List<String> listaTabel, String dataPoczątkowa, String dataKoncowa) {
        int indexPoczatkowy = zwrocIndexDaty(listaTabel, dataPoczątkowa);
        int indexKoncowy = zwrocIndexDaty(listaTabel, dataKoncowa);
        List<String> tabelaZPrzedzialu = new ArrayList<String>();

        for (int i = indexPoczatkowy; i < indexKoncowy + 1; i++) {
            tabelaZPrzedzialu.add(listaTabel.get(i));
        }

        return tabelaZPrzedzialu;
    }

    /**
     * Metoda zwraca z daty np "2013-01-28"
     * "0128" potrzebną do zwrocenia indeksu z daty
     */

    public static String ZwrocMiesiacIdzienZdaty(String date) {
        String sparsowanaData = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.charAt(5));
        stringBuilder.append(date.charAt(6));
        stringBuilder.append(date.charAt(8));
        stringBuilder.append(date.charAt(9));
        sparsowanaData = stringBuilder.toString();

        return sparsowanaData;
    }

    public static String zwrocRokDaty(String date) {
        String sparsowanaData = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.charAt(0));
        stringBuilder.append(date.charAt(1));
        stringBuilder.append(date.charAt(2));
        stringBuilder.append(date.charAt(3));
        sparsowanaData = stringBuilder.toString();
        return sparsowanaData;
    }

    /**
     metoda przeszukuje liste na podstawie  sparsowanego miesiaca i dnia miesiaca np "0128"
     zwraca indeks nazwy Tabeli kursow zawierającą tą date
     */

    public static int zwrocIndexDaty(List<String> list, String data) {
        String dateParsed = ZwrocMiesiacIdzienZdaty(data);
        String kod;
        int k = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(dateParsed)) {
                k = i;
            }
        }
        return k;
    }

    /**
     ZwracaListe Url potrzebnych do zmapowania obiektu
     */

    public static List<InputStream> ZwrocListeInputStreamow(List<String> listaTabelKursowZprzedziału) throws IOException {
        String kod = "";
        List<InputStream> inputStreamList = new ArrayList<InputStream>();
        for (int i = 0; i < listaTabelKursowZprzedziału.size(); i++) {
            kod = listaTabelKursowZprzedziału.get(i);
            URL url = new URL("http://www.nbp.pl/kursy/xml/" + kod + ".xml");
            inputStreamList.add(url.openStream());
        }
        return inputStreamList;
    }
    /**
     * Metoda mapująca xml na pojo (plain old java object) przy pomocy JAXB zwracająca liste TabelKursow
     *
     */


    public static List<Tabela_kursow> zwrocListeZmapowanychKursowList(List<InputStream> inputStreamList) {
        JAXBContext jaxbContext;
        List<Tabela_kursow> tabelaKursowList = new ArrayList<Tabela_kursow>();
        try {
            jaxbContext = JAXBContext.newInstance(Tabela_kursow.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            for (int i = 0; i < inputStreamList.size(); i++) {
                Tabela_kursow tabela_kursow = (Tabela_kursow) unmarshaller.unmarshal(inputStreamList.get(i));

                tabelaKursowList.add(tabela_kursow);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return tabelaKursowList;
    }

    /**
     *Zwraca średnią wartość kupna waluty w paramatrze z przedziału daty początkowej do daty końcowej
     */

    public static double liczSredniąKupna(List<Tabela_kursow> tabela_kursows, String kodwaluty) {
        double kursKupna = 0.00;
        double średnia = 0.00;
        for (int i = 0; i < tabela_kursows.size(); i++) {
            for (int k = 0; k < tabela_kursows.get(i).getPozycja().size(); k++) {
                if (tabela_kursows.get(i).getPozycja().get(k).getKod_waluty().equalsIgnoreCase(kodwaluty)) {
                    kursKupna += Double.parseDouble(tabela_kursows.get(i).getPozycja().get(k).getKurs_kupna().replace(",", "."));
                }
            }
        }
        return kursKupna / tabela_kursows.size();
    }
    /**
     *Zwraca średnią wartość  sprzedazy waluty w paramatrze z przedziału daty początkowej do daty końcowej.
     * Uzywana przy liczeniu odchylenia standardowego sprzedazy
     */


    public static double liczSredniaKursuSprzedazy(List<Tabela_kursow> tabela_kursows, String kodwaluty) {
        double kursSprzedazy = 0.00;
        double średnia = 0.00;
        for (int i = 0; i < tabela_kursows.size(); i++) {
            for (int k = 0; k < tabela_kursows.get(i).getPozycja().size(); k++) {
                if (tabela_kursows.get(i).getPozycja().get(k).getKod_waluty().equalsIgnoreCase(kodwaluty)) {
                    kursSprzedazy += Double.parseDouble(tabela_kursows.get(i).getPozycja().get(k).getKurs_sprzedazy().replace(",", "."));
                }
            }
        }
        return  kursSprzedazy/tabela_kursows.size();
        /**
         *Zwraca odchylenie standardowe  z próby
         */

    }
    public static  double liczOdchylenieStandardoweKursuSprzedazy(List<Tabela_kursow> tabela_kursows, String kodwaluty) {
        double Wariancja = 0.0;
        double srednia = liczSredniaKursuSprzedazy(tabela_kursows, kodwaluty);
        double kursSprzedazy=0.00;
        for (int i = 0; i < tabela_kursows.size(); i++) {
            for (int k = 0; k < tabela_kursows.get(i).getPozycja().size(); k++) {
                if (tabela_kursows.get(i).getPozycja().get(k).getKod_waluty().equalsIgnoreCase(kodwaluty)) {
                    kursSprzedazy = Double.parseDouble(tabela_kursows.get(i).getPozycja().get(k).getKurs_sprzedazy().replace(",", "."));
                    Wariancja+=Math.pow(kursSprzedazy-srednia,2);
               }
            }
        }
        return Math.sqrt(Wariancja/(tabela_kursows.size()));

        }

    /**
      Zaokrągla doubla do 4 miejsca po przecinku
     */


    public static double zaokrąglijDoCzwategoMiejsca(double liczbaDoZaokręglenia){
            liczbaDoZaokręglenia *= 10000;

            liczbaDoZaokręglenia = Math.round(liczbaDoZaokręglenia);

            liczbaDoZaokręglenia /= 10000;

            return liczbaDoZaokręglenia;
        }
}



