/**
 * Die abstrakte Klasse Inhalt stellt die gemeinsame Oberklasse
 * für alle Inhalte eines Textadventures dar.
 * Sie speichert den Text eines Knotens und definiert Methoden,
 * mit denen zwischen Ereignissen und Enden unterschieden werden kann.
 * 
 * @author f10haase@GitHub
 * @version 0.5-2026-1
 */
public abstract class Inhalt
{
    protected String schluesselwort; 
    protected String text;
    protected String bild;
    protected String sound;

    /**
     * Konstruktor der abstrakten Klasse Inhalt.
     * Initialisiert den Text mit einer Fehlermeldung,
     * falls noch kein konkreter Text gesetzt wurde.
     */
    public Inhalt()
    {
        schluesselwort = "Fehler 00: Achtung, Schlüsselwort von Ereignis oder Ende noch nicht gesetzt!";   
        text = "Fehler 01: Achtung, Text von Ereignis oder Ende noch nicht gesetzt!";
        bild = "Fehler 02: Achtung, Bild von Ereignis oder Ende noch nicht gesetzt!";
        sound = "Fehler 03: Achtung, Sound von Ereignis oder Ende noch nicht gesetzt!";
    }

    /**
     * Gibt das Schlüsselwort zurück.
     * 
     * @return das aktuelle Schlüsselwort
     */
    public String getSchluesselwort() {
        return schluesselwort;
    }

    /**
     * Setzt das Schlüsselwort auf den angegebenen Wert.
     * 
     * @param pSchluesselwort das neue Schlüsselwort
     */
    public void setSchluesselwort(String pSchluesselwort) {
        this.schluesselwort = pSchluesselwort;
    }

    /**
     * Gibt den Pfad oder Namen des Bildes zurück.
     * 
     * @return das aktuelle Bild
     */
    public String getBild() {
        return bild;
    }

    /**
     * Setzt das Bild auf den angegebenen Wert.
     * 
     * @param pBild der neue Bildpfad oder Name
     */
    public void setBild(String pBild) {
        this.bild = pBild;
    }

    /**
     * Gibt den Pfad oder Namen des Sounds zurück.
     * 
     * @return der aktuelle Sound
     */
    public String getSound() {
        return sound;
    }

    /**
     * Setzt den Sound auf den angegebenen Wert.
     * 
     * @param pSound der neue Soundpfad oder Name
     */
    public void setSound(String pSound) {
        this.sound = pSound;
    }

    /**
     * Gibt den Text des Inhalts zurück.
     *
     * @return der aktuell gespeicherte Text
     */
    public String getText() {
        return text; 
    }

    /**
     * Setzt den Text des Inhalts neu.
     *
     * @param pText der neue Text des Inhalts
     */
    public void setText(String pText) {
        text = pText; 
    }

    /**
     * Gibt an, ob dieses Objekt ein Ereignis darstellt.
     * Diese Methode muss von Unterklassen implementiert werden.
     *
     * @return true, wenn es sich um ein Ereignis handelt, sonst false
     */
    public abstract boolean istEreignis();

    /**
     * Gibt an, ob dieses Objekt ein Ende der Geschichte darstellt.
     * Diese Methode muss von Unterklassen implementiert werden.
     *
     * @return true, wenn es sich um ein Ende handelt, sonst false
     */
    public abstract boolean istEnde();
}
