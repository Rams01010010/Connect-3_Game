package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: yellow ; 1: red ; 2: Empty
    //game variables
    int activePlayer = 0;
    int gameState[] = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int flagForDrawMatch = 0;
    boolean gameActive = true;
    
    //Views
    TextView gameCurrentState;
    Button playAgainBtn;
    
    //Counter touch function
    public void dropIn(View view)
    {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter] == 2 && gameActive)
        {
            counter.setTranslationY(-1000);
            gameState[tappedCounter] = activePlayer;
            if(activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }
            else {
                counter.setImageResource(R.drawable.blue);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000).setDuration(300);
            displayNextPlayerInfo(activePlayer);

            for(int[] winningPostion : winningPositions){
                if(gameState[winningPostion[0]] == gameState[winningPostion[1]] && gameState[winningPostion[1]] == gameState[winningPostion[2]] && gameState[winningPostion[0]] != 2){

                    gameActive = false;

                    String winner = "";
                    if(activePlayer == 1)
                        winner = "Yellow";
                    else
                        winner = "Blue";

                    gameCurrentState.setText(winner+" has won!");

                    playAgainBtn.setVisibility(View.VISIBLE);

                    //temp
                    flagForDrawMatch = 0;
                    return;
                }
            }
            flagForDrawMatch++;
            Log.i("Flag : ",String.valueOf(flagForDrawMatch));
        }
        if(flagForDrawMatch == 9 && gameActive){
            flagForDrawMatch = 0;
            gameCurrentState.setText("Draw Match!");
            playAgainBtn.setVisibility(View.VISIBLE);
        }
    }

    public void displayNextPlayerInfo(int player) {
        if (player == 1)
            gameCurrentState.setText("Blue has to play");
        else
            gameCurrentState.setText("Yellow has to play");
    }

    public void playAgain(View view)
    {
        playAgainBtn.setVisibility(View.INVISIBLE);
        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);
       for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for(int i = 0; i<gameState.length;i++)
            gameState[i] = 2;
        activePlayer = 0;
        gameActive = true;
        gameCurrentState.setText("Yellow has to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameCurrentState = (TextView) findViewById(R.id.gameCurrentStateText);
        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
    }
}