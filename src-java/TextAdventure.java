
/**
 * Die Klasse TextAdventure implementiert ein textbasiertes Adventure-Spiel.
 * Die Geschichte wird als binärer Baum gespeichert, wobei Ereignisse
 * Entscheidungsknoten und Enden Blattknoten darstellen.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */
public class TextAdventure
{
    private BinaryTree<Inhalt> baum; 

    private EingabeSteuerung eingabeSteuerung; 
    private SoundSteuerung soundsteuerung; 
    private Editor editor; 

    /**
     * Konstruktor der Klasse TextAdventure.
     * Initialisiert den Entscheidungsbaum und startet das Hauptmenü.
     */
    public TextAdventure() {
        baum = new BinaryTree<>();
        soundsteuerung = new SoundSteuerung();
        eingabeSteuerung = new EingabeSteuerung(); 
        editor = new Editor(); 
        menue(); 
    }

    /**
     * Führt das Spiel rekursiv durch, indem der Entscheidungsbaum durchlaufen wird.
     * 
     * Zeigt Bild und Text des aktuellen Knotens an und fragt bei Ereignissen nach
     * der Spielerentscheidung. Bei Enden wird das Spiel beendet, bei Ereignissen
     * erfolgt die Rekursion zu den gewählten Teilbäumen.
     *
     * @param pBaum aktueller Knoten im Entscheidungsbaum
     */

    private void spielen(BinaryTree<Inhalt> pBaum) throws TextAdventureException {
        if (pBaum.isEmpty()) {
            throw new TextAdventureException(FehlerCode.BAUM_LEER); 
        }
        
        System.out.println();
        System.out.println();
        System.out.println(pBaum.getContent().getBild() + "\n");
        
        String soundDateiName = pBaum.getContent().getSound(); 
        if(!soundDateiName.equals("")) {
             try {
                String soundDateiPfad = KONFIG.STANDARD_SOUND_PFAD + soundDateiName; 
                soundsteuerung.spieleSound(soundDateiPfad);         
            } catch (TextAdventureException e) {
                throw e; 
            }    
        }
        
        schreibmaschinenEffekt(pBaum.getContent().getText() + "\n");
       
        if (pBaum.getContent().istEnde()) {
            System.out.println("Ende erreicht!");
        } else {
            Ereignis ereignis = (Ereignis) pBaum.getContent(); 
            schreibmaschinenEffekt("Wahl 1: " + ereignis.getErsteAntwort()+ "\n"); 
            schreibmaschinenEffekt("Wahl 2: " + ereignis.getZweiteAntwort()+ "\n"); 
            int eingabe = eingabeSteuerung.leseGanzzahl("Deine Wahl: ");
            switch (eingabe) {
                case 1: spielen(pBaum.getLeftTree()); break; 
                case 2: spielen(pBaum.getRightTree()); break;
                case 0: beendeSpiel();
                default: System.out.println(FehlerCode.FALSCHE_BENUTZEREINGABE.getNachricht()); 
            }
        }
        System.out.println();
    }

    /**
     * Zeigt das Hauptmenü an und verarbeitet Benutzereingaben,
     * bis der Spieler das Spiel beendet.
     */
    private void menue() {
        int eingabe = -1;
        while (eingabe != 0) {
            gibMenueAus(); 
            eingabe = eingabeSteuerung.leseGanzzahl("Deine Wahl: ");
            verarbeiteEingabe(eingabe);    
        }   
    }

    /**
     * Erstellt und liefert den Menütext als String.
     *
     * @return formatierter Menütext
     */
    private void gibMenueAus() {
        String menue = "================================================\n" +
            "||     ***********************************    ||\n" +
            "||     *  B I N A R Y  A D V E N T U R E *    ||\n" +
            "||     ***********************************    ||\n" +
            "||                                            ||\n" +
            "||  [1] Spiel starten                         ||\n" +
            "||  [2] Geschichte laden                      ||\n" +
            "||  [3] Editor                                ||\n" +
            "||                                            ||\n" +
            "||  [0] Spiel beenden                         ||\n" +
            "||                                            ||\n" +
            "================================================";
        System.out.println(menue);
    }

    /**
     * Wertet die Eingabe des Spielers im Hauptmenü aus
     * und führt die entsprechende Aktion aus.
     *
     * Mögliche Eingaben:
     * 0 → Spiel beenden
     * 1 → Spiel starten
     * 2 → Geschichte laden
     * 3 → Editor öffnen
     * andere Zahlen → Fehlermeldung
     *
     * @param pEingabe Auswahl des Spielers
     */
    private void verarbeiteEingabe(int pEingabe) {
        switch (pEingabe) {
            case 1:
                try {
                    spielen(baum); 
                } catch (TextAdventureException e) {
                    TextAdventureException.hantiereMitException(e);
                }                 
                break;

            case 2:
                baum = editor.laden("geschichte1");
                break;

            case 3:
                editor.menue();
                break;

            case 0:
                beendeSpiel();
                break;

            default:
                System.out.println("Ungültige Eingabe!");
        }
        System.out.println();
    }

    /**
     * Gibt einen Text zeichenweise mit einer kleinen Verzögerung aus,
     * um einen Schreibmaschinen-Effekt zu erzeugen.
     *
     * @param pText der auszugebende Text
     */
    private void schreibmaschinenEffekt (String pText) {
        for(int i = 0; i < pText.length(); i++){
            char zeichen = pText.charAt(i);

            /* \ ist in Java ein "Escape-Zeichen"; 
             * da wir aber das Zeichen \ als char haben wollen, müssen wir \\ verwenden
             * für Java ist also \\ EIN character
             */
            if(zeichen == '\\') { 
                System.out.println(); 
            } else {
                System.out.print(zeichen);  
            }

            try{
                Thread.sleep(25*KONFIG.SCHREIBMASCHINE_GESCHWINDIGKEIT);
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Liefert den aktuellen Entscheidungsbaum zurück.
     * Nützlich z.B. für GUI-Komponenten oder Tests.
     *
     * @return der Wurzelknoten des Entscheidungsbaums
     */
    public BinaryTree<Inhalt> getBaum() throws TextAdventureException {
        if(baum.isEmpty()) {
            throw new TextAdventureException(FehlerCode.BAUM_LEER);
        } else {
            return baum;   
        }        
    }
    
    /**
     * Beendet das Spiel.
     * Gibt eine entsprechende Meldung auf der Konsole aus
     * und beendet anschließend den aktuellen Programmablauf.
     */
    private void beendeSpiel() {
        System.out.println("Spiel beendet!");
        System.out.println();
        System.out.println("Danke fürs Spielen ! BinaryAdventure made by " + KONFIG.AUTOR + " / Version: " + KONFIG.VERSION);
        return;    
    }
}
