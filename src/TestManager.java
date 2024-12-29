import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TestManager extends SurveyManager implements Serializable {

    private Test test;
    private static final long serialVersionUID = 1L;

    public TestManager(){
        this.test = null;
    }


    public void saveTest(){
        if (this.test == null) {
            System.out.println("You must have a survey loaded in order to save it.");
        } else {
            String userInput = "";

            // Ensure that the survey name is not empty or just spaces
            while (userInput.trim().isEmpty()) {
                System.out.println("Name the survey file:");
                userInput = this.scanner.nextLine();

                if (userInput.trim().isEmpty()) {
                    System.out.println("Invalid input: Survey name cannot be empty or just spaces.");
                }
            }

            this.test.setSurveyName(userInput);

            String path = Test.serialize(this.test);

            System.out.println("Survey saved successfully at: " + path);
        }
    }

    public void loadTest() {
        File folder = new File(Test.basePath);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No surveys found to load.");
            return;
        }
        System.out.println("Please select a file to load:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ") " + files[i].getName());
        }
        String userInput = this.scanner.nextLine();
        while (true) {
            try {
                String filePath = Test.basePath + files[Integer.parseInt(userInput) - 1].getName();
                this.test = Test.deserialize(filePath);
                break;
            } catch (Exception e) {
                System.out.println("Invalid selection or error occurred. Please try again.");
                userInput = this.scanner.nextLine();
            }
        }
    }

    @Override
    public void survey_menu() {
        String num = "-1";
        while (!num.equals("10")) {
            System.out.println("1. Create a new Test");
            System.out.println("2. Display an existing Test without correct answers");
            System.out.println("3. Display an existing Test with correct answers");
            System.out.println("4. Load an existing Test");
            System.out.println("5. Save the current Test");
            System.out.println("6. Take the current Test");
            System.out.println("7. Modify the current Test");
            System.out.println("8. Tabulate a Test");
            System.out.println("9. Grade a Test");
            System.out.println("10. Quit");

            num = this.scanner.nextLine();

            if (!num.matches("[1-9]")) {
                System.out.println("Invalid input! Please choose a valid option (1-10).");
                continue;
            }
            switch (num) {
                case "1":
                    String survey_name = "";
                    while (survey_name.trim().isEmpty()) {
                        System.out.println("Enter your test name: ");
                        survey_name = this.scanner.nextLine().trim();
                        if (survey_name.isEmpty()) {
                            System.out.println("Test name cannot be empty. Please try again.");
                        }
                    }
                    this.test = createTest(survey_name);
                    addQuestions(this.test);
                    break;
                case "2":
                    if (getSurvey() == null) {
                        System.out.println("No test is loaded.");
                    } else {
                        getSurvey().displaySurvey();
                    }
                    break;
                case "3":
                    if (getSurvey() == null) {
                        System.out.println("No test is loaded.");
                    } else {
                        getSurvey().displayResponse();
                    }
                    break;
                case "4":
                    loadTest();
                    break;
                case "5":
                    saveTest();
                    break;
                case "6":
                    if (getSurvey() == null) {
                        System.out.println("No test is loaded.");
                    } else {
                        getSurvey().takeSurvey();
                        System.out.println("Test is taken!");
                    }
                    break;
                case "7":
                    if (getSurvey() == null) {
                        System.out.println("No test is loaded.");
                    } else {
                        modifySurvey();
                    }
                    break;
                case "8":
                        tabulate();
                    break;
                case "9":
                        gradeTest();
                    break;
                case "10":
                    System.out.println("Exiting test menu...");
                    break;
            }
        }
    }


    public void addQuestions(Test test) {
        String num = "-1";
        while (!num.equals("7")) {
            System.out.println("Please select from the following:");
            System.out.println("1. Add a new T/F question");
            System.out.println("2. Add a new multiple-choice question");
            System.out.println("3. Add a new short answer question");
            System.out.println("4. Add a new essay question");
            System.out.println("5. Add a new date question");
            System.out.println("6. Add a new matching question");
            System.out.println("7. Return to previous menu");

            num = this.scanner.nextLine();
            if (!num.matches("[1-7]")) {
                System.out.println("Invalid input! Please choose a number between 1 and 7.");
                continue;
            }

            switch (num) {
                case "1":
                    test.addTrueFalse();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "2":
                    test.addMultipleChoice();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "3":
                    test.addShortAnswer();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "4":
                    test.addEssay();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "5":
                    test.addNewDate();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "6":
                    test.addMatchingQuestion();
                    test.addCorrectAnswer(test.getQuestion(test.getQuestionListSize() - 1));
                    break;
                case "7":
                    System.out.println("Returning to previous menu.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static Test createTest(String testName){
        return new Test(testName);
    }

    public Test getSurvey(){
        return this.test;
    }

    public void gradeTest() {
        File folder = new File(Test.basePath);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No surveys found to load.");
            return;
        }

        System.out.println("Please select a file to grade:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ") " + files[i].getName());
        }

        String userInput = this.scanner.nextLine();
        while (true) {
            try {
                String filePath = Test.basePath + files[Integer.parseInt(userInput) - 1].getName();
                this.test = Test.deserialize(filePath);
                break;
            } catch (Exception e) {
                System.out.println("Invalid selection or error occurred. Please try again.");
                userInput = this.scanner.nextLine();
            }
        }
        grade();
    }

    public void grade() {
        int totalNumberOfQuestions = this.test.getQuestionListSize();
        int pointsPerQuestion = 100 / totalNumberOfQuestions;
        int essayQuestions = 0;
        int correctScore = 0;

        for (int i = 0; i < totalNumberOfQuestions; i++) {
            Question question = this.test.getQuestion(i);
            String questionType = question.getQuestionType();

            if (questionType.equalsIgnoreCase("Essay")) {
                essayQuestions++;
                continue;
            }

            ArrayList<String> userResponses = question.getUserResponseArray();
            ArrayList<String> correctAnswers = this.test.getCorrectAnswerOfQuestion(question.getQuestionName());
            correctScore += compareTheResponseAnswer(userResponses, correctAnswers) ? pointsPerQuestion : 0;
        }

        int gradablePoints = (totalNumberOfQuestions - essayQuestions) * pointsPerQuestion;

        System.out.printf("You received %d points out of 100 on the test. Make sure you take the survey before you grade it!\n", correctScore);
        System.out.printf("The test was worth 100 points, but only %d points could be auto-graded because there were %d essay question(s).\n",
                gradablePoints, essayQuestions);
    }

    public boolean compareTheResponseAnswer(ArrayList<String> userResponses, ArrayList<String> correctAnswers) {
        if (userResponses == null || correctAnswers == null) {
            return false;
        }

        if (userResponses.size() != correctAnswers.size()) {
            return false;
        }

        for (int i = 0; i < userResponses.size(); i++) {
            if (!userResponses.get(i).equalsIgnoreCase(correctAnswers.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tabulate(){
        if(this.test == null){
            System.out.println("The Survey should be loaded first");

        }else{
            if(this.test.isTaken()){
                for(int i = 0; i < this.test.getQuestionListSize(); i++){
                    this.test.getQuestion(i).displayTabulate();
                }
            }else{
                System.out.println("Survey should be taken first!");
            }
        }
    }

}
