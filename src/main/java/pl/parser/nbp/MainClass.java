package pl.parser.nbp;

import org.apache.log4j.Logger;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import static pl.parser.nbp.ApiParser.*;
import static pl.parser.nbp.NarzedziaKalkulacji.getAskStandardDevotion;
import static pl.parser.nbp.NarzedziaKalkulacji.getBidMean;

public class MainClass {
   public final static Logger log = Logger.getLogger(MainClass.class);


    public static void main(String[] args) {


        if (args.length < 3) {
            log.error("Invalid number of parameters");
            System.exit(1);
        }

        String currencyCode = args[0];
        String startDate = args[1];
        String endDate = args[2];


        List<String> listOfCodes = listOfCodes(startDate, endDate);

        List<String> finalListOfCodes = searchedListOfCodes(listOfCodes, startDate, endDate);


        List<InputStream> inputStreamList = getInputStreamList(finalListOfCodes);

        List<Tabela_kursow> listOfExchangeRates = returnListOfMappedExchangeRates(inputStreamList);

        System.out.println(getBidMean(listOfExchangeRates, currencyCode)+ "<-------- BidMean ");
        System.out.println(getAskStandardDevotion(listOfExchangeRates, currencyCode)+"<------- AskStandardDevotion");


    }





    public static List<String> searchedListOfCodes(List<String> listofcodes, String startDate, String endDate) {
        List<String> searchedCodeList = new ArrayList<String>();
        searchedCodeList.addAll(listofcodes);

        LocalDate beginningDate = DateParser.parseDate(startDate);
        LocalDate finishDate = DateParser.parseDate(endDate);


        Iterator<String> iterator = searchedCodeList.iterator();
        while (iterator.hasNext()) {
            String kurs = iterator.next();

            LocalDate parsedDate = DateParser.parseCodeToDate(kurs);
            if (parsedDate.isBefore(beginningDate) || parsedDate.isAfter(finishDate)) {
                iterator.remove();
            }
        }
        return searchedCodeList;
    }





}



