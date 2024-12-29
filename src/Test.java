import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Test extends Survey {
    private ResponseCorrectAnswer responseCorrectAnswer = new ResponseCorrectAnswer();
    transient Scanner scanner = new Scanner(System.in);
    public static final String basePath = "tests" + File.separator;
    private static final long serialVersionUID = 1L;

    public Test(String surveyName) {
        super(surveyName);
    }

    public ArrayList<String> getCorrectAnswerOfQuestion(String qN){
        return this.responseCorrectAnswer.getCorrectAnswerOfQuestion(qN);
    }

    public static String serialize(Test test) {
        return utils.SerializationHelper.serialize(Test.class, test, basePath, test.getSurveyName()+".txt");
    }

    public static Test deserialize(String path) {
        return utils.SerializationHelper.deserialize(Test.class, path);
    }

    public void addCorrectAnswer(Question q) {
        String qType = q.getQuestionType();
        switch (qType){
            case "Essay":
                break;
            case "ShortAnswer":
                addShortAnswerAnswer(q);
                break;
            case "Matching":
                addMatchingAnswer(q);
                break;
            case "ValidDate":
                addValidDateAnswer(q);
                break;
            case "MultipleChoice":
                addMultipleChoiceAnswer(q);
                break;
            case "TrueFalse":
                addTrueFalseAnswer(q);
                break;
        }
        System.out.println();
    }

    private void addShortAnswerAnswer(Question q) {
        ShortAnswer shortAnswer = (ShortAnswer) q;
        int questionLength = shortAnswer.getResponseLength();
        ArrayList<String> correctR = new ArrayList<>();
        System.out.print("Add your ShortAnswer Answer (max length: " + questionLength + "):");
        String answer;
        while (true) {
            answer = this.scanner.nextLine().trim();
            if (answer.isEmpty()) {
                System.out.println("Invalid input. The answer cannot be empty. Please try again.");
            } else if (answer.length() > questionLength) {
                System.out.println("Invalid input. The answer exceeds the maximum length of " + questionLength + ". Please try again.");
            } else {
                break;
            }
        }
        correctR.add(answer);
        this.responseCorrectAnswer.addCorrectAnswer(q.getQuestionName(), correctR);
    }


    private void addMatchingAnswer(Question q) {
        ArrayList<String> correctR = new ArrayList<>();
        Matching matchingQuestion = (Matching) q;
        for(int i = 0; i < matchingQuestion.getLeftSize(); i++){
            matchingQuestion.displaySides();
            System.out.print("Add you matching answer (ex. A1, B2, ..):");
            String response = scanner.nextLine();
            correctR.add(response);
        }
        this.responseCorrectAnswer.addCorrectAnswer(q.getQuestionName(), correctR);
    }

    private String getValidOptions(int numberOfOptions) {
        StringBuilder validOptions = new StringBuilder();
        for (int i = 0; i < numberOfOptions; i++) {
            validOptions.append((char) ('A' + i));
            if (i < numberOfOptions - 1) {
                validOptions.append(", ");
            }
        }
        return validOptions.toString();
    }

    private void addMultipleChoiceAnswer(Question q) {
        ArrayList<String> correctR = new ArrayList<>();
        HashSet<String> seenAnswers = new HashSet<>();
        int numberOfOptions = 3;
        int numberOfResponses = q.getUserResponseNumber();

        System.out.print("Available options are: ");
        for (int i = 0; i < numberOfOptions; i++) {
            char option = (char) ('A' + i);
            System.out.print(option + (i < numberOfOptions - 1 ? ", " : "\n"));
        }

        System.out.println("You can choose " + numberOfResponses + " of these options.");
        for (int i = 0; i < numberOfResponses; i++) {
            System.out.print("Add choice " + (i + 1) + ":");
            String answer;
            while (true) {
                answer = scanner.nextLine().trim().toUpperCase();
                if (answer.isEmpty()) {
                    System.out.println("Invalid input. The answer cannot be empty. Please try again.");
                } else if (answer.length() != 1 || answer.charAt(0) < 'A' || answer.charAt(0) >= 'A' + numberOfOptions) {
                    System.out.println("Invalid input. Please enter one of the following: " + getValidOptions(numberOfOptions));
                } else if (seenAnswers.contains(answer)) {
                    System.out.println("Invalid input. This choice has already been selected. Please select a different option.");
                } else {
                    seenAnswers.add(answer);
                    break;
                }
            }
            correctR.add(answer);
        }
        this.responseCorrectAnswer.addCorrectAnswer(q.getQuestionName(), correctR);
    }



    public void addValidDateAnswer(Question q) {
        ArrayList<String> correctR = new ArrayList<>();
        System.out.print("Add your ValidDate Answer (format: yyyy-MM-dd):");
        String answer;
        while (true) {
            answer = this.scanner.nextLine().trim();
            if (isValidDate(answer)) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter a valid date in the format yyyy-MM-dd.");
            }
        }
        correctR.add(answer);
        this.responseCorrectAnswer.addCorrectAnswer(q.getQuestionName(), correctR);
    }

    public void addTrueFalseAnswer(Question q) {
        ArrayList<String> correctR = new ArrayList<>();
        System.out.print("Add your True/False Answer (T or F):");
        String answer;
        while (true) {
            answer = this.scanner.nextLine().trim().toUpperCase();
            if (answer.equals("T") || answer.equals("F")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'T' or 'F'.");
            }
        }
        correctR.add(answer);
        this.responseCorrectAnswer.addCorrectAnswer(q.getQuestionName(), correctR);
    }


    @Override
    public void displayResponse(){
        System.out.println(getSurveyName());
        for(int i = 0; i < getQuestionList().size(); i++){
            System.out.print((i + 1) + ")");
            getQuestionList().get(i).display();
            displayCorrectA(getQuestionList().get(i).getQuestionName());
            System.out.println();
        }
    }

    public void displayCorrectA(String questionN){
        StringBuilder correctA = new StringBuilder("The Correct choice is:");
        for(int i = 0; i < this.responseCorrectAnswer.getNumberOfCorrectAnswers(questionN); i++){
            correctA.append(" ").append(this.responseCorrectAnswer.getCorrectAnswerByIndex(questionN, i));
        }
        System.out.println(correctA);
    }

    @Override
    public void modifyQuestion(int questionNum){
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        String questionN = getQuestion(questionNum).getQuestionName();
        getQuestion(questionNum).modify();
        this.responseCorrectAnswer.updateTheQuestionName(questionN,getQuestion(questionNum).getQuestionName());
        modifyCorrectAnswer(getQuestion(questionNum));
    }

    public void modifyCorrectAnswer(Question q){

        switch (q.getQuestionType()){
            case "ShortAnswer":
                ShortAnswer shortAnswer = (ShortAnswer) q;
                int questionLength = shortAnswer.getResponseLength();

                System.out.println("Do you want to modify the correct answer as well?");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    String newAnswer;
                    while (true) {
                        System.out.println("Enter your new correct answer (max length: " + questionLength + "):");
                        newAnswer = scanner.nextLine().trim();
                        if (newAnswer.isEmpty()) {
                            System.out.println("Invalid input. The answer cannot be empty. Please try again.");
                        } else if (newAnswer.length() > questionLength) {
                            System.out.println("Invalid input. The answer exceeds the maximum length of " + questionLength + ". Please try again.");
                        } else {
                            break;
                        }
                    }
                    this.responseCorrectAnswer.setNewCorrectAnswer(q.getQuestionName(), 0, newAnswer);
                }
                break;
            case "Essay":
                break;
            case "TrueFalse":
                System.out.println("Do you want to modify the correct answer as well?");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    String newAnswer;
                    while (true) {
                        System.out.println("Enter your new correct answer (T or F):");
                        newAnswer = scanner.nextLine().trim().toUpperCase();
                        if (newAnswer.equals("T") || newAnswer.equals("F")) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'T' or 'F'.");
                        }
                    }
                    this.responseCorrectAnswer.setNewCorrectAnswer(q.getQuestionName(), 0, newAnswer);
                }
                break;
            case "MultipleChoice":
                System.out.println("Do you want to modify the correct answer as well?");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    Set<String> uniqueResponses = new HashSet<>();
                    for (int i = 0; i < q.getUserResponseNumber(); i++) {
                        int position = i + 1;
                        String newAnswer;
                        while (true) {
                            System.out.print("Add your " + position + " Multiple Choice Answer: ");
                            newAnswer = scanner.nextLine().trim();
                            if (newAnswer.isEmpty()) {
                                System.out.println("Invalid input. The answer cannot be empty. Please try again.");
                            } else if (uniqueResponses.contains(newAnswer)) {
                                System.out.println("Duplicate answer detected. Please enter a unique answer.");
                            } else {
                                uniqueResponses.add(newAnswer);
                                break;
                            }
                        }
                        this.responseCorrectAnswer.setNewCorrectAnswer(q.getQuestionName(), i, newAnswer);
                    }
                }
                break;
            case "ValidDate":
                System.out.println("Do you want to modify the correct answer as well?");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    String newAnswer;
                    while (true) {
                        System.out.println("Enter your new correct answer (format: yyyy-mm-dd):");
                        newAnswer = scanner.nextLine().trim();
                        if (isValidDate(newAnswer)) {
                            break;
                        } else {
                            System.out.println("Invalid date format. Please enter a valid date in the format yyyy-mm-dd.");
                        }
                    }
                    this.responseCorrectAnswer.setNewCorrectAnswer(q.getQuestionName(), 0, newAnswer);
                }
                break;
            case "Matching":
                System.out.println("Do you want to modify the correct answer as well?");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    Matching matchingQuestion = (Matching) q;
                    ArrayList<String> providedAnswers = new ArrayList<>();

                    for (int i = 0; i < matchingQuestion.getLeftSize(); i++) {
                        System.out.print("Add your matching answer for item " + (i + 1) + ":");
                        String answer;
                        while (true) {
                            answer = scanner.nextLine().trim();
                            if (answer.isEmpty()) {
                                System.out.println("Invalid input. The answer cannot be empty. Please try again.");
                            } else if (providedAnswers.contains(answer)) {
                                System.out.println("Invalid input. This answer has already been provided. Please provide a unique answer.");
                            } else {
                                providedAnswers.add(answer);
                                break;
                            }
                        }
                        this.responseCorrectAnswer.setNewCorrectAnswer(matchingQuestion.getQuestionName(), i, answer);
                    }
                }
                break;
        }
    }

    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
