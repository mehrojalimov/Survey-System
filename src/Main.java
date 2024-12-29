import java.util.Objects;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    SurveyManager surveyManager = new SurveyManager();


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Survey");
        System.out.println("2.Test");
        String userInput = scanner.nextLine();
        if(Objects.equals(userInput, "1")){
            SurveyManager surveyManager = new SurveyManager();
            surveyManager.survey_menu();
        }else{
            TestManager testManager = new TestManager();
            testManager.survey_menu();
        }
    }

}
