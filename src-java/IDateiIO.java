/**
 * Das Interface IDateiIO definiert Methoden zum
 * Speichern und Laden eines BinaryTrees mit Objekten
 * vom Typ Inhalt.
 * 
 * @author f10haase@GitHub
 * @version 0.8-2026-1-14
 */
public interface IDateiIO
{
    /**
     * Speichert den übergebenen Binärbaum persistent,
     * in einer Datei.
     *
     * @param pBaum der zu speichernde BinaryTree
     * @param pDateipfad Datepfad der zu speichernden Datei
     */
    public void speichern(BinaryTree<Inhalt> pBaum, String pDateipfad) throws TextAdventureException; 
        
    /**
     * Lädt einen zuvor gespeicherten Binärbaum aus einer Datei.
     *
     * @param pDateiname Name der Datei, aus der geladen werden soll
     * @return der geladene BinaryTree mit Inhalt-Objekten
     */
    public BinaryTree<Inhalt> laden(String pDateiname) throws TextAdventureException; 
}
