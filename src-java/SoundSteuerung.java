import javax.sound.sampled.*;     // Klassen zum Abspielen von WAV-Sounds
import java.io.File;              // Repräsentation von Dateien
import java.io.IOException;       // Fehlerbehandlung bei Ein- und Ausgabe

/**
 * Die Klasse SoundSteuerung dient zum Abspielen von Sounddateien.
 * Es werden ausschließlich WAV-Dateien unterstützt.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */
public class SoundSteuerung
{

    /**
     * Erzeugt ein neues Objekt der Klasse {@code SoundSteuerung}.
     */
    public SoundSteuerung()
    {

    }
    
    /**
     * Spielt eine WAV-Sounddatei ab.
     * Die Methode lädt die angegebene Datei, öffnet einen Audiostream
     * und spielt den Sound vollständig ab.
     *
     * @param dateipfad der Pfad zur abzuspielenden WAV-Sounddatei
     */
    public void spieleSound(String pDateiname) throws TextAdventureException
    {
        try {
            File soundDatei = new File(pDateiname);
            if (!soundDatei.exists()) {
                throw new TextAdventureException(FehlerCode.DATEI_NICHT_GEFUNDEN );
            }

            // AudioInputStream aus der Datei erzeugen
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundDatei);

            // Clip erzeugen, öffnen und starten
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Warten, bis der Sound vollständig abgespielt wurde
            Thread.sleep(clip.getMicrosecondLength() / 1000);

            // Ressourcen freigeben
            clip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException e) {
            throw new TextAdventureException(FehlerCode.FALSCHES_AUDIOFORMAT); 
        } catch (IOException e) {
            throw new TextAdventureException(FehlerCode.DATEI_NICHT_GEFUNDEN );
        } catch (LineUnavailableException e) {
            throw new TextAdventureException(FehlerCode.ALLGEMEINER_FEHLER);
        } catch (InterruptedException e) {
            throw new TextAdventureException(FehlerCode.ALLGEMEINER_FEHLER);
        }
    }
}
