/**
 * Die Klasse Ende repräsentiert ein Endelement der Geschichte.
 * Sie kennzeichnet das Ende eines Spielpfades im Textadventure
 * und enthält den Abschlusstext für den Spieler.
 *
 * @author f10haase@GitHub 
 * @version 0.8-2026-1-14
 */
public class Ende extends Inhalt
{  
    /**
     * Konstruktor der Klasse Ende.
     * Erstellt ein neues Endelement mit dem angegebenen Text.
     *
     * @param pText Der Text, der am Ende der Geschichte ausgegeben wird
     */
    public Ende(String pSchluesselwort, String pText, String pBild, String pSound)
    {
        schluesselwort = pSchluesselwort;
        text = pText;    
        bild = pBild;
        sound = pSound;
    }
    
    /**
     * Gibt an, ob dieses Objekt ein Ereignis darstellt.
     * Da es sich um ein Ende handelt, wird false zurückgegeben.
     *
     * @return false, da dieses Objekt kein Ereignis ist
     */
    @Override
    public boolean istEreignis() {
        return false; 
    }
    
    /**
     * Gibt an, ob dieses Objekt ein Ende der Geschichte darstellt.
     *
     * @return true, da dieses Objekt ein Endknoten ist
     */
    @Override
    public boolean istEnde() {
        return true; 
    }
}
