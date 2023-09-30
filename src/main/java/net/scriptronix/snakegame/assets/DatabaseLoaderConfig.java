package net.scriptronix.snakegame.assets;

/**
 * Struct for database loader configuration.
 */
public class DatabaseLoaderConfig {

    final String url;
    final String username;
    final String password;

    /**
     * Construct a new DataBaseLoader Config
     *
     * @param url
     * @param username
     * @param password
     */
    public DatabaseLoaderConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
