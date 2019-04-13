package pl.parser.nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pozycja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Pozycja
{
    private String kurs_kupna;

    private String kod_waluty;

    private String kurs_sprzedazy;

    private String nazwa_waluty;

    private String przelicznik;

    public String getKurs_kupna ()
    {
        return kurs_kupna;
    }

    public void setKurs_kupna (String kurs_kupna)
    {
        this.kurs_kupna = kurs_kupna;
    }

    public String getKod_waluty ()
    {
        return kod_waluty;
    }

    public void setKod_waluty (String kod_waluty)
    {
        this.kod_waluty = kod_waluty;
    }

    public String getKurs_sprzedazy ()
    {
        return kurs_sprzedazy;
    }

    public void setKurs_sprzedazy (String kurs_sprzedazy)
    {
        this.kurs_sprzedazy = kurs_sprzedazy;
    }

    public String getNazwa_waluty ()
    {
        return nazwa_waluty;
    }

    public void setNazwa_waluty (String nazwa_waluty)
    {
        this.nazwa_waluty = nazwa_waluty;
    }

    public String getPrzelicznik ()
    {
        return przelicznik;
    }

    public void setPrzelicznik (String przelicznik)
    {
        this.przelicznik = przelicznik;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [kurs_kupna = "+kurs_kupna+", kod_waluty = "+kod_waluty+", kurs_sprzedazy = "+kurs_sprzedazy+", nazwa_waluty = "+nazwa_waluty+", przelicznik = "+przelicznik+"]";
    }
}
