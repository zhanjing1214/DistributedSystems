
import java.lang.reflect.Array;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhanjing
 */
public class SurveyModel {
    //store choices
    String[] choice;
    //store the number registered for each choice
    int[] count;
    
    //initialize the survey model
    public SurveyModel() {
        //initialize the choices : ABCD
        choice = new String[4];
        choice[0] = "A";
        choice[1] = "B";
        choice[2] = "C";
        choice[3] = "D";
        
        //initialize the counts with 0
        count = new int[4];
        for(int i = 0; i < count.length; i ++) {
            count[i] = 0;
        }
    }
    
    /**
     * This method adds one to the counds corresponding to the answer
     * @param answer the input answer
     * @return the input answer
     */
    public String submitResult(String answer) {
        for(int i = 0; i < choice.length; i ++) {
            if(choice[i].equalsIgnoreCase(answer)) {
                count[i] ++;
                break;
            }
        }
        return answer;
    }
    
    /**
     * This method returns the result of current counts and choices for a specific format
     * the methos will call the cleanResults method after if there is any result
     * @return 
     */
    public String getResults() {
        String results = "";
        for(int i = 0; i < choice.length; i ++) {
            if(count[i] != 0) {
                results = results + choice[i] + ": " + count[i] + "<br>";
            }
        }
        if(!results.equals("")) {
            results = "The results from the survey are as follows: <br><br>" + results
                    + "<br>These results have been cleared";
            cleanResults();
        } else {
            results = "There are currently no results.";
        }
        return results;
    }
    
    /**
     * clean the results by setting all counts to 0
     */
    public void cleanResults() {
        for(int i = 0; i < choice.length; i ++) {
            count[i] = 0;
        }
    }
}
