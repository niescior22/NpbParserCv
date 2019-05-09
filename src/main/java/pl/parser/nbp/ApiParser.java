package pl.parser.nbp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.parser.nbp.MainClass.log;
import static pl.parser.nbp.DateParser.getParsedYearFromDate;

public  class ApiParser {



    public static List<String> listOfCodes(String startDate, String endDate) {

        List<String> codeslist = new ArrayList<>();
        InputStream inputStream = null;
        try {
            Integer startDateInt = Integer.parseInt(getParsedYearFromDate(startDate));
            Integer endDateInt = Integer.parseInt(getParsedYearFromDate(endDate));
            for (int i = startDateInt; i <= endDateInt; i++) {
                inputStream = new URL("http://www.nbp.pl/kursy/xml/dir" + i + ".txt").openStream();

                codeslist.addAll(returnListWithCodes(inputStream));

            }

        } catch (IOException e) {
            log.error("Error on loading url. Not valid url.");
            System.exit(3);
        }
        return codeslist;
    }


    public static List<String> returnListWithCodes(InputStream stream) {
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

    public static List<InputStream> getInputStreamList(List<String> exchangeRatesCodesList) {
        String kod = "";
        List<InputStream> inputStreamList = new ArrayList<InputStream>();
        for (int i = 0; i < exchangeRatesCodesList.size(); i++) {
            kod = exchangeRatesCodesList.get(i);
            URL url = null;
            try {
                url = new URL("http://www.nbp.pl/kursy/xml/" + kod + ".xml");
            } catch (MalformedURLException e) {
                log.error("No legal protocol found");
                System.exit(3);
            }
            try {
                inputStreamList.add(url.openStream());
            } catch (IOException e) {
                log.error("Error on loading url. Not valid url.");
                System.exit(3);
            }
        }
        return inputStreamList;

    }
    public static List<Tabela_kursow> returnListOfMappedExchangeRates(List<InputStream> inputStreamList) {
        JAXBContext jaxbContext;
        List<Tabela_kursow> exchangeRatesObjectLists = new ArrayList<Tabela_kursow>();
        try {
            jaxbContext = JAXBContext.newInstance(Tabela_kursow.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            for (int i = 0; i < inputStreamList.size(); i++) {
                Tabela_kursow tabela_kursow = (Tabela_kursow) unmarshaller.unmarshal(inputStreamList.get(i));

                exchangeRatesObjectLists.add(tabela_kursow);
            }

        } catch (JAXBException e) {
            log.error("Error during maping to Pojo. Not valid class formats or xml properties");
            System.exit(3);
        }
        return exchangeRatesObjectLists;
    }
}
