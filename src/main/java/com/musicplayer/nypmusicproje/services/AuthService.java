package com.musicplayer.nypmusicproje.services;

import com.musicplayer.nypmusicproje.models.Playlist;
import com.musicplayer.nypmusicproje.models.Song;
import com.musicplayer.nypmusicproje.models.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * AuthService sınıfı, kullanıcı giriş/kayıt işlemlerini yöneten servis katmanıdır.
 * Singleton tasarım deseni kullanılarak uygulama genelinde tek bir örnek üzerinden çalışır.
 * Kullanıcı verileri JSON dosyası üzerinden kalıcı olarak saklanır.
 */
public class AuthService {

    private static AuthService instance;
    private static final String USERS_FILE = "users.json";

    private List<User> users;
    private User currentUser;
    private Gson gson;

    // Private constructor - Singleton
    private AuthService() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

        // Song nesnesi için custom deserializer
        builder.registerTypeAdapter(Song.class, new SongDeserializer());

        // ObservableList<Song> için custom deserializer
        builder.registerTypeAdapter(
                new TypeToken<ObservableList<Song>>() {}.getType(),
                new ObservableListDeserializer<>()
        );

        gson = builder.create();
        users = new ArrayList<>();
        loadUsers();
    }

    // Singleton instance erişimi
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    /**
     * Song nesnelerinin JSON'dan doğru şekilde oluşturulmasını sağlar.
     */
    private static class SongDeserializer implements JsonDeserializer<Song> {
        @Override
        public Song deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) {
            JsonObject obj = json.getAsJsonObject();

            return new Song(
                    obj.has("title") ? obj.get("title").getAsString() : "",
                    obj.has("artist") ? obj.get("artist").getAsString() : "",
                    obj.has("album") ? obj.get("album").getAsString() : "",
                    obj.has("duration") ? obj.get("duration").getAsString() : "",
                    obj.has("filePath") ? obj.get("filePath").getAsString() : ""
            );
        }
    }

    /**
     * JSON'daki listelerin ObservableList olarak deserialize edilmesini sağlar.
     */
    private static class ObservableListDeserializer<T>
            implements JsonDeserializer<ObservableList<T>> {

        @Override
        public ObservableList<T> deserialize(JsonElement json, Type typeOfT,
                                             JsonDeserializationContext context) {
            List<T> list = new ArrayList<>();

            if (json != null && json.isJsonArray()) {
                for (JsonElement element : json.getAsJsonArray()) {
                    @SuppressWarnings("unchecked")
                    T item = (T) context.deserialize(element, Song.class);
                    list.add(item);
                }
            }
            return FXCollections.observableArrayList(list);
        }
    }

    // JSON dosyasından kullanıcıları yükler
    private void loadUsers() {
        File file = new File(USERS_FILE);

        if (!file.exists() || file.length() == 0) {
            createDefaultUser();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            users = gson.fromJson(reader, listType);

            if (users == null || users.isEmpty()) {
                createDefaultUser();
            }
        } catch (Exception e) {
            users = new ArrayList<>();
            createDefaultUser();
        }
    }

    // Kullanıcıları JSON dosyasına kaydeder
    private void saveUsers() {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException ignored) {}
    }

    // Varsayılan kullanıcı oluşturur
    private void createDefaultUser() {
        User defaultUser = new User("admin", "admin123");
        defaultUser.addPlaylist("Favorilerim");
        users.add(defaultUser);
        saveUsers();
    }

    // Giriş işlemi
    public boolean login(String username, String password) {
        return login(username, password, true);
    }

    // Giriş işlemi (OVERLOADING)
    public boolean login(String username, String password, boolean verbose) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    // Kayıt işlemi
    public boolean register(String username, String password) {
        return register(username, password, true);
    }

    // Kayıt işlemi (OVERLOADING)
    public boolean register(String username, String password,
                            boolean createDefaultPlaylist) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        User newUser = new User(username, password);
        if (createDefaultPlaylist) {
            newUser.addPlaylist("Favorilerim");
        }

        users.add(newUser);
        saveUsers();
        currentUser = newUser;
        return true;
    }

    // Çıkış işlemi
    public void logout() {
        if (currentUser != null) {
            saveUsers();
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void updateCurrentUser() {
        if (currentUser != null) {
            saveUsers();
        }
    }
}
