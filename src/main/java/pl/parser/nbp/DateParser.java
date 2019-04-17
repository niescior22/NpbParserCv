package pl.parser.nbp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser { // date utility
    private static final DateTimeFormatter CODE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final DateTimeFormatter USER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //zwrocMiesiacIdzienZdaty
    //getParsedYearFromDate

    public static LocalDate parseCodeToDate(String code) {
        return LocalDate.parse(code.substring(5), CODE_DATE_FORMATTER);
    }

    public static LocalDate parseDate(String date){
        return LocalDate.parse(date, USER_DATE_FORMATTER);
    }
    public static String getParsedYearFromDate(String date) {
        String parsedYear = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.charAt(0));
        stringBuilder.append(date.charAt(1));
        stringBuilder.append(date.charAt(2));
        stringBuilder.append(date.charAt(3));
        parsedYear = stringBuilder.toString();
        return parsedYear;
    }
}
