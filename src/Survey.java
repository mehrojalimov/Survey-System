import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Survey implements Serializable {

    private String surveyName;
    public static final String basePath = "surveys" + File.separator;
    ArrayList<Question>  questionList;
    transient Scanner scanner = new Scanner(System.in);
    private static final long serialVersionUID = 1L;

    public Survey(String surveyName){
        this.surveyName = surveyName;
        this.questionList = new ArrayList<>();
    }

    public boolean isTaken(){
        return this.questionList.get(0).isTaken();
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public static String serialize(Survey survey) {
        // Serialize the car to disk using the existing helper function
        return utils.SerializationHelper.serialize(Survey.class, survey, basePath, survey.getSurveyName()+".txt");
    }

    public static Survey deserialize(String path) {
        return utils.SerializationHelper.deserialize(Survey.class, path);
    }

    public int getQuestionListSize(){
        return this.questionList.size();
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public void addQuestion(Question q){
        this.questionList.add(q);
    }

    public Question getQuestion(int q_index){
        return this.questionList.get(q_index);
    }

    public void removeQuestion(int q_index){
        this.questionList.remove(q_index);
    }

    public String getSurveyName(){
        return this.surveyName;
    }

    public void setSurveyName(String s){
        this.surveyName = s;
    }


    public void addMultipleChoice() {
        System.out.print("Enter your multiple-choice question: ");
        String questionName = this.scanner.nextLine();

        while (questionName.trim().isEmpty()) {
            System.out.println("Question name cannot be empty. Please enter a valid question.");
            questionName = this.scanner.nextLine();
        }

        MultipleChoice new_question = new MultipleChoice(questionName, 1);
        addQuestion(new_question);
        addMultipleChoiceOptions(new_question);
    }


    public void addMultipleChoiceOptions(MultipleChoice question) {
        int numberOfChoices = -1;
        while (numberOfChoices <= 0) {
            System.out.print("Enter number of choices: ");
            try {
                numberOfChoices = Integer.parseInt(this.scanner.nextLine());
                if (numberOfChoices <= 0) {
                    System.out.println("The number of choices must be greater than 0. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        question.setNumberOfChoices(numberOfChoices);

        for (int i = 1; i <= numberOfChoices; i++) {
            String choice;
            do {
                System.out.print("Enter Choice #" + (char) ('A' + (i-1)) + ": ");
                choice = this.scanner.nextLine().trim();
                if (choice.isEmpty()) {
                    System.out.println("Choice cannot be empty. Please enter a valid choice.");
                }
            } while (choice.isEmpty());
            question.addResponseOption(choice);
        }

        int userResponseNumber = -1;
        while (userResponseNumber <= 0 || userResponseNumber > numberOfChoices) {
            System.out.print("Enter number of responses user can select: ");
            try {
                userResponseNumber = Integer.parseInt(this.scanner.nextLine());
                if (userResponseNumber <= 0 || userResponseNumber > numberOfChoices) {
                    System.out.println("The number of responses must be greater than 0 and less than or equal to the number of choices.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }

        question.setUserResponseNumber(userResponseNumber);
    }

    public void addTrueFalse() {
        String questionName;
        while (true) {
            System.out.print("Enter your T/F question: ");
            questionName = this.scanner.nextLine();

            if (!questionName.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Question cannot be empty. Please enter a valid question.");
            }
        }
        addQuestion(new TrueFalse(questionName));
    }


    public void addShortAnswer() {
        String questionName;
        while (true) {
            System.out.print("Enter your short answer question: ");
            questionName = this.scanner.nextLine();
            if (!questionName.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Question cannot be empty. Please enter a valid question.");
            }
        }
        int responseLength;
        while (true) {
            System.out.print("Enter the maximum response length: ");
            try {
                responseLength = Integer.parseInt(this.scanner.nextLine());
                if (responseLength > 0) {
                    break;
                } else {
                    System.out.println("Response length must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for response length.");
            }
        }

        // Create and add the new ShortAnswer question
        ShortAnswer new_shortAnswer = new ShortAnswer(questionName, responseLength);
        addQuestion(new_shortAnswer);
    }


    public void addEssay() {
        String questionName;
        while (true) {
            System.out.print("Enter your Essay question: ");
            questionName = this.scanner.nextLine();
            if (!questionName.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Question cannot be empty. Please enter a valid question.");
            }
        }

        int responseLength;
        while (true) {
            System.out.print("Enter the maximum response length: ");
            try {
                responseLength = Integer.parseInt(this.scanner.nextLine());
                if (responseLength > 0) {
                    break;
                } else {
                    System.out.println("Response length must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for response length.");
            }
        }

        int responses;
        while (true) {
            System.out.print("Enter number of prompts (response(s) user can): ");
            try {
                responses = Integer.parseInt(this.scanner.nextLine());
                if (responses > 0) {
                    break;
                } else {
                    System.out.println("The number of prompts must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the number of prompts.");
            }
        }

        Essay new_essay = new Essay(questionName, responseLength, responses);
        addQuestion(new_essay);
    }

    public void addNewDate(){
        System.out.print("Enter your Date question: ");
        String questionName = this.scanner.nextLine().trim();
        while (questionName.isEmpty()) {
            System.out.println("Question name cannot be empty. Please enter a valid question name.");
            questionName = this.scanner.nextLine().trim();
        }

        ValidDate new_validDate = new ValidDate(questionName);
        addQuestion(new_validDate);
    }

    public void addMatchingQuestion() {
        String questionName = "";
        while (questionName.trim().isEmpty()) {
            System.out.print("Enter your Matching question: ");
            questionName = this.scanner.nextLine();
            if (questionName.trim().isEmpty()) {
                System.out.println("Question name cannot be empty. Please try again.");
            }
        }
        Matching new_matching = new Matching(questionName, 1, 1);
        addQuestion(new_matching);
        addMatchingOptions(new_matching);
    }

    private void addMatchingOptions(Matching matching) {
        int number_of_options = 0;
        while (number_of_options <= 1) {
            System.out.print("Enter number of matching options (more than 1): ");
            try {
                number_of_options = Integer.parseInt(this.scanner.nextLine());
                if (number_of_options <= 1) {
                    System.out.println("Invalid input: Please enter a number greater than 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.");
            }
        }

        matching.setLeftSize(number_of_options);
        matching.setRightSize(number_of_options);

        setUpSides(matching, number_of_options, "left");
        setUpSides(matching, number_of_options, "right");
    }

    private void setUpSides(Matching matching, int number_of_option, String side) {
        for (int i = 1; i <= number_of_option; i++) {
            String choice = "";

            while (choice.trim().isEmpty()) {
                System.out.print("Enter Choice #" + i + " for the " + side + " side: ");
                choice = this.scanner.nextLine();

                if (choice.trim().isEmpty()) {
                    System.out.println("Invalid input: Choice cannot be empty or just spaces.");
                }
            }
            if (Objects.equals(side, "left")) {
                matching.add_item_left_side(choice);
            } else {
                matching.add_item_right_side(choice);
            }
        }
    }
    public void closeScanner() {
        this.scanner.close();
    }

    public void displaySurvey(){
        System.out.println(getSurveyName());
        for(int i = 0; i < getQuestionList().size(); i++){
            System.out.print((i + 1) + ")");
            getQuestionList().get(i).display();
            System.out.println();
        }
    }

    public void takeSurvey(){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        for(int i = 0; i < getQuestionList().size(); i++){
            getQuestion(i).takeQuestion();
        }
    }

    public void modifyQuestion(int questionNum){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        getQuestion(questionNum).modify();
    }
    public void displayResponse(){}
}
