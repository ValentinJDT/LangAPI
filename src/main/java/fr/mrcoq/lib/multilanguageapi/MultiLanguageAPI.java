package fr.mrcoq.lib.multilanguageapi;

import fr.mrcoq.lib.multilanguageapi.exception.LangFileCantCreatedException;
import fr.mrcoq.lib.multilanguageapi.exception.LangFileNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class MultiLanguageAPI {

    private static File globalLangFolder = new File("./plugins/MultiLanguageAPI/");
    private static File globalLangFile = new File(globalLangFolder, "config.yml");

    static {
        if(!globalLangFolder.exists())
            globalLangFolder.mkdirs();

        if(!globalLangFile.exists()) {
            try {

                if(globalLangFile.createNewFile()) {
                    Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(globalLangFile, true), "UTF-8"));

                    List<String> list = Arrays.stream(Lang.values()).map((lang -> String.join("/", lang.getLocals()))).collect(Collectors.toList());

                    writer.append("# Languages allowed : ").append(String.join(", ", list)).append("\n");
                    writer.append("lang.default: ").append(Lang.ENGLISH.getLocals().stream().findFirst().get());
                    writer.close();
                }

            } catch(IOException e) {
                try {
                    throw new LangFileCantCreatedException();
                } catch(LangFileCantCreatedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private final Plugin plugin;
    private final File langFolder;
    private final boolean generateLangFiles;

    public MultiLanguageAPI(Plugin plugin) {
        this.plugin = plugin;
        this.langFolder = new File(this.plugin.getDataFolder(), "langs");
        this.generateLangFiles = false;

        init();
    }

    public MultiLanguageAPI(Plugin plugin, boolean generateLangFiles) {
        this.plugin = plugin;
        this.langFolder = new File(this.plugin.getDataFolder(), "langs");
        this.generateLangFiles = generateLangFiles;

        init();
    }

    /**
     * Create LangAPI directory in plugin datadir
     */
    private void init() {
        if(!langFolder.exists())
            langFolder.mkdirs();

        if(!generateLangFiles) return;

        for(Lang lang : Lang.values()) {
            lang.getLocals().forEach((local) -> {
                try {
                    new File(langFolder, local + ".yml").createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Get string from player and path.
     * @param player
     * @param path
     * @return
     * @throws LangFileNotFoundException
     */
    public String getString(Player player, String path) throws LangFileNotFoundException {
        Lang lang = getPlayerLang(player);

        return getString(lang, path);
    }

    public FileConfiguration getConfig(Lang lang) throws LangFileNotFoundException {
        Optional<File> optionalFile = Arrays.stream(langFolder.listFiles()).filter((file) -> lang.getLocals().stream().anyMatch(local -> file.getName().startsWith(local))).findFirst();

        FileConfiguration config = null;

        if(optionalFile.isPresent()) {
            config = YamlConfiguration.loadConfiguration(optionalFile.get());
        } else {

            Lang defaultLang = getDefaultLang();

            optionalFile = Arrays.stream(langFolder.listFiles()).filter((file) -> defaultLang.getLocals().stream().anyMatch(local -> file.getName().startsWith(local))).findFirst();

            if(optionalFile.isPresent()) {
                config = YamlConfiguration.loadConfiguration(optionalFile.get());
            }
        }

        if(config == null) {
            throw new LangFileNotFoundException(lang);
        }

        return config;
    }

    /**
     * Get string from lang and path.
     * @param lang
     * @param path
     * @return
     * @throws LangFileNotFoundException
     */
    public String getString(Lang lang, String path) throws LangFileNotFoundException {
        String value = getConfig(lang).getString(path);

        if(value == null) {
            String err = "Missing translation value on `" + path + "` in " + lang.getErrorInformation() + ".";
            plugin.getLogger().severe(err);
            value = "§4" + err + ". Please contact admin.";
        }

        return value;
    }

    /**
     * Fix local value latency when {@link org.bukkit.event.player.PlayerJoinEvent} is called.
     * @param player
     * @param path
     * @param consumer
     */
    public void getStringOnJoin(Player player, String path, Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                consumer.accept(getString(player, path));
            } catch(LangFileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, 5L);
    }

    /**
     * Fix local value latency when {@link org.bukkit.event.player.PlayerJoinEvent} is called.
     * @param lang
     * @param path
     * @param consumer
     */
    public void getStringOnJoin(Lang lang, String path, Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                consumer.accept(getString(lang, path));
            } catch(LangFileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, 5L);
    }

    /**
     * Get the player language.
     * @param player
     * @return
     */
    public static Lang getPlayerLang(Player player) {
        String local = player.getLocale().split("_")[0];

        return Lang.getFromLocal(local);
    }

    /**
     * Obviously, get the default language.
     * By default, is english.
     * @return
     */
    public static Lang getDefaultLang() {
        if(!globalLangFile.exists()) {
            return Lang.ENGLISH;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(globalLangFile);
        String defaultLang = config.getString("lang.default");

        return Lang.getFromLocal(defaultLang);
    }
}
