import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public abstract class Question implements Serializable {
    protected ResponseCorrectAnswer responses = new ResponseCorrectAnswer();
    protected String questionName;
    protected String questionType;
    protected ArrayList <String> responseOptions;
    transient Scanner scanner = new Scanner(System.in);
    private int userResponseNumber;
    protected HashMap<String, Integer> tabulateData = new HashMap<>();


    private static final long serialVersionUID = 1L;

    public Question(String questionName){
        this.questionName = questionName;
        this.responseOptions = new ArrayList<>();
        this.responses = new ResponseCorrectAnswer();
    }

    public String getQuestionName(){
        return this.questionName;
    }

    public boolean isTaken(){
        return this.responses.isTaken();
    }

    public void setQuestionName(String qName){
        this.questionName = qName;
    }

    public String getQuestionType(){
        return this.questionType;
    }

    public void setQuestionType(String qType){
        this.questionType = qType;
    }

    public void display(){
    }

    public void addResponseOption(String s){
        this.responseOptions.add(s);
    }

    public void removeResponseOption(int r_index){
        this.responseOptions.remove(r_index);
    }

    public void setResponseOptions(int r_index, String new_response){
        this.responseOptions.set(r_index, new_response);
    }

    public String getResponseOptions(int r_index){
        return this.responseOptions.get(r_index);
    }

    public void takeQuestion(){
        return;
    }


    public void modify() {
        return;
    }

    public void displayResponse(){
        return;
    }

    public void addUserResponse(String res){
        this.responses.addResponse(res);
    }

    public ArrayList<String> getUserResponseArray(){
        return this.responses.getResponseAnswer();
    }

    public int getUserResponseNumber(){
        return this.userResponseNumber;
    }

    public void setUserResponseNumber(int r_number){
        this.userResponseNumber = r_number;
    }

    public void setUpTabulate(){

    }

    public void updateTabulate(String response){
        if(this.tabulateData.containsKey(response)){
            int new_value = this.tabulateData.get(response) + 1;
            this.tabulateData.replace(response, new_value);
        }
        else{
            this.tabulateData.put(response, 1);
        }
    }

    public void displayTabulate(){
        System.out.println(this.questionName);
        for (Map.Entry<String, Integer> entry : this.tabulateData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
