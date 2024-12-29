import java.util.Scanner;

public class ShortAnswer extends Essay{
    private final String questionType = "ShortAnswer";
    private static final long serialVersionUID = 1L;

    public ShortAnswer(String questionName, int responseLength) {
        super(questionName, responseLength, 1);
        setQuestionType("ShortAnswer");
    }

    @Override
    public void takeQuestion(){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println(this.questionName);
        System.out.println("Response length should be " + getResponseLength());
            while (true) {
                String input = this.scanner.nextLine().trim();
                if ((input.length() <= getResponseLength()) && (!input.isEmpty())){
                    this.responses.addResponse(input);
                    updateTabulate(input);
                    break;
                } else {
                    System.out.println("Invalid input. Either it's empty or out of response length. Pls try again:");
                }
            }
        }


    @Override
    public void modify(){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println("1. Change question name");
        System.out.println("2. Change response length");

        String userResponse;
        while (true) {
            userResponse = this.scanner.nextLine().trim();
            if (userResponse.matches("[1-2]")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid option (1 or 2):");
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
    public void displayResponse(){
        //System.out.println(this.responses.getResponse(0));
    }

}
