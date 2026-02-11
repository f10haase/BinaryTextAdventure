public class TextAdventureException extends Exception {
    private final FehlerCode code;

    public TextAdventureException(FehlerCode code) {
        super(code.getNachricht());
        this.code = code;
    }

    private FehlerCode getCode() {
        return code;
    }
    
    public static void hantiereMitException(TextAdventureException e) {
        System.out.println(e.getCode().getNachricht());
    }
}