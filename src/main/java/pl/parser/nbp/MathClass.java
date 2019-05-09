package pl.parser.nbp;

import java.util.List;

public class MathClass {




    public static double getAskMean(List<Tabela_kursow> ExchangeRatesList, String currencyCode) {
        double ask = 0.00;
        // todo: zastąpić stream'em
        for (Tabela_kursow exchangeRate : ExchangeRatesList) {
            for (Pozycja pozycja : exchangeRate.getPozycja()) {
                if (pozycja.getKod_waluty().equalsIgnoreCase(currencyCode)) {
                    ask += Double.parseDouble(pozycja.getKurs_sprzedazy().replace(",", "."));
                }
            }
        }
        return ask / ExchangeRatesList.size();


    }



    public static double getAskStandardDevotion(List<Tabela_kursow> exchangeRatesList, String currencyCode) {
        double Wariancja = 0.0;
        double mean = getAskMean(exchangeRatesList, currencyCode);
        double ask = 0.00;

        for (Tabela_kursow exchangeRate : exchangeRatesList) {
            for (Pozycja position : exchangeRate.getPozycja()){
                if (position.getKod_waluty().equalsIgnoreCase(currencyCode)) {
                    ask = Double.parseDouble(position.getKurs_sprzedazy().replace(",", "."));
                    Wariancja += Math.pow(ask - mean, 2);
                }
            }
        }
        double roundedStandardDevotion = roundTo4DecimalPlaces(Math.sqrt(Wariancja / (exchangeRatesList.size())));
        return roundedStandardDevotion;

    }

    public static double roundTo4DecimalPlaces(double numberToRound) {
        numberToRound *= 10000;

        numberToRound = Math.round(numberToRound);

        numberToRound /= 10000;

        return numberToRound;
    }


    public static double getBidMean(List<Tabela_kursow> exchangeRatesList, String currencyCode) {
        double bid = 0.00;
        // spróbuj zastąpić stream'em
        for (Tabela_kursow exchangeRate : exchangeRatesList) {
            for (Pozycja position : exchangeRate.getPozycja()) {
                if (position.getKod_waluty().equalsIgnoreCase(currencyCode)) {
                    bid += Double.parseDouble(position.getKurs_kupna().replace(",", "."));
                }
            }
        }
        double bidMean = roundTo4DecimalPlaces(bid / exchangeRatesList.size());
        return bidMean;
    }


}
