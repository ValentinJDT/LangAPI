package fr.mrcoq.lib.multilanguageapi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Lang {
    ENGLISH(List.of("en")),
    SPANISH(List.of("es")),
    FRENCH(List.of("fr")),
    ITALIAN(List.of("it")),
    GERMAN(List.of("de")),
    CHINESE(List.of("zh")),
    ARABIC(List.of("ar"));

    private List<String> locals;

    Lang(List<String> locals) {
        this.locals = locals;

        if(locals.isEmpty()) {
            throw new NullPointerException("Locals can't be null or empty.");
        }
    }

    /**
     * Get name from {@link #getConfigName()}
     * @param name
     * @return
     */
    public static Lang getFromName(String name) {
        return Lang.valueOf(name.toUpperCase());
    }

    /**
     * Get locals from {@link #getLocals()}
     * @param local
     * @return
     */
    public static Lang getFromLocal(String local) {

        Optional<Lang> optionalLang =  Arrays.stream(Lang.values()).filter(lang -> lang.getLocals().contains(local)).findFirst();

        if(optionalLang.isPresent()) {
            return optionalLang.get();
        } else {
            return Lang.ENGLISH;
        }
    }

    public List<String> getLocals() {
        return this.locals;
    }

    public String getErrorInformation() {
        return getConfigName() + " (" + String.join(", ", getLocals()) + ")";
    }

    /**
     * Get name to use into {@link #getFromName(String)}
     * @return
     */
    public String getConfigName() {
        return this.name();
    }
}
