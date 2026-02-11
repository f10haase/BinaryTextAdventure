// Für Lesen und Schreiben von Dateien
import java.io.BufferedReader;    // Puffer zum zeilenweisen Lesen
import java.io.FileReader;        // Datei öffnen zum Lesen
import java.io.FileWriter;        // Datei öffnen zum Schreiben
import java.io.Writer;            // Allgemeine Schreib-Schnittstelle
import java.io.IOException;       // Fehlerbehandlung bei IO
import java.io.FileNotFoundException; // Fehler, wenn Datei fehlt
import java.io.File;                // Für Ordnerstrukturausgabe

/**
 * Die Klasse DateiIO ist für das Laden und Speichern
 * eines Entscheidungsbaums zuständig.
 * Der Baum besteht aus Objekten der Klasse Inhalt
 * und wird rekursiv aus einer Textdatei gelesen bzw. in
 * eine Textdatei geschrieben.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */

public class DateiIO implements IDateiIO
{    
    public DateiIO()
    { 
    }

    /**
     * Liest rekursiv einen TextAdventure-Baum aus einer Datei ein.
     * Die Methode verarbeitet dabei Zeile für Zeile den Inhalt des BufferedReaders
     * und erstellt abhängig vom gelesenen Block entweder ein Ereignis oder ein Ende.
     * Bei Ereignissen werden rekursiv der linke und rechte Teilbaum geladen.
     *
     * @param puffer BufferedReader zum Lesen der Datei
     * @param knoten Aktueller Knoten des Binärbaums, der befüllt werden soll
     */
    private void ladeRekursiv(BufferedReader puffer, BinaryTree<Inhalt> knoten) {
        try {
            String s;
            while ((s = puffer.readLine()) != null) {
                s = s.trim();
                if (s.isEmpty()) continue;
                
                // Ende
                if (s.startsWith("Ende {")) {
                    String schluesselwort = "", text = "", bild = "", sound = "";

                    while (!(s = puffer.readLine().trim()).equals("}")) {
                        if (s.startsWith("schluesselwort:")) schluesselwort = s.substring(13).trim();
                        else if (s.startsWith("text:")) text = s.substring(5).trim();
                        else if (s.startsWith("bild {")) {
                            StringBuilder bildBuilder = new StringBuilder();
                            while (!(s = puffer.readLine().trim()).equals("}")) {
                                bildBuilder.append(s).append("\n");
                            }
                            bild = bildBuilder.toString();
                        } else if (s.startsWith("sound:")) sound = s.substring(6).trim();
                    }

                    knoten.setContent(new Ende(schluesselwort, text, bild, sound));
                    return;
                }

                // Ereignis
                if (s.startsWith("Ereignis {")) {
                    String eSchluesselwort = "", eText = "", eBild = "", eSound = "";
                    String a1Text = "", a2Text = "";

                    while (!(s = puffer.readLine().trim()).equals("}")) {
                        if (s.startsWith("schluesselwort:")) eSchluesselwort = s.substring(13).trim();
                        else if (s.startsWith("text:")) eText = s.substring(5).trim();
                        else if (s.startsWith("bild {")) {
                            StringBuilder bildBuilder = new StringBuilder();
                            while (!(s = puffer.readLine().trim()).equals("}")) {
                                bildBuilder.append(s).append("\n");
                            }
                            eBild = bildBuilder.toString();
                        } else if (s.startsWith("sound:")) eSound = s.substring(6).trim();
                        else if (s.startsWith("AntwortEins {")) {
                            while (!(s = puffer.readLine().trim()).equals("}")) {
                                if (s.startsWith("text:")) a1Text = s.substring(5).trim();
                            }
                        } else if (s.startsWith("AntwortZwei {")) {
                            while (!(s = puffer.readLine().trim()).equals("}")) {
                                if (s.startsWith("text:")) a2Text = s.substring(5).trim();
                            }
                        }
                    }

                    Ereignis ereignis = new Ereignis(eSchluesselwort, eText, eBild, eSound, a1Text, a2Text);
                    knoten.setContent(ereignis);
                    knoten.setLeftTree(new BinaryTree<>());
                    knoten.setRightTree(new BinaryTree<>());

                    // Rekursion für Kinder
                    ladeRekursiv(puffer, knoten.getLeftTree());
                    ladeRekursiv(puffer, knoten.getRightTree());
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden: " + e.getMessage());
        }
    }

    /**
     * Lädt einen TextAdventure-Baum aus einer Datei.
     * Die Methode überprüft, ob das notwendige Interface implementiert ist,
     * öffnet die angegebene Datei und startet den rekursiven Ladevorgang.
     *
     * @param pDateiname Pfad zur zu ladenden Datei
     * @return Der geladene Binärbaum mit Inhalten
     * @throws TextAdventureException Wenn die Datei nicht gefunden wird,
     *         ein allgemeiner Fehler auftritt oder das Interface nicht implementiert ist
     */
    public BinaryTree<Inhalt> laden(String pDateiname) throws TextAdventureException {
        if (!(this instanceof IDateiIO)) {
            throw new TextAdventureException(FehlerCode.INTERFACE_NICHT_IMPLEMENTIERT);
        }

        BinaryTree<Inhalt> baum = new BinaryTree<>();
        try (BufferedReader puffer = new BufferedReader(new FileReader(pDateiname))) {
            ladeRekursiv(puffer, baum);
        } catch (FileNotFoundException e) {
            throw new TextAdventureException(FehlerCode.DATEI_NICHT_GEFUNDEN);
        } catch (IOException e) {
            throw new TextAdventureException(FehlerCode.ALLGEMEINER_FEHLER);
        }

        return baum;
    }

    /**
     * Speichert einen TextAdventure-Baum rekursiv in eine Datei.
     * Je nach Knotentyp wird entweder ein Ereignis oder ein Ende
     * in das entsprechende Textformat geschrieben.
     * Bei Ereignissen werden anschließend die linken und rechten Teilbäume gespeichert.
     *
     * @param writer Writer zum Schreiben in die Datei
     * @param knoten Aktueller Knoten des Binärbaums, der gespeichert werden soll
     */
    private void speichernRekursiv(Writer writer, BinaryTree<Inhalt> knoten) throws TextAdventureException {
        try {
            if (knoten.isEmpty()) return;

            if (knoten.getContent().istEnde()) {
                Ende ende = (Ende) knoten.getContent();
                writer.write("Ende {\n");
                writer.write("    schluesselwort: " + ende.getSchluesselwort() + "\n");
                writer.write("    text: " + ende.getText() + "\n");

                // Bild als Block
                writer.write("    bild {\n");
                writer.write(ende.getBild());
                writer.write("    }\n");

                writer.write("    sound: " + ende.getSound() + "\n");
                writer.write("}\n");
            } else {
                Ereignis ereignis = (Ereignis) knoten.getContent();
                writer.write("Ereignis {\n");
                writer.write("    schluesselwort: " + ereignis.getSchluesselwort() + "\n");
                writer.write("    text: " + ereignis.getText() + "\n");

                // Bild als Block
                writer.write("    bild {\n");
                writer.write(ereignis.getBild());
                writer.write("    }\n");

                writer.write("    sound: " + ereignis.getSound() + "\n");

                // Antworten direkt im Ereignisblock
                writer.write("AntwortEins {\n");
                writer.write("    text: " + ereignis.getErsteAntwort() + "\n");
                writer.write("}\n");

                writer.write("AntwortZwei {\n");
                writer.write("    text: " + ereignis.getZweiteAntwort() + "\n");
                writer.write("}\n");

                writer.write("}\n"); // Ereignis erst hier schließen

                // Rekursion für Kinder
                speichernRekursiv(writer, knoten.getLeftTree());
                speichernRekursiv(writer, knoten.getRightTree());
            }
        } catch (IOException e) {
            throw new TextAdventureException(FehlerCode.DATEIPFAD_NICHT_ANGEGEBEN); 
        }
    }

    /**
     * Speichert einen vollständigen TextAdventure-Baum in einer Datei.
     * Die Methode prüft alle notwendigen Vorbedingungen und startet
     * anschließend den rekursiven Speichervorgang.
     *
     * @param pBaum Der zu speichernde Binärbaum
     * @param pDateipfad Pfad zur Zieldatei
     * @throws TextAdventureException Wenn das Interface nicht implementiert ist,
     *         kein Dateipfad angegeben wurde oder der Baum leer ist
     */
    public void speichern(BinaryTree<Inhalt> pBaum, String pDateipfad) throws TextAdventureException {
        if (!(this instanceof IDateiIO)) {
            throw new TextAdventureException(FehlerCode.INTERFACE_NICHT_IMPLEMENTIERT);
        }
        if (pDateipfad.isEmpty()) {
            throw new TextAdventureException(FehlerCode.DATEIPFAD_NICHT_ANGEGEBEN);
        }
        if (pBaum.isEmpty()) {
            throw new TextAdventureException(FehlerCode.BAUM_LEER);
        }

        try (FileWriter writer = new FileWriter(pDateipfad)) {
            speichernRekursiv(writer, pBaum);
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (TextAdventureException e) {
            TextAdventureException.hantiereMitException(e);
        }
    }

    /**
     * Gibt alle Dateien in einem Ordner übersichtlich aus.
     *
     * @param ordnerPfad Pfad zum Ordner
     */
    public void ordnerUebersichtAusgeben(String ordnerPfad) {
        File ordner = new File(ordnerPfad);

        if (!ordner.exists() || !ordner.isDirectory()) {
            System.out.println("Ordner existiert nicht: " + ordnerPfad);
            return;
        }

        File[] dateien = ordner.listFiles();
        if (dateien == null || dateien.length == 0) {
            System.out.println("Keine Dateien im Ordner: " + ordnerPfad);
            return;
        }

        System.out.println("Dateien im Ordner '" + ordnerPfad + "':");
        for (File datei : dateien) {
            if (datei.isFile()) {
                System.out.println("  - " + datei.getName());
            } else if (datei.isDirectory()) {
                System.out.println("  [Ordner] " + datei.getName());
            }
        }
    }

    /**
     * Löscht eine Datei an einem gegebenen Pfad.
     *
     * @param dateiPfad Pfad zur Datei, die gelöscht werden soll
     */
    public void loescheDatei(String dateiPfad) {
        File datei = new File(dateiPfad);

        if (!datei.exists()) {
            System.out.println("Die Datei existiert nicht: " + dateiPfad);
            return;
        }

        if (datei.delete()) {
            System.out.println("Datei erfolgreich gelöscht: " + dateiPfad);
        } else {
            System.out.println("Die Datei konnte nicht gelöscht werden: " + dateiPfad);
        }
    }
}