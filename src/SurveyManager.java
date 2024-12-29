import java.io.File;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SurveyManager {

	protected Survey survey;
	transient Scanner scanner = new Scanner(System.in);


	public SurveyManager(){
		this.survey = null;
	}

	public void saveSurvey(){
		if (this.survey == null) {
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

			this.survey.setSurveyName(userInput);

			String path = Survey.serialize(this.survey);

			System.out.println("Survey saved successfully at: " + path);
		}
	}


	public void loadSurvey() {
		File folder = new File(Survey.basePath);
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
				String filePath = Survey.basePath + files[Integer.parseInt(userInput) - 1].getName();
				this.survey = Survey.deserialize(filePath);
				break;
			} catch (Exception e) {
				System.out.println("Invalid selection or error occurred. Please try again.");
				userInput = this.scanner.nextLine();
			}
		}
	}


	public void survey_menu() {
		String num = "-1";
		while (!num.equals("8")) {
			System.out.println("1. Create a new Survey");
			System.out.println("2. Display an existing Survey");
			System.out.println("3. Load an existing Survey");
			System.out.println("4. Save the current Survey");
			System.out.println("5. Take the current Survey");
			System.out.println("6. Modify the current Survey");
			System.out.println("7. Tabulate a Survey");
			System.out.println("8. Quit");

			num = this.scanner.nextLine();

			if (!num.matches("[1-8]")) {
				System.out.println("Invalid input! Please choose a valid option (1-7).");
				continue;
			}

			switch (num) {
				case "1":
					String survey_name = "";
					while (survey_name.trim().isEmpty()) {
						System.out.println("Enter your survey name: ");
						survey_name = this.scanner.nextLine().trim();
						if (survey_name.isEmpty()) {
							System.out.println("Survey name cannot be empty. Please try again.");
						}
					}
					this.survey = createSurvey(survey_name);
					addQuestions(this.survey);
					break;
				case "2":
					if (getSurvey() == null) {
						System.out.println("No survey is loaded.");
					} else {
							getSurvey().displaySurvey();
					}
					break;
				case "3":
					loadSurvey();
					break;
				case "4":
					saveSurvey();
					break;
				case "5":
					if (getSurvey() == null) {
						System.out.println("No survey is loaded.");
					} else {
						getSurvey().takeSurvey();
						System.out.println("Survey is taken!");
					}
					break;
				case "6":
					if (getSurvey() == null) {
						System.out.println("No survey is loaded.");
					} else {
						modifySurvey();
					}
					break;
				case "7":
					tabulate();
					break;
				case "8":
					System.out.println("Exiting survey menu...");
					break;
			}
		}
	}


	public void modifySurvey() {
		System.out.println("What question (number) do you wish to modify?");
		if (getSurvey().getQuestionList().isEmpty()) {
			System.out.println("No questions available to modify.");
			return;
		}
		for (int i = 0; i < getSurvey().getQuestionList().size(); i++) {
			System.out.println("Question " + (i + 1) + ": " + getSurvey().getQuestion(i).getQuestionName());
		}

		int user_response = -1;
		while (true) {
			try {
				user_response = Integer.parseInt(this.scanner.nextLine());
				if (user_response < 1 || user_response > getSurvey().getQuestionList().size()) {
					System.out.println("Invalid input! Please enter a number between 1 and " + getSurvey().getQuestionList().size());
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			}
		}
		getSurvey().modifyQuestion(user_response - 1);
	}



	public void addQuestions(Survey survey) {
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
					survey.addTrueFalse();
					break;
				case "2":
					survey.addMultipleChoice();
					break;
				case "3":
					survey.addShortAnswer();
					break;
				case "4":
					survey.addEssay();
					break;
				case "5":
					survey.addNewDate();
					break;
				case "6":
					survey.addMatchingQuestion();
					break;
				case "7":
					System.out.println("Returning to previous menu.");
					break;
				default:
					System.out.println("Invalid choice, please try again.");
			}
		}
	}



	public static Survey createSurvey(String surveyName){
		return new Survey(surveyName);
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey(){
		return this.survey;
	}

	public void tabulate(){
		if(this.survey == null){
			System.out.println("The Survey should be loaded first");

		}else{
			if(this.survey.isTaken()){
				for(int i = 0; i < this.survey.getQuestionListSize(); i++){
					this.survey.getQuestion(i).displayTabulate();
				}
			}else{
				System.out.println("Survey should be taken first!");
			}
		}
	}

}