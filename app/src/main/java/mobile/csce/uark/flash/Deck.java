package mobile.csce.uark.flash;

import java.io.Serializable;

/**
 * Created by chrismuzzin on 11/11/14.
 */
public class Deck implements Serializable {
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    private long ID;
    public String deckname;



public String GetDeckname()
{
    return deckname;
}

public void setDeckname(String n)
{
    deckname = n;
}

@Override
public String toString()
{

    return deckname;
}

}
