package BackEnd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> users;
    private String filename;
    private Gson gson;

    private static class TempUser {

        String userId;
        String username;
        String email;
        String passwordHash;
        String role;
        ArrayList<String> enrolledCourses;
        ArrayList<String> createdCourses;

    }

    public UserManager(String filename) {
        this.filename = filename;
        this.gson = new Gson();
        this.users = new ArrayList<>();
        readFromFile();
    }

    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {

            ArrayList<TempUser> temp = gson.fromJson(reader,
                    new TypeToken<ArrayList<TempUser>>() {
                    }.getType()
            );

            users = new ArrayList<>();

            if (temp != null) {
                for (TempUser u : temp) {

                    if ("student".equalsIgnoreCase(u.role)) {
                        Student s = new Student(u.userId, u.username, u.email, u.passwordHash);
                        if (u.enrolledCourses != null) {
                            s.setEnrolledCourses(u.enrolledCourses);
                        }
                        users.add(s);
                    } else if ("instructor".equalsIgnoreCase(u.role)) {
                        Instructor i = new Instructor(u.userId, u.username, u.email, u.passwordHash);
                        if (u.createdCourses != null) {
                            i.setCreatedCourses(u.createdCourses);
                        }
                        users.add(i);
                    }
                }
            }

        } catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filename)) {

            ArrayList<TempUser> x = new ArrayList<>();

            for (User u : users) {
                TempUser j = new TempUser();
                j.userId = u.getUserId();
                j.username = u.getUsername();
                j.email = u.getEmail();
                j.passwordHash = u.getPasswordHash();

                if (u instanceof Student) {
                    j.role = "student";
                    j.enrolledCourses = ((Student) u).getEnrolledCourses();
                } else if (u instanceof Instructor) {
                    j.role = "instructor";
                    j.createdCourses = ((Instructor) u).getCreatedCourses();
                }

                x.add(j);
            }

            gson.toJson(x, writer);

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

    public int contains(String ID) {
        int i = 0;
        for (User u : users) {
            if (u.getUserId().equals(ID)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void courseCreated(String courseID, String instructorID) {
        int i = contains(instructorID);
        ArrayList<String> createdCourses = ((Instructor) users.get(i)).getCreatedCourses();
        createdCourses.add(courseID);
        ((Instructor) users.get(i)).setCreatedCourses(createdCourses);
        saveToFile();
    }

    public void courseDeleted(String courseID, String instructorID) {
        int i = contains(instructorID);
        ArrayList<String> createdCourses = ((Instructor) users.get(i)).getCreatedCourses();
        int j = 0;
        for (String s : createdCourses) {
            if (s.equals(courseID)) {
                break;
            }
            j++;
        }
        createdCourses.remove(j);
        ((Instructor) users.get(i)).setCreatedCourses(createdCourses);
        saveToFile();
    }

    private boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User getUserFromEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public int login(String email, String password) throws Exception {
        String hashed = hashPassword(password);
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPasswordHash().equals(hashed)) {
                if (u.getRole().equals("Instructor")) {
                    return 1;
                } else {
                    return 2;
                }

            }
        }
        return -1;
    }

    public void logout() {
        saveToFile();
    }

    public ArrayList<User> getAllUsers() {
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

    public void studentEnrolled(String studentID, String courseID) {
        int i = 0;
        for (User u : users) {
            if (u instanceof Student) {
                Student s = (Student) u;
                if (s.getUserId().equals(studentID)) {
                    s.getEnrolledCourses().add(courseID);
                    break;
                }
            }

            i++;
        }
        saveToFile();
    }
}
