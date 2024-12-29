import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class MultipleChoice extends Question{
    private final String questionType = "MultipleChoice";
    private static final long serialVersionUID = 1L;

    private int numberOfChoices;

    public MultipleChoice(String questionName, int numberOfChoices) {
        super(questionName);
        this.numberOfChoices = numberOfChoices;
        setQuestionType("MultipleChoice");
    }

    public void addUserResponses(String r){
        this.responses.addResponse(r);
    }

    public int getNumberOfChoices(){
        return this.numberOfChoices;
    }

    public void setNumberOfChoices(int n){
        this.numberOfChoices = n;
    }

    @Override
    public void display(){
        System.out.println(this.questionName);
        int i = 0;
        for (String responseOption : this.responseOptions) {
            System.out.println((char) ('A' + i) + ") " + responseOption);
            i++;
        }
    }

    @Override
    public void takeQuestion() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }

        if (getUserResponseNumber() > 1) {
            System.out.println(getQuestionName() + " Please choose " + getUserResponseNumber() + " choices:");
        } else {
            System.out.println(getQuestionName() + " Please choose only one choice:");
        }

        int i = 0;
        for (String responseOption : this.responseOptions) {
            System.out.println((char) ('A' + (i)) + ") " + responseOption);
            i++;
        }

        if (getUserResponseNumber() > 1) {
            for (int z = 0; z < getUserResponseNumber(); z++) {
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.println("Enter choice " + (z + 1) + ": ");
                    String userInput = this.scanner.nextLine().toUpperCase();
                    if (isValidChoice(userInput)) {
                        addUserResponses(userInput);
                        updateTabulate(userInput);
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a valid option (A, B, C, ...).");
                    }
                }
            }
        } else {
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("Enter your choice: ");
                String userInput = this.scanner.nextLine().toUpperCase(); // Normalize to uppercase
                if (isValidChoice(userInput)) {
                    addUserResponses(userInput);
                    updateTabulate(userInput);
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }

    private boolean isValidChoice(String userInput) {
        int choiceIndex = userInput.charAt(0) - 'A';
        return choiceIndex >= 0 && choiceIndex < this.responseOptions.size();
    }


    @Override
    public void modify() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }

        System.out.println("1. Change question name");
        System.out.println("2. Change question choices");
        System.out.println("3. Change response numbers");

        String userResponse = this.scanner.nextLine();

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

            case "3":
                System.out.println("Enter how many choices the user can choose:");
                while (true) {
                    try {
                        setUserResponseNumber(Integer.parseInt(this.scanner.nextLine()));
                        if (getUserResponseNumber() > getNumberOfChoices()) {
                            System.out.println("Response number is more than the response options. Please add more response options.");
                            addResponseOption(this.scanner.nextLine());
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Please try again.");
                    }
                }
                break;

            case "2":
                String userI = "";
                while (true) {
                    System.out.println("1. Edit the choice");
                    System.out.println("2. Remove the choice");
                    userI = this.scanner.nextLine();

                    if (userI.equals("1")) {
                        editChoice();
                        break;
                    } else if (userI.equals("2")) {
                        removeChoice();
                        break;
                    } else {
                        System.out.println("Invalid option. Please select '1' or '2'.");
                    }
                }

            default:
                System.out.println("Invalid selection. Please choose a valid option.");
                break;
        }
    }

    private void editChoice() {
        System.out.println("Which option do you want to edit:");
        displayChoices();

        while (true) {
            try {
                int choiceIndex = Integer.parseInt(this.scanner.nextLine()) - 1;
                if (choiceIndex >= 0 && choiceIndex < this.responseOptions.size()) {
                    System.out.println("Enter a new choice:");
                    while (true) {
                        String newChoice = this.scanner.nextLine().trim();
                        if (!newChoice.isEmpty()) {
                            this.setResponseOptions(choiceIndex, newChoice);
                            break;
                        } else {
                            System.out.println("Choice cannot be empty. Please enter a valid choice:");
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid choice number. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid choice number.");
            }
        }
    }

    private void removeChoice() {
        System.out.println("Which option do you want to delete:");
        displayChoices();

        while (true) {
            try {
                int choiceIndex = Integer.parseInt(this.scanner.nextLine()) - 1;
                if (choiceIndex >= 0 && choiceIndex < this.responseOptions.size()) {
                    this.removeResponseOption(choiceIndex);
                    break;
                } else {
                    System.out.println("Invalid choice number. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid choice number.");
            }
        }
    }

    private void displayChoices() {
        for (int i = 0; i < this.responseOptions.size(); i++) {
            System.out.println(i + 1 + ") " + this.getResponseOptions(i));
        }
    }

    @Override
    public void displayResponse() {
    }

    @Override
    public void displayTabulate(){
        System.out.println(this.questionName);
        displayChoices();
        for (Map.Entry<String, Integer> entry : this.tabulateData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
