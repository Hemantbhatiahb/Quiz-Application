package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String check_ans_intent_tag ="this_is_intent_tag" ;
    private static final String extra_ans_shown= "fjlas";
    TextView warning_text , answer_text ;
    Button check_ans_button ;
    boolean checkedAnswer =false;
    private static final String tag = "tag_for_onSaveInstanceState" ;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

       warning_text =(TextView) findViewById(R.id.warning_text) ;
       answer_text = (TextView) findViewById(R.id.answer_text) ;
       check_ans_button =(Button) findViewById(R.id.show_ans_Button) ;

       if(savedInstanceState!=null){
           if(savedInstanceState.getBoolean(tag) ==true){
               clickedButton();
               Log.d("index22","after rotation button clicked  "+checkedAnswer) ;
           }
       }

       check_ans_button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                clickedButton();
            }
       });

    }

    private void clickedButton(){
        checkedAnswer = true ;
        boolean ans = getIntent().getBooleanExtra(check_ans_intent_tag,false) ;
        warning_text.setText(" ");
       answer_text.setText(""+ans);
       
    }

    // checking whether user has seen the answer or not
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent intent = new Intent() ;
        intent.putExtra(extra_ans_shown ,isAnswerShown) ;
        setResult(RESULT_OK , intent);       // passing the result to parent activity

    }

    public static boolean wasAnswerShown(Intent result){
        Log.d("index22",result.getBooleanExtra(extra_ans_shown,false)+"");
       return result.getBooleanExtra(extra_ans_shown ,false) ;

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

       outState.putBoolean(tag,checkedAnswer);
       Log.d("index22","button clicked "+checkedAnswer) ;
    }
}
