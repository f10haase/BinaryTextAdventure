/**
 * Enthält alle möglichen Fehlercodes des Textadventures.
 * Jedem Fehlercode ist eine passende Fehlermeldung zugeordnet,
 * die zur einheitlichen Ausgabe von Fehlern verwendet wird.
 * Die Fehlercodes erleichtern die Fehlerbehandlung und
 * verbessern die Übersichtlichkeit im Programm
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */

public enum FehlerCode {
    ALLGEMEINER_FEHLER("Allgemeiner Fehler. Viel Glück!"),
    DATEI_NICHT_GEFUNDEN("Datei wurde nicht gefunden."),
    BAUM_LEER("Der Entscheidungsbaum ist leer. Keine Geschichte geladen."),
    INTERFACE_NICHT_IMPLEMENTIERT("Interface nicht implementiert."),
    DATEIPFAD_NICHT_ANGEGEBEN("Dateipfad nicht angegeben!"),
    FALSCHES_AUDIOFORMAT("Falsches Audioformat!"),
    FALSCHE_BENUTZEREINGABE("Falsche Nutzereingabe! Bitte Zahl eingeben! ");

    private final String nachricht;

    FehlerCode(String nachricht) {
        this.nachricht = nachricht;
    }

    public String getNachricht() {
        return "[" + this.getClass().getSimpleName() + "] " + nachricht;
    }
}