/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Info;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mariam
 */
public class LoginInfo{
    File file = new File("Admins.txt");
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
    
    
    

}
