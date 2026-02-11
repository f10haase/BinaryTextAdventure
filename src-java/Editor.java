/**
 * Der Editor ermöglicht das Erstellen, Anzeigen, Speichern
 * und Löschen von Geschichten in Form eines Entscheidungsbaums.
 * Benutzerinteraktionen werden über Menüs gesteuert, die
 * Eingaben werden verarbeitet und die Geschichten grafisch
 * auf der Konsole dargestellt.
 *
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */

public class Editor
{
    private BinaryTree<Inhalt> baum; 

    private DateiIO dateiio; 
    private EingabeSteuerung eingabeSteuerung; 

    public Editor() {
        baum = new BinaryTree<>();
        dateiio = new DateiIO(); 
        eingabeSteuerung = new EingabeSteuerung(); 
    }

    /**
     * Zeigt das Hauptmenü des Editors an und verarbeitet Benutzereingaben,
     * bis der Spieler das Spiel beendet.
     */
    public void menue() {
        int eingabe = -1;
        while (eingabe != 0) {
            gibEditorMenueAus(); 
            eingabe = eingabeSteuerung.leseGanzzahl("Deine Wahl: ");
            verarbeiteEingabe(eingabe);    
        }   
    }

    /**
     * Zeigt das Menü für den Editor an.
     * 
     * Optionen:
     * 1 → Geschichte neu schreiben
     * 2 → Geschichte überarbeiten
     * 3 → Übersicht aller Geschichten
     * 4 → Geschichte löschen
     * 0 → Editor verlassen
     */
    private void gibEditorMenueAus() {
        String menue = "================================================\n" +
            "||     ***********************************    ||\n" +
            "||     *  B I N A R Y  A D V E N T U R E *    ||\n" +
            "||     ***********************************    ||\n" +
            "||        ******** E D I T O R ********       ||\n" +
            "||                                            ||\n" +
            "||  [1] Geschichte neu schreiben              ||\n" +
            "||  [2] Geschichte darstellen                 ||\n" +
            "||  [3] Übersicht aller Geschichten           ||\n" +
            "||  [4] Geschichte löschen                    ||\n" +
            "||                                            ||\n" +
            "||  [0] Editor verlassen                      ||\n" +
            "||                                            ||\n" +
            "================================================";
        System.out.println(menue);
    }
    
    /**
     * Gibt eine formatierte Überschrift für die Darstellung der Geschichte
     * auf der Konsole aus. 
     */
    
    private void gibUeberschriftDarstellung() { 
        String ueberschrift = 
            "||*****************************************||\n" +
            "||************** Darstellung **************||\n" +
            "||************ der Geschichte *************||\n" +
            "||*****************************************||\n";
        System.out.println(ueberschrift);
    }

    /**
     * Wertet die Eingabe des Spielers im Hauptmenü aus
     * und führt die entsprechende Aktion aus.
     *
     * Mögliche Eingaben: 
     * 1 → Geschichte neu schreiben
     * 2 → Geschichte überarbeiten
     * 3 → Übersicht aller Geschichten
     * 4 → Geschichte löschen
     * 0 → Editor verlassen     *
     * @param pEingabe Auswahl des Spielers
     */
    private void verarbeiteEingabe(int pEingabe) {
        switch (pEingabe) {
            case 1:
                BinaryTree<Inhalt> baum = new BinaryTree<Inhalt>();
                baum = geschichteErstellen();
                baumGrafischAusgeben(baum);
                String dateiname = eingabeSteuerung.leseString("Bitte gib einen Dateinnamen an!"); 
                String dateipfad = KONFIG.STANDARD_GESCHICHTEN_PFAD+dateiname; 
                speichern(baum, dateipfad); 
                break;
            case 2:
                geschichteDarstellen();
                break;
            case 3:
                dateiio.ordnerUebersichtAusgeben(KONFIG.STANDARD_GESCHICHTEN_PFAD);
                break;                
            case 4: 
                geschichteLoschen();
                break;
            case 0:
                System.out.println("Editor beendet!");
                return;  
            default:
                System.out.println("Ungültige Eingabe!");
        }
        System.out.println();
    }

    /**
     * Lädt eine gespeicherte Geschichte aus einer Datei
     * und speichert sie im Entscheidungsbaum.
     *
     * @param pDateiname Name der Quelldatei
     */
    public BinaryTree<Inhalt> laden(String pDateiname) {
        BinaryTree<Inhalt> baum = new BinaryTree<Inhalt>();
        try {
            String dateipfad = KONFIG.STANDARD_GESCHICHTEN_PFAD+pDateiname; 
            baum = dateiio.laden(dateipfad);  
            System.out.println("Geschichte geladen.");
        } catch (TextAdventureException e) {
            TextAdventureException.hantiereMitException(e);
        } 

        return baum; 
    }    

    /**
     * Speichert die aktuelle Geschichte in einer Datei.
     *
     * @param pDateiname Name der Zieldatei
     */
    private void speichern(BinaryTree<Inhalt> pBaum, String pDateiname) {
        String eingabe = eingabeSteuerung.leseString("Möchtest du die Geschichte speichern? j/n"); 
        if(eingabe.equals("j")) {
            try {
                dateiio.speichern(pBaum, pDateiname); 
                System.out.println("Geschichte gespeichert.");
            } catch (TextAdventureException e) {
                TextAdventureException.hantiereMitException(e);
            }          
        } else {
            System.out.println("Geschichte nicht gespeichert.");    
        }                  
    }
    
    /**
     * Ermöglicht dem Benutzer die Auswahl einer gespeicherten Geschichte
     * und stellt diese anschließend grafisch als Baum dar.
     * Dazu wird eine Übersicht der verfügbaren Geschichten angezeigt,
     * die gewünschte Datei geladen und der Baum ausgegeben.
     */
    
    private void geschichteDarstellen() {
        BinaryTree<Inhalt> baum = new BinaryTree<>();
        System.out.println(); 
        dateiio.ordnerUebersichtAusgeben(KONFIG.STANDARD_GESCHICHTEN_PFAD);
        System.out.println(); 
        String dateiname = eingabeSteuerung.leseString("Gib den Dateinamen der Geschichte ein, die du darstellen möchtest."); 
        String dateipfad = KONFIG.STANDARD_GESCHICHTEN_PFAD+dateiname; 
        System.out.println();
        try {
            baum = dateiio.laden(dateipfad);    
        } catch(TextAdventureException e) {
            TextAdventureException.hantiereMitException(e);
        }
        
        baumGrafischAusgeben(baum);
    }
    
    /**
     * Gibt einen Binärbaum grafisch auf der Konsole aus.
     * Vor der eigentlichen Ausgabe wird eine Überschrift angezeigt.
     * Die Darstellung des Baums erfolgt rekursiv.
     *
     * @param pBaum Der darzustellende Binärbaum
     */
    private void baumGrafischAusgeben(BinaryTree<Inhalt> pBaum) {
        System.out.println(); 
        gibUeberschriftDarstellung();
        baumGrafischAusgebenRekursiv(pBaum, "", false);     
        System.out.println(); 
    }
    
    /**
     * Gibt einen Binärbaum rekursiv in einer grafischen Baumstruktur
     * auf der Konsole aus. Dabei werden Einrückungen und Verzweigungen
     * genutzt, um die Struktur der Geschichte übersichtlich darzustellen.
     * Ereignisse zeigen zusätzlich ihre Antwortmöglichkeiten an.
     *
     * @param knoten Aktueller Knoten des Binärbaums
     * @param prefix Zeichenkette zur Einrückung der aktuellen Baumebene
     * @param istLinkesKind Gibt an, ob der aktuelle Knoten ein linkes Kind ist
     */
    private void baumGrafischAusgebenRekursiv(BinaryTree<Inhalt> knoten, String prefix, boolean istLinkerBaum) {
        if (knoten == null || knoten.isEmpty()) {
            System.out.println(FehlerCode.BAUM_LEER.getNachricht());
        } else {
            Inhalt inhalt = knoten.getContent();
            String name = FehlerCode.ALLGEMEINER_FEHLER.getNachricht();

            if (inhalt.istEnde()) {
                name = "Ende: " + ((Ende) inhalt).getSchluesselwort();
            } else {
                name = "Ereignis: " + ((Ereignis) inhalt).getSchluesselwort();
            }
            System.out.println(prefix + (istLinkerBaum ? "├─ " : "└─ ") + name);
            if (!inhalt.istEnde()) {
                Ereignis ereignis = (Ereignis) inhalt;
                
                //linker Baum 
                System.out.println(prefix + (istLinkerBaum ? "│  " : "   ") + "AntwortEins: " + ereignis.getErsteAntwort());
                baumGrafischAusgebenRekursiv(knoten.getLeftTree(), prefix + (istLinkerBaum ? "│  " : "   "), true);

                //rechter Baum
                System.out.println(prefix + (istLinkerBaum ? "│  " : "   ") + "AntwortZwei: " + ereignis.getZweiteAntwort());
                baumGrafischAusgebenRekursiv(knoten.getRightTree(), prefix + (istLinkerBaum ? "│  " : "   "), false);
            }
        }
    }
    
    /**
     * Startet die Erstellung einer neuen Geschichte.
     * Die Methode ruft die rekursive Knotenerstellung auf
     * und gibt den daraus entstehenden Binärbaum zurück.
     *
     * @return Der neu erstellte Binärbaum mit Inhalten der Geschichte
     */
    private BinaryTree<Inhalt> geschichteErstellen() {
        return erstelleKnotenRekursiv(null, "");
    }
    

    /**
     * Erstellt rekursiv einen Knoten (Ereignis oder Ende) und ggf. seine Kinder.
     *
     * @param parent   der übergeordnete Knoten (nur für Anzeige, kann null sein)
     * @param richtung Angabe "AntwortEins" oder "AntwortZwei" für die Anzeige
     * @return erstellter BinaryTree<Inhalt>
     */
    private BinaryTree<Inhalt> erstelleKnotenRekursiv(BinaryTree<Inhalt> parent, String richtung) {
        BinaryTree<Inhalt> knoten = new BinaryTree<>();
        String info = (parent == null) ? "Wurzelknoten" : "Teilbaum für " + richtung;
        System.out.println("Erstelle " + info + ":");

        int eingabe = -1;
        while (eingabe != 1 && eingabe != 2) {
            System.out.println("(1) Ereignis anlegen\n(2) Ende anlegen");
            eingabe = eingabeSteuerung.leseGanzzahl("Deine Wahl: ");
            switch (eingabe) {
                case 1:
                    Ereignis ereignis = ereignisAnlegen();
                    knoten.setContent(ereignis);
                    // Leere Teilbäume vorbereiten
                    knoten.setLeftTree(new BinaryTree<>());
                    knoten.setRightTree(new BinaryTree<>());
                    // Rekursion für linken und rechten Teilbaum
                    knoten.setLeftTree(erstelleKnotenRekursiv(knoten, "AntwortEins"));
                    knoten.setRightTree(erstelleKnotenRekursiv(knoten, "AntwortZwei"));
                    break;

                case 2:
                    Ende ende = endeAnlegen();
                    knoten.setContent(ende);
                    // Ende ist Blatt, keine weiteren Teilbäume
                    break;

                default:
                    System.out.println("Ungültige Eingabe!");
            }
        }
        return knoten;
    }
    /**
     * Legt ein neues Ereignis für eine Geschichte an.
     * Der Benutzer gibt über die Eingabesteuerung alle notwendigen
     * Informationen wie Schlüsselwort, Text, Bild, Sound und
     * die beiden Antwortmöglichkeiten ein.
     *
     * @return Das neu erstellte Ereignis
     */
    private Ereignis ereignisAnlegen() {
        String schluesselwort = "Nicht gesetzt!"; 
        String text = "Nicht gesetzt!"; 
        String bild = "Nicht gesetzt!"; 
        String sound = "Nicht gesetzt!"; 
        String antwortEins = "Nicht gesetzt!"; 
        String antwortZwei = "Nicht gesetzt!"; 

        schluesselwort = eingabeSteuerung.leseString("Bitte gib ein Schlüsselwort (maximal 3 Wörter) ein:"); 
        text = eingabeSteuerung.leseString("Bitte gib den Erzählungstext (Zeilenumbrüche machst du mit \\\\) zu dem Ereignis ein:"); 
        bild = eingabeSteuerung.leseString("Bitte gib den Dateinamen des Bildes ein:"); 
        sound = eingabeSteuerung.leseString("Bitte gib den Dateinamen des Sounds ein:"); 
        antwortEins = eingabeSteuerung.leseString("Bitte gib den Erzählungstext der ersten Entscheidung (maximal 3 Worte) zu dem Ereignis ein:"); 
        antwortZwei = eingabeSteuerung.leseString("Bitte gib den Erzählungstext der zweiten Entscheidung (maximal 3 Worte) zu dem Ereignis ein:"); 

        Ereignis ereignis = new Ereignis(schluesselwort, text, bild, sound, antwortEins, antwortZwei); 
        return ereignis;
    } 

    /**
     * Legt ein neues Ende für eine Geschichte an.
     * Der Benutzer gibt über die Eingabesteuerung die benötigten
     * Informationen wie Schlüsselwort, Text, Bild und Sound ein.
     *
     * @return Das neu erstellte Ende
     */
    private Ende endeAnlegen() {
        String schluesselwort = "Nicht gesetzt!"; 
        String text = "Nicht gesetzt!"; 
        String bild = "Nicht gesetzt!"; 
        String sound = "Nicht gesetzt!"; 

        schluesselwort = eingabeSteuerung.leseString("Bitte gib ein Schlüsselwort (maximal 3 Wörter) ein:"); 
        text = eingabeSteuerung.leseString("Bitte gib den Erzählungstext (Zeilenumbrüche machst du mit \\\\) zu dem Ende ein:"); 
        bild = eingabeSteuerung.leseString("Bitte gib den Dateinamen des Bildes ein:"); 
        sound = eingabeSteuerung.leseString("Bitte gib den Dateinamen des Sounds ein:"); 

        Ende ende = new Ende(schluesselwort, text, bild, sound); 
        return ende;
    } 
    
    /**
     * Löscht eine gespeicherte Geschichte nach einer Sicherheitsabfrage.
     * Zuerst wird eine Übersicht der vorhandenen Geschichten angezeigt,
     * anschließend die ausgewählte Geschichte geladen und grafisch dargestellt.
     * Die Datei wird nur gelöscht, wenn die Sicherheitsabfrage korrekt bestätigt wird.
     */
    private void geschichteLoschen() {
        BinaryTree<Inhalt> baum = new BinaryTree<>();
        System.out.println(); 
        dateiio.ordnerUebersichtAusgeben(KONFIG.STANDARD_GESCHICHTEN_PFAD);
        System.out.println(); 
        String dateiname = eingabeSteuerung.leseString("Gib den Dateinamen der Geschichte ein, die du darstellen möchtest."); 
        String dateipfad = KONFIG.STANDARD_GESCHICHTEN_PFAD+dateiname; 
        System.out.println();
        try {
            baum = dateiio.laden(dateipfad);    
        } catch(TextAdventureException e) {
            TextAdventureException.hantiereMitException(e);
        }
        
        baumGrafischAusgeben(baum);   
        
        System.out.println(); 
        String loeschPhrase = "loesche" + dateiname; 
        System.out.println("======ACHTUNG======ACHTUNG======ACHTUNG======ACHTUNG======ACHTUNG======ACHTUNG======ACHTUNG======ACHTUNG======");  
        String sicherheitsEingabe = eingabeSteuerung.leseString("Möchtest du die Geschichte " + dateiname + " wirklich löschen?  Tippe " + loeschPhrase + " ein!");
        System.out.println(); 
        if(sicherheitsEingabe.equals(loeschPhrase)) {
            dateiio.loescheDatei(dateipfad);
            System.out.println("Geschichte gelöscht!");      
        } else {
            System.out.println("Geschichte nicht gelöscht!");     
        }
        System.out.println();    
        dateiio.ordnerUebersichtAusgeben(KONFIG.STANDARD_GESCHICHTEN_PFAD);
        System.out.println();   
    }
}