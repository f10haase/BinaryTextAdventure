// Für Eingaben in Konsole 
import java.util.Scanner;        // Lesen von Benutzereingaben

/**
 * Beschreiben Sie hier die Klasse EingabeSteuerung.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */
public class EingabeSteuerung
{
    // Scanner wird nur einmal für das ganze Spiel erstellt
    private static final Scanner eingabeLeser = new Scanner(System.in);

    public EingabeSteuerung() {
        // Konstruktor kann leer bleiben
    }

    /**
     * Liest eine ganze Zahl aus der Konsole ein.
     * Bei fehlerhafter Eingabe wird erneut gefragt.
     *
     * @return die gültige eingegebene Zahl
     */
    public int leseGanzzahl(String pAnzeigeText)  {
        int zahl = -1;
        boolean gueltig = false;

        while(!gueltig) {
            try {
                System.out.print(pAnzeigeText);
                zahl = eingabeLeser.nextInt();
                eingabeLeser.nextLine();
                gueltig = true;
            } catch (Exception e) {
                System.out.println(FehlerCode.FALSCHE_BENUTZEREINGABE.getNachricht());
                eingabeLeser.nextLine();
            }
        }
        return zahl;
    }

    /**
     * Liest einen Text/String aus der Konsole ein.
     * Leere Eingaben werden nicht akzeptiert.
     *
     * @return die gültige eingegebene Zeichenkette
     */
    public String leseString(String pAnzeigeText) {
        String eingabe = "";
        boolean gueltig = false;
        while (!gueltig) {
            try {
                System.out.print(pAnzeigeText);
                eingabe = eingabeLeser.nextLine().trim(); 

                if (eingabe.isEmpty()) {
                    System.out.println(FehlerCode.FALSCHE_BENUTZEREINGABE.getNachricht());
                } else {
                    gueltig = true; 
                }
            } catch (Exception e) {
                System.out.println(FehlerCode.FALSCHE_BENUTZEREINGABE.getNachricht());
                eingabeLeser.nextLine(); 
            }
        }
        return eingabe;
    }

}