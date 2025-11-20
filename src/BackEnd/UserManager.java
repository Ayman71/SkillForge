package BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    public static UserManager getInstance() { return instance; }
    private String filename;
    private ArrayList<User> users;
    private Gson gson;
    public UserManager(String filename) {
        this.filename = filename;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.users = new ArrayList<>();
        readFromFile();
        instance = this;
    }
    private void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {}.getType());
            if (users == null) users = new ArrayList<>();
            rebuildUserObjects();
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
    private void rebuildUserObjects() {
        ArrayList<User> rebuilt = new ArrayList<>();
        for (User u : users) {
            switch (u.getRole()) {
                case "Student": {
                    Student s = new Student(
                            u.getUserId(),
                            u.getUsername(),
                            u.getEmail(),
                            u.getPasswordHash()
                    );
                    if (u instanceof Student) {
                        Student raw = (Student) u;
                        s.setEnrolledCourses(raw.getEnrolledCourses());
                        if (raw.getLessonsProgress() != null)
                            s.setLessonsProgress(raw.getLessonsProgress());
                        else
                            s.setLessonsProgress(new LessonsProgress());
                        Map<String, Map<String, java.util.List<Integer>>> attempts =
                                raw.getQuizAttempts();
                        s.setQuizAttempts(attempts != null ? attempts : new HashMap<>());
                    }
                    rebuilt.add(s);
                }
                break;
                case "Instructor": {
                    Instructor inst = new Instructor(
                            u.getUserId(),
                            u.getUsername(),
                            u.getEmail(),
                            u.getPasswordHash()
                    );
                    rebuilt.add(inst);
                }
                break;
                case "Admin": {
                    Admin admin = new Admin(
                            u.getUserId(),
                            u.getUsername(),
                            u.getEmail(),
                            u.getPasswordHash()
                    );
                    rebuilt.add(admin);
                }
                break;
                default:
                    rebuilt.add(u);
            }
        }
        users = rebuilt;
    }
    public User getUserFromID(String userId) {
        for (User u : users)
            if (u.getUserId().equals(userId)) return u;
        return null;
    }
    public User getUserFromEmail(String email) {
        for (User u : users)
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        return null;
    }
    public boolean emailExists(String email) {
        return getUserFromEmail(email) != null;
    }
    public boolean addUser(User user) {
        if (getUserFromID(user.getUserId()) != null) return false;
        users.add(user);
        saveToFile();
        return true;
    }
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        for (User u : users)
            if (u instanceof Student) list.add((Student) u);
        return list;
    }
    public ArrayList<Instructor> getAllInstructors() {
        ArrayList<Instructor> list = new ArrayList<>();
        for (User u : users)
            if (u instanceof Instructor) list.add((Instructor) u);
        return list;
    }
    public ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> list = new ArrayList<>();
        for (User u : users)
            if (u instanceof Admin) list.add((Admin) u);
        return list;
    }
    public ArrayList<User> getAllUsers() {
        return users;
    }
}
