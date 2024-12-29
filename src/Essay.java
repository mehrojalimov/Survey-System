import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Essay extends Question implements Serializable {
    private int responseLength;
    private final String questionType = "Essay";
    private int responseNumber;

    private static final long serialVersionUID = 1L;


    public Essay(String questionName, int responseLength, int responseNumber) {
        super(questionName);
        this.responseLength = responseLength;
        this.responseNumber = responseNumber;
        setQuestionType("Essay");
    }

    public int getResponseNumber(){
        return this.responseNumber;
    }

    public void setResponseNumber(int r_number){
        this.responseNumber = r_number;
    }

    public int getResponseLength(){
        return this.responseLength;
    }

    public void setResponseLength(int r){
        this.responseLength = r;
    }

    @Override
    public void display(){
        System.out.println(this.questionName);
    }

    @Override
    public void takeQuestion(){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println(this.questionName);
        System.out.println("Response length should be " + this.responseLength);
        if(getResponseNumber() > 1){
            for(int i = 0; i < getResponseNumber(); i++) {
                System.out.print((char) ('A' + i) + ") ");
                while (true) {
                    String input = this.scanner.nextLine().trim();
                    if ((input.length() <= this.responseLength) && (!input.isEmpty())){
                        this.addUserResponse(input);
                        updateTabulate(input);
                        break;
                    } else {
                        System.out.println("Invalid input. Either it's empty or out of response length. Pls try again:");
                    }
                }
            }
        } else {
            while (true) {
                String input = this.scanner.nextLine().trim();
                if ((!input.isEmpty()) && (input.length() <= this.responseLength)) {
                    this.addUserResponse(input);
                    updateTabulate(input);
                    break;
                } else {
                    System.out.println("Invalid input. Either it's empty or out of response length. Pls try again:");
                }
            }
        }
    }

    @Override
    public void modify(){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }

        System.out.println("1. Change question name");
        System.out.println("2. Change response number");
        System.out.println("3. Change response length");

        String userResponse;
        while (true) {
            userResponse = this.scanner.nextLine().trim();
            if (userResponse.matches("[1-3]")) {
                break; // Exit loop if input is valid (1, 2, or 3)
            } else {
                System.out.println("Invalid input. Please enter a valid option (1, 2, or 3):");
            }
        }

        switch (userResponse) {
            case "1":
                System.out.println("Enter a new question name: ");
                String newName;
                while (true) {
                    newName = this.scanner.nextLine().trim();
                    if (!newName.isEmpty()) {
                        setQuestionName(newName);
                        break;
                    } else {
                        System.out.println("Question name cannot be empty. Please try again:");
                    }
                }
                break;

            case "2":
                System.out.println("Enter new number of response prompts:");
                while (true) {
                    try {
                        int newResponseNumber = Integer.parseInt(this.scanner.nextLine().trim());
                        if (newResponseNumber > 0) {
                            setResponseNumber(newResponseNumber);
                            break;
                        } else {
                            System.out.println("Number must be greater than 0. Please try again:");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer:");
                    }
                }
                break;

            case "3":
                System.out.println("Enter new response length:");
                while (true) {
                    try {
                        int newResponseLength = Integer.parseInt(this.scanner.nextLine().trim());
                        if (newResponseLength > 0) {
                            setResponseLength(newResponseLength);
                            break;
                        } else {
                            System.out.println("Length must be greater than 0. Please try again:");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer:");
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void displayResponse() {

    }

    @Override
    public void displayTabulate(){
        System.out.println(this.questionName);
        for (Map.Entry<String, Integer> entry : this.tabulateData.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

}
