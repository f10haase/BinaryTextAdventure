/**
 * Die Klasse Ereignis repräsentiert einen Entscheidungsknoten
 * innerhalb der Geschichte des Textadventures.
 * Ein Ereignis enthält einen Text sowie zwei Antwortmöglichkeiten,
 * zwischen denen der Spieler wählen kann.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */
public class Ereignis extends Inhalt
{
    String linkeAntwort; 
    String rechteAntwort; 

    /**
     * Konstruktor der Klasse Ereignis.
     * Erstellt ein neues Ereignis mit einem Beschreibungstext
     * und zwei möglichen Antworten.
     *
     * @param pText Text, der das Ereignis beschreibt
     * @param pErsteAntwort Text der ersten Antwortmöglichkeit
     * @param pZweiteAntwort Text der zweiten Antwortmöglichkeit
     */
    public Ereignis(String pSchluesselwort, String pText, String pBild, String pSound, String pErsteAntwort, String pZweiteAntwort)
    {
        schluesselwort = pSchluesselwort;
        text = pText;    
        bild = pBild;
        sound = pSound;
        linkeAntwort = pErsteAntwort; 
        rechteAntwort = pZweiteAntwort;
    }
      
    /**
     * Gibt an, ob dieses Objekt ein Ereignis darstellt.
     *
     * @return true, da dieses Objekt ein Ereignisknoten ist
     */
    @Override
    public boolean istEreignis() {
        return true; 
    }
    
    /**
     * Gibt an, ob dieses Objekt ein Ende der Geschichte darstellt.
     *
     * @return false, da dieses Objekt kein Endknoten ist
     */
    @Override
    public boolean istEnde() {
        return false; 
    }
    
    /**
     * Setzt den Text der ersten Antwortmöglichkeit.
     *
     * @param pErsteAntwort neuer Text für die erste Antwort
     */
    public void setErsteAntwort(String pErsteAntwort) {
        linkeAntwort = pErsteAntwort;     
    }
    
    /**
     * Setzt den Text der zweiten Antwortmöglichkeit.
     *
     * @param pZweiteAntwort neuer Text für die zweite Antwort
     */
    public void setZweiteAntwort(String pZweiteAntwort) {
        rechteAntwort = pZweiteAntwort;      
    }
    
    /**
     * Liefert den Text der ersten Antwortmöglichkeit.
     *
     * @return Text der ersten Antwort
     */
    public String getErsteAntwort() {
        return linkeAntwort;
    }
    
    /**
     * Liefert den Text der zweiten Antwortmöglichkeit.
     *
     * @return Text der zweiten Antwort
     */
    public String getZweiteAntwort() {
        return rechteAntwort;
    }
}
