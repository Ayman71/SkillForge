package BackEnd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private List<User> users;
    private String filename;
    private Gson gson;

    public UserManager(String filename) {
        this.filename = filename;
        this.gson = new Gson();
        this.users = new ArrayList<>();
        readFromFile();
    }

    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(users, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean addStudent(String username, String email, String password) throws Exception {
        if (emailExists(email)) {
            return false;
        }

        String userId = "S" + (users.size() + 1);
        Student s = new Student(userId, username, email, hashPassword(password));
        users.add(s);
        saveToFile();
        return true;
    }

    public boolean addInstructor(String username, String email, String password) throws Exception {
        if (emailExists(email)) {
            return false;
        }

        String userId = "I" + (users.size() + 1);
        Instructor i = new Instructor(userId, username, email, hashPassword(password));
        users.add(i);
        saveToFile();
        return true;
    }

    private boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User login(String email, String password) throws Exception {
        String hashed = hashPassword(password);
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPasswordHash().equals(hashed)) {
                return u;
            }
        }
        return null;
    }

    public void logout() {
        saveToFile();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void deleteUser(String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.remove(i);
                break;
            }
        }
        saveToFile();
    }

    public void updateUser(String userId, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.set(i, updatedUser);
                saveToFile();
                return;
            }
        }
    }
}
