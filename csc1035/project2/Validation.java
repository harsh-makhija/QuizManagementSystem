package csc1035.project2;

import java.util.Scanner;

public class Validation {

    // Creates a scanner object.

    public static int validateInteger(String strInput, String error) {
        Scanner in = new Scanner(System.in);
        int num = 0;
        boolean validity = false;
// Keep looping until a valid input is entered.
        while (validity == false) {
            try {
                num = Integer.parseInt(strInput);
                validity = true;
            } catch (NumberFormatException e) {
                System.out.println(error);
                strInput = in.nextLine();
            }
        }

        return num;
    }

    // Create a stringLimit method to limit the maximum input given by the user.
    public static String stringLimit(String stringInput) {
        Scanner in = new Scanner(System.in);
        String errorMsg = "You have exceeded the maximum input, please try again.";
        int maxLength = 255;
        System.out.println("Enter your answer");
        boolean inputLengthExceded = true;
// While loop to run unless the input length is greater than the limit. Will give error if it is exceeded,
        while (inputLengthExceded == true) {
            stringInput = in.nextLine();
            if (stringInput.length() > maxLength) {
                System.out.println(errorMsg);
                stringInput = in.nextLine();
            } else {
                break;
            }

        }
        return stringInput;
    }


    public static String blankInput(String stringInput) {
        Scanner in = new Scanner(System.in);
        String errorMsg = "You have not entered a valid input, please try again.";
        boolean wrongInputDetect = true;

        while (wrongInputDetect == true) {

            if (stringInput.isEmpty()) {
                System.out.println(errorMsg);
                stringInput = in.nextLine();

            }
            else if(stringInput.length()>250) {
                System.out.println(errorMsg);
                stringInput = in.nextLine();
            }
            else {
                boolean allSpaces = true;
                for (int x = 0; x < stringInput.length(); x++) {
                    if (stringInput.charAt(x) != ' ') {
                        allSpaces = false;
                    }
                }
                if (!allSpaces) {
                    break;
                }
                else {
                    System.out.println(errorMsg);
                    stringInput = in.nextLine();
                }
            }

        }
        return stringInput;
    }
}






