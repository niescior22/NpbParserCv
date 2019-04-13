package pl.parser.nbp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Tabela_kursow
{
    private String data_publikacji;

    private String numer_tabeli;

    private String typ;

    private String data_notowania;

    private List<Pozycja>pozycja=new ArrayList<Pozycja>();

    public String getData_publikacji ()
    {
        return data_publikacji;
    }

    public void setData_publikacji (String data_publikacji)
    {
        this.data_publikacji = data_publikacji;
    }

    public String getNumer_tabeli ()
    {
        return numer_tabeli;
    }

    public void setNumer_tabeli (String numer_tabeli)
    {
        this.numer_tabeli = numer_tabeli;
    }

    public String getTyp ()
    {
        return typ;
    }

    public void setTyp (String typ)
    {
        this.typ = typ;
    }

    public String getData_notowania ()
    {
        return data_notowania;
    }

    public void setData_notowania (String data_notowania)
    {
        this.data_notowania = data_notowania;
    }

    public List<Pozycja> getPozycja() {
        return pozycja;
    }

    public void setPozycja(List<Pozycja> pozycja) {
        this.pozycja = pozycja;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data_publikacji = "+data_publikacji+", numer_tabeli = "+numer_tabeli+", typ = "+typ+", data_notowania = "+data_notowania+", pozycja = "+pozycja+"]";
    }
}