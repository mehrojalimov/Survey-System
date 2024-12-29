import java.io.Serializable;
import java.util.*;

public class Matching extends Question implements Serializable {
    private final String  questionType = "Matching";
    private int leftSize;
    private int rightSize;
    private ArrayList <String> leftSide;
    private ArrayList <String> rightSide;
    private static final long serialVersionUID = 1L;


    public Matching(String questionName, int l, int r){
        super(questionName);
        this.leftSide = new ArrayList<>();
        this.rightSide = new ArrayList<>();
        this.leftSize = l;
        this.rightSize = r;
        setQuestionType("Matching");
    }

    public int getLeftSize(){
        return this.leftSize;
    }

    public void setLeftSize(int l){
        this.leftSize = l;
    }

    public int getRightSize(){
        return this.rightSize;
    }

    public void setRightSize(int r){
        this.rightSize = r;
    }

    public void add_item_left_side(String i){
        this.leftSide.add(i);
    }

    public void add_item_right_side(String i){
        this.rightSide.add(i);
    }

    public String get_item_left_side(int index){
        return this.leftSide.get(index);
    }

    public String get_item_right_side(int index){
        return this.rightSide.get(index);
    }

    public void remove_item_from_left_side(int index){
        this.leftSide.remove(index);
    }

    public void remove_item_from_right_side(int index){
        this.rightSide.remove(index);
    }

    public void match(char i, char j) {
        String userR = "" + i+j;
        this.responses.addResponse(userR);
        updateTabulate(userR);
    }

    public void displaySides(){
        for (int i = 0; i < this.leftSide.size(); i++) {
            System.out.println((char) ('A' + i) + ") " + this.leftSide.get(i) + "\t" + (i + 1) + ") " + this.rightSide.get(i));
        }
    }

    @Override
    public void display(){
        System.out.println(this.questionName);
        System.out.println("The matching should be in a form of [Letter][Number]");
        displaySides();
    }

    @Override
    public void takeQuestion() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        display();

        int leftSize = getLeftSize();
        int rightSize = getRightSize();
        char maxLetter = (char) ('A' + leftSize - 1);

        Set<Character> usedLetters = new HashSet<>();
        Set<Character> usedNumbers = new HashSet<>();

        for (int i = 0; i < leftSize; i++) {
            System.out.println("Enter your matching choice (e.g., A1, B2): ");
            String matching = this.scanner.nextLine().trim();

            while (matching.length() != 2 ||
                    matching.charAt(0) < 'A' || matching.charAt(0) > maxLetter ||
                    !Character.isDigit(matching.charAt(1)) ||
                    Integer.parseInt(String.valueOf(matching.charAt(1))) < 1 ||
                    Integer.parseInt(String.valueOf(matching.charAt(1))) > rightSize ||
                    usedLetters.contains(matching.charAt(0)) ||
                    usedNumbers.contains(matching.charAt(1))) {

                System.out.println("Invalid input. Make sure to enter in the format [Letter][Number] (e.g., A1) with each pair unique.");
                matching = this.scanner.nextLine().trim();
            }

            usedLetters.add(matching.charAt(0));
            usedNumbers.add(matching.charAt(1));

            this.match(matching.charAt(0), matching.charAt(1));
        }
    }



    @Override
    public void modify() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println("1. Change question name");
        System.out.println("2. Change left-side matching choices");
        System.out.println("3. Change right-side matching choices");

        String userResponse;
        while (true) {
            userResponse = this.scanner.nextLine().trim();
            if (userResponse.matches("[1-3]")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid option (1, 2, or 3):");
            }
        }

        switch (userResponse) {
            case "1":
                System.out.println("Enter a new question name: ");
                while (true) {
                    String newName = this.scanner.nextLine().trim();
                    if (!newName.isEmpty()) {
                        this.setQuestionName(newName);
                        break;
                    } else {
                        System.out.println("Invalid Input. Please try again:");
                    }
                }
                break;

            case "2":
                System.out.println("Modifying left-side matching choices:");
                modify_sides(this.leftSide, this.rightSide);
                break;

            case "3":
                System.out.println("Modifying right-side matching choices:");
                modify_sides(this.rightSide, this.leftSide);
                break;

            default:
                break;
        }
    }


    public void modify_sides(ArrayList<String> arrayList1, ArrayList<String> arrayList2) {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println("1. Edit the choice");
        System.out.println("2. Add the choice");
        System.out.println("3. Delete the choice");

        String userI;
        while (true) {
            userI = this.scanner.nextLine().trim();
            if (userI.matches("[1-3]")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid option (1, 2, or 3):");
            }
        }

        switch (userI) {
            case "1":
                System.out.println("Which of these choices do you want to edit:");
                for (int i = 0; i < arrayList1.size(); i++) {
                    System.out.println(i + 1 + ") " + arrayList1.get(i));
                }

                int choice_option_index;
                while (true) {
                    try {
                        choice_option_index = Integer.parseInt(this.scanner.nextLine()) - 1;
                        if (choice_option_index >= 0 && choice_option_index < arrayList1.size()) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select a valid option.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                System.out.println("Enter a new choice prompt: ");
                String newChoice = this.scanner.nextLine().trim();

                while (newChoice.isEmpty()) {
                    System.out.println("Choice prompt cannot be empty. Please enter a valid prompt:");
                    newChoice = this.scanner.nextLine().trim();
                }
                arrayList1.set(choice_option_index, newChoice);

            case "2":
                System.out.println("Add the new choice for one side:");
                String newChoice1 = this.scanner.nextLine().trim();

                while (newChoice1.isEmpty()) {
                    System.out.println("Choice for the first side cannot be empty. Please enter a valid prompt:");
                    newChoice1 = this.scanner.nextLine().trim();
                }
                arrayList1.add(newChoice1);

                System.out.println("Also add the new choice for the other side for matching:");
                String newChoice2 = this.scanner.nextLine().trim();

                while (newChoice2.isEmpty()) {
                    System.out.println("Choice for the second side cannot be empty. Please enter a valid prompt:");
                    newChoice2 = this.scanner.nextLine().trim();
                }
                arrayList2.add(newChoice2);
                break;

            case "3":
                delete_choice_in_array(arrayList1);
                System.out.println("Also, to make sure the same size, delete one item from the other side:");
                delete_choice_in_array(arrayList2);
                break;

            default:
                break;
        }
    }


    public void delete_choice_in_array(ArrayList<String> arrayList) {
        if (arrayList.isEmpty()) {
            System.out.println("There are no choices to delete.");
            return;
        }

        System.out.println("Which of these choices do you want to delete:");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(i + 1 + ") " + arrayList.get(i));
        }

        int choice_option_index;
        while (true) {
            try {
                choice_option_index = Integer.parseInt(this.scanner.nextLine()) - 1;
                if (choice_option_index >= 0 && choice_option_index < arrayList.size()) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please select a valid option (1 to " + arrayList.size() + "):");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        arrayList.remove(choice_option_index);
        System.out.println("Choice removed successfully.");
    }

    @Override
    public void displayResponse() {

    }

}