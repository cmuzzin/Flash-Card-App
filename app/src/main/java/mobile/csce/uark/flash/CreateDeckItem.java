package mobile.csce.uark.flash;

import java.io.Serializable;

/**
 * Created by chrismuzzin on 11/11/14.
 */
public class CreateDeckItem implements Serializable {

    public String deckname;



public String GetDeckname()
{
    return deckname;
}

public void setDeckname(String n)
{
    deckname = n;
}

public String ToString()
{

    return deckname;
}

}
