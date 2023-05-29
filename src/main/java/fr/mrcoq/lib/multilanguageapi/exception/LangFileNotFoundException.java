package fr.mrcoq.lib.multilanguageapi.exception;

import fr.mrcoq.lib.multilanguageapi.Lang;

public class LangFileNotFoundException extends Exception {
    public LangFileNotFoundException(Lang lang) {
        super("No language file found for " + lang.getConfigName() + " (" + String.join(", ", lang.getLocals()) + ")");
    }
}
