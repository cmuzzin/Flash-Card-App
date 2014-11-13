package mobile.csce.uark.flash;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chrismuzzin on 11/11/14.
 */
public class Deck implements Serializable {

    public String deckname;
    private Date date;



public String GetDeckname()
{
    return deckname;
}
public Date getDate(){
    return date;
}

 public void setDate(Date d ){
     date = d;

 }


public void setDeckname(String n)
{
    deckname = n;
}

@Override
public String toString()
{
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String dateString = sdf.format(date);

    return  deckname + " " +"(" + dateString + ")";

}

}
