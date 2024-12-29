import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ValidDate extends Question{
    private final String questionType = "ValidDate";
    private static final long serialVersionUID = 1L;

    public ValidDate(String questionName) {
        super(questionName);
        setQuestionType("ValidDate");
    }

    public String getQuestionName(){
        return this.questionName;
    }

    public void setQuestionName(String n){
        this.questionName = n;
    }

    @Override
    public void display(){
        System.out.println(getQuestionName());
        System.out.println("A date should be entered in the following format: YYYY-MM-DD");
    }

    @Override
    public void takeQuestion() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        display();

        String date;
        while (true) {
            date = this.scanner.nextLine().trim();

            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                String[] dateParts = date.split("-");

                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);

                if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                    this.responses.addResponse(date);
                    updateTabulate(date);
                    break;
                } else {
                    System.out.println("Invalid date.");
                }
            } else {
                System.out.println("Invalid format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    @Override
    public void modify() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        String userInput = "";
        System.out.println("1. Change question name");
        while (!Objects.equals(userInput, "1")) {
            userInput = this.scanner.nextLine().trim();
            if (Objects.equals(userInput, "1")) {
                String new_name = "";
                System.out.println("Enter a new question name: ");
                while (new_name.isEmpty()) {
                    new_name = this.scanner.nextLine().trim();
                    if (new_name.isEmpty()) {
                        System.out.println("An input cannot be empty. Please enter a new question name: ");
                    }
                }
                setQuestionName(new_name);
            } else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    @Override
    public void displayResponse(){
        //System.out.println(this.responses.getResponse(0));
    }

    @Override
    public void displayTabulate(){
        System.out.println(this.questionName);
        for (Map.Entry<String, Integer> entry : this.tabulateData.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}
