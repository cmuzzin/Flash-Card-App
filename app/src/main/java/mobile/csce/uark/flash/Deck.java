package mobile.csce.uark.flash;

import java.io.Serializable;

/**
 * Created by chrismuzzin on 11/11/14.
 */
public class Deck{

    Deck(long id, String deckname)
    {
        ID = id;
        DeckName = deckname;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    private long ID;
    public String DeckName;



public String GetDeckname()
{
    return DeckName;
}

public void setDeckname(String n)
{
    DeckName = n;
}

@Override
public String toString()
{

    return DeckName;
}

}
