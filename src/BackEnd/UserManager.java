package BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;

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
        Map<String, Map<String, Boolean>> lessonsProgress;
        Map<String, QuizAttempt> quizAttempts; 
        ArrayList<Certificate> certificates; 
    }

    public UserManager(String filename) {
        this.filename = filename;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
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
                        if (u.lessonsProgress != null) {
                            LessonsProgress lp = new LessonsProgress();
                            lp.setProgressMap(u.lessonsProgress);
                            s.setLessonsProgress(lp);
                        }  if (u.quizAttempts != null) {
                            s.setQuizAttempts(u.quizAttempts);
                        }if (u.certificates != null) {
                            s.setCertificates(u.certificates);
                        }
                        users.add(s);
                    } else if ("instructor".equalsIgnoreCase(u.role)) {
                        Instructor i = new Instructor(u.userId, u.username, u.email, u.passwordHash);
                        if (u.createdCourses != null) {
                            i.setCreatedCourses(u.createdCourses);
                        }
                        users.add(i);
                    }else{
                        Admin a = new Admin(u.userId, u.username, u.email, u.passwordHash);
                        users.add(a);
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
                    j.lessonsProgress = ((Student) u).getLessonsProgress().getProgressMap();
                     j.quizAttempts = ((Student) u).getQuizAttempts(); 
                     j.certificates = ((Student) u).getCertificates();   
                } else if (u instanceof Instructor) {
                    j.role = "instructor";
                    j.createdCourses = ((Instructor) u).getCreatedCourses();
                }else{
                    j.role = "Admin";
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

    public boolean addStudent(String userId, String username, String email, String password) throws Exception {
        if (emailExists(email) || IdExists(userId)) {
            return false;
        }
        Student s = new Student(userId, username, email, hashPassword(password));
        users.add(s);
        saveToFile();
        return true;
    }

    public boolean addInstructor(String userId, String username, String email, String password) throws Exception {
        if (emailExists(email) || IdExists(userId)) {
            return false;
        }
        Instructor i = new Instructor(userId, username, email, hashPassword(password));
        users.add(i);
        saveToFile();
        return true;
    }
    
    public boolean addAdmin(String userId, String username, String email, String password) throws Exception {
        if (emailExists(email) || IdExists(userId)) {
            return false;
        }
        Admin a = new Admin(userId, username, email, hashPassword(password));
        users.add(a);
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
        Instructor inst = (Instructor) users.get(i);
        ArrayList<String> createdCourses = inst.getCreatedCourses();

        int index = createdCourses.indexOf(courseID);

        if (index != -1) {
            createdCourses.remove(index);
        } else {
            System.out.println("Course not found in instructor's list");
            return;
        }
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

    private boolean IdExists(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
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

    public User getUserFromID(String ID) {
        for (User u : users) {
            if (u.getUserId().equals(ID)) {
                return u;
            }
        }
        return null;
    }

    public int login(String id, String password) throws Exception {
        String hashed = hashPassword(password);
        for (User u : users) {
            if (u.getUserId().equals(id) && u.getPasswordHash().equals(hashed)) {
                if (u.getRole().equals("Instructor")) {
                    return 1;
                }else if (u.getRole().equals("Student")){
                    return 2;
                }else{
                    return 3;
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

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        for (User user : users) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
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
