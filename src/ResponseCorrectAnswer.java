import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ResponseCorrectAnswer implements Serializable {

    private ArrayList<String> responseAnswer;
    private HashMap<String, ArrayList<String>> correctAnswer;
    transient Scanner scanner = new Scanner(System.in);
    private static final long serialVersionUID = 1L;

    public ResponseCorrectAnswer() {
        this.responseAnswer = new ArrayList<>();
        this.correctAnswer = new HashMap<>();
    }

    public ArrayList<String> getResponseAnswer() {
        return this.responseAnswer;
    }

    public ArrayList<String> getCorrectAnswerOfQuestion(String questionN) {
        return this.correctAnswer.getOrDefault(questionN, new ArrayList<>());
    }

    public void setCorrectAnswer(ArrayList<String> newResponseAnswer) {
        this.responseAnswer = newResponseAnswer;
    }

    public void addResponse(String response) {
        this.responseAnswer.add(response);
    }

    public boolean isTaken() {
        return !responseAnswer.isEmpty();
    }

    public void addCorrectAnswer(String qName, ArrayList<String> answers) {
        this.correctAnswer.put(qName, answers);
    }

    public int getNumberOfCorrectAnswers(String questionN) {
        return getCorrectAnswerOfQuestion(questionN).size();
    }

    public String getCorrectAnswerByIndex(String questionN, int index) {
        ArrayList<String> answers = getCorrectAnswerOfQuestion(questionN);
        if (index >= 0 && index < answers.size()) {
            return answers.get(index);
        }
        return null;
    }

    public void setNewCorrectAnswer(String questionN, int index, String newCorrectA) {
        ArrayList<String> answers = this.correctAnswer.get(questionN);
        if (answers == null) {
            answers = new ArrayList<>();
            this.correctAnswer.put(questionN, answers);
        }
        while (answers.size() <= index) {
            answers.add(null);
        }
        answers.set(index, newCorrectA);
    }

    public void updateTheQuestionName(String qName, String newName) {
        if (this.correctAnswer.containsKey(newName)) {
            System.out.println("Error: A question with the new name already exists.");
            return;
        }
        ArrayList<String> responses = this.correctAnswer.remove(qName);
        if (responses != null) {
            this.correctAnswer.put(newName, responses);
        }
    }

}
