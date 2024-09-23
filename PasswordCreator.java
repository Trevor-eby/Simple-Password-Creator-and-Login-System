import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class PasswordCreator {
    public static void main(String args[]) {

        Scanner scnr = new Scanner(System.in);
        String userInput = "";

        while (true) {
            mainMenu();
            userInput = scnr.nextLine();
            if (userInput.equals("1")) {
                signIn();
            } else if (userInput.equals("2")) {
                signUp();
            } else if (userInput.equals("3")) {
                scnr.close();
                System.exit(0);
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    public static void signIn() {
        try {
            File dataFile = new File("passwords.txt");
            if (dataFile.length() == 0) {
                System.out.println("There are currently no accounts in the system.");
                return;
            }
            Scanner scnr = new Scanner(System.in);
            Scanner fileScnr = new Scanner(dataFile);

            String username = "";
            String password = "";
            String currLine = "";

            System.out.println("*************************************");
            System.out.println("Enter Username: ");
            System.out.print(">");
            username = scnr.nextLine();

            // loop through usernames
            boolean isFound = false;
            while (fileScnr.hasNextLine()) {
                currLine = fileScnr.nextLine();
                String usernameData[] = currLine.split("#");
                if (username.equals(usernameData[0])) {
                    isFound = true;
                    System.out.println("Enter Password");
                    System.out.print(">");
                    password = scnr.nextLine();
                    if (password.equals(usernameData[1])) {
                        System.out.println("Welcome!");
                        System.exit(0);
                    } else {
                        System.out.println("INCORRECT PASSWORD");
                        scnr.close();
                        fileScnr.close();
                        return;
                    }
                }
            }
            if (!isFound) {
                System.out.println("INCORRECT USERNAME");
                return;
            }
        } catch (

        IOException e) {
            System.out.println("An error occurred during sign in.");
        }
    }

    public static void signUp() {
        try {
            File dataFile = new File("passwords.txt");
            if (dataFile.createNewFile()) {
                System.out.println("Passwords file created.");
            }
            Scanner scnr = new Scanner(System.in);
            Scanner fileScnr = new Scanner(dataFile);
            FileWriter writer = new FileWriter(dataFile, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);

            String username = "";
            String password = "";
            String currLine = "";

            char input[];
            System.out.println("Please Enter a Username:");
            System.out.print(">");
            username = scnr.nextLine();

            while (fileScnr.hasNextLine()) {
                currLine = fileScnr.nextLine();
                String userData[] = currLine.split("#");
                if (username.equals(userData[0])) {
                    System.out.println("Entered Username already exists, please use another.");
                    return;
                }
            }
            System.out.println("Username: " + username);
            System.out.println("Please Enter a Password:");
            System.out.print(">");
            password = scnr.nextLine();
            input = password.toCharArray();

            if (checkPassword(input)) {
                System.out.println("Password Accepted!");
                buffWriter.write(username + "#" + password);
                buffWriter.newLine();
                buffWriter.close();
                System.out.println("Account Created!");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static boolean checkPassword(char[] input) {
        if (input.length < 8) {
            System.out.println("Password not accepted, it is not at least 8 characters long.");
            return false;
        }
        boolean hasLetter = false;
        boolean hasNumber = false;
        for (int a = 0; a < input.length; a++) {
            if (Character.isLetter(input[a])) {
                hasLetter = true;
            }
            if (Character.isDigit(input[a])) {
                hasNumber = true;
            }
        }
        if (!hasLetter || !hasNumber) {
            System.out.println("Entered Password does not contain both letters and numbers, please try again.");
            return false;
        }
        for (int i = 0; i <= input.length - 4; i++) {
            if (input[i] == input[i + 1] && input[i] == input[i + 2]
                    && input[i] == input[i + 3]) {
                System.out.println("Password not accepted, cannot have 4 repeated characters.");
                return false;
            }
            if (input[i + 1] == input[i] + 1
                    && input[i + 2] == input[i] + 2
                    && input[i + 3] == input[i] + 3) {
                System.out.println("Password not accepted, cannot have 4 consecutive characters.");
                return false;
            }
            if (input[i + 1] == input[i] - 1
                    && input[i + 2] == input[i] - 2
                    && input[i + 3] == input[i] - 3) {
                System.out.println("Password not accepted, cannot have 4 consecutive characters.");
                return false;
            }
        }
        return true;
    }

    public static void mainMenu() {
        System.out.println("*************************************");
        System.out.println("                WELCOME              ");
        System.out.println("*************************************");
        System.out.println("1. Sign In");
        System.out.println("2. Create Account");
        System.out.println("3. Quit");
        System.out.print(">");
        return;
    }
}