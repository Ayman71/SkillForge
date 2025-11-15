/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Info;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mariam
 */
public class LoginInfo{
    File file = new File("users.txt");
    Scanner scanner;
    ArrayList<String> userNames = new ArrayList<String>();
    ArrayList<String> passwords = new ArrayList<String>();

    public LoginInfo() throws FileNotFoundException {
        this.scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(",");
            userNames.add(tokens[0]);
            passwords.add(tokens[1]);
        }
    }
    
    public boolean validateLogin(String userName, String password){
        for (int i = 0; i < userNames.size(); i++) {
            if(userName.equals(userNames.get(i)) && password.equals(passwords.get(i))){
                return true;
            }
        }
        return false;
    }
    public boolean registerUser(String username, String email, String password, String role) throws IOException {
    // Open the file users.txt or users.json whichever you are using
    FileWriter writer = new FileWriter("users.txt", true);  // append mode

    writer.write(username + "," + email + "," + password + "," + role + "\n");
    writer.close();

    return true;
}

    
    

}
