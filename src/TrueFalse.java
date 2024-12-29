import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class TrueFalse extends MultipleChoice{

    private final String questionType = "TrueFalse";
    private static final long serialVersionUID = 1L;

    public TrueFalse(String questionName) {
        super(questionName, 2);
        this.addResponseOption("True");
        this.addResponseOption("False");
        this.scanner = new Scanner(System.in);
        setQuestionType("TrueFalse");
    }

    @Override
    public void display(){
        System.out.println(this.questionName);
        System.out.println("T/F");
    }

    @Override
    public void takeQuestion() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        display();
        while (true) {
            String newChoice = this.scanner.nextLine().trim();
            if (newChoice.equals("T") || newChoice.equals("F")) {
                this.responses.addResponse(newChoice);
                updateTabulate(newChoice);
                break;
            } else {
                System.out.println("Invalid choice, choose either T or F");
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
            userInput = this.scanner.nextLine();

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
    public void setUpTabulate(){
        this.tabulateData.put("T", 0);
        this.tabulateData.put("F", 0);
    }
}
