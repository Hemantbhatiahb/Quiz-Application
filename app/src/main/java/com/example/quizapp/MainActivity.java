package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TextView ques_text  , score_text;
    Button trueButton , falseButton ;
    Button cheat_button ;
    int ques_no = 0 ;
    int score= 0 ;
    private int current_ques ;
    private static final String tag ="index" ;
    private static final int intent_request_code =0 ;
    boolean mIsCheater ;

    private truefalse quesBank[] = new truefalse[] {
            new truefalse(R.string.question1 , true) ,
            new truefalse(R.string.question2 , false ),
            new truefalse(R.string.question3 , true) ,
            new truefalse(R.string.question4 , false ),
            new truefalse(R.string.question5 , false ),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( savedInstanceState != null){
            ques_no =savedInstanceState.getInt(tag) ;
            score = savedInstanceState.getInt(tag) ;
            Log.i(tag , "current tag now"+ques_no);
        }
        ques_text = (TextView) findViewById(R.id.question) ;
        trueButton = (Button) findViewById(R.id.true_button) ;
        falseButton =(Button) findViewById(R.id.false_button) ;
        score_text  =(TextView) findViewById(R.id.Score )  ;
        cheat_button = (Button ) findViewById(R.id.cheat_Button) ;

        ques_text.setText(quesBank[ques_no].getQuesId());
        score_text.setText("Score :" +score+"/"+quesBank.length);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQues(true) ;
                updateQues();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQues(false);
                updateQues();
            }
        });

        // cheat button on click listener
        cheat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean actual_answer= quesBank[ques_no].isAns() ;          // retreiving the actual answer
                Toast.makeText(getApplicationContext(),"Cheating is wrong!" ,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this , CheatActivity.class) ;
                intent.putExtra(CheatActivity.check_ans_intent_tag ,actual_answer) ;        // passing the answer to child activity

                startActivityForResult(intent , intent_request_code);           // calling the child activity and passing  request code
            }
        });

    }

    public void checkQues(boolean user_ans){

        boolean correct_ans = quesBank[ques_no].isAns() ;

        String s ;
        if(mIsCheater){
            s = "User is cheater ,No updation in score" ;
        }
        else if(user_ans == correct_ans){
            score = score+1 ;
            s= "Correct!" ;
        }
        else {
           s="Incorrect!" ;
        }

        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    public void updateQues(){

        ques_no = (ques_no+1)%quesBank.length ;
        mIsCheater=false ;
        if(ques_no == 0){
            AlertDialog.Builder alert =new AlertDialog.Builder(this)
                    .setTitle("Game Over!")
                    .setMessage("You scored "+ score+ " points .")
                    .setCancelable(false)
                    .setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }) ;
            alert.show() ;
        }

        current_ques = quesBank[ques_no].getQuesId() ;
        ques_text.setText(current_ques);

        score_text.setText("Score :" +score+"/"+quesBank.length);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

       Log.i(tag , "current tag"+ques_no);
       outState.putInt(tag,ques_no);
       outState.putInt(tag ,score);
    }

    // returnning from the setResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == intent_request_code){
            if (data ==null){
                return;
            }
        }
        mIsCheater = CheatActivity.wasAnswerShown(data) ;
    }
}