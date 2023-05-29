package fr.mrcoq.lib.multilanguageapi.exception;

public class LangFileCantCreatedException extends Exception {

    public LangFileCantCreatedException() {
        super("Can't create lang file configuration.");
    }
}
