package jzhan1.cmu.edu.project4android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by zhanjing on 16/11/10.
 */

public class CurrencyConverter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final CurrencyConverter ma = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                String searchAmount = ((EditText) findViewById(R.id.searchAmount)).getText().toString();
                Spinner spinnerFrom = (Spinner) findViewById(R.id.searchFrom);
                Spinner spinnerTo = (Spinner) findViewById(R.id.searchTo);
//                final TextView answer = (TextView) findViewById(R.id.textView);
                String searchFrom = spinnerFrom.getSelectedItem().toString();
                String searchTo = spinnerTo.getSelectedItem().toString();
//                answer.setText(result);
                GetConvertResult gcr = new GetConvertResult();
                gcr.search(searchAmount, searchFrom, searchTo, ma);
            }
        });
    }

    /**
     * display the result when sent back
     */
    public void resultReady(String result) {
        TextView searchView = (EditText)findViewById(R.id.searchAmount);
        TextView answerView = (TextView)findViewById(R.id.textView);
        if (result != "") {
            answerView.setText("Convert result: " + result);
        } else {
            answerView.setText("Sorry, we are not able to convert.");
        }
        searchView.setText("");
    }
}
