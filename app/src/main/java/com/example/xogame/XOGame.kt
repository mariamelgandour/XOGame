package com.example.xogame

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Collections

class XOGame : AppCompatActivity() {
    var counter = 0
    var player1Score = 0
    var player2Score = 0
    lateinit var player1Tv: TextView
    lateinit var player2Tv: TextView
    lateinit var main: ConstraintLayout
    var boardState = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xogame)
        player1Tv = findViewById(R.id.player1_score)
        player2Tv = findViewById(R.id.player2_score)
        main = findViewById(R.id.main)
        boardState = ArrayList(Collections.nCopies(9, ""))
        initializeBoardState()
    }

    fun initializeBoardState() {
//        Log.d("XOGame", "إعادة ضبط اللوحة")
//        boardState = ArrayList(Collections.nCopies(9, ""))
//        counter = 0
//        for (i in 0 until main.childCount) {
//            val view = main.getChildAt(i)
//            if (view is Button) {
//                view.text = ""
//                view.isEnabled = true
//            }
//        }
        Log.d("XOGame", "إعادة ضبط اللوحة")
        boardState = ArrayList(Collections.nCopies(9, "")) // إنشاء قائمة جديدة بحجم 9 وتعبئتها بالقيم الفارغة
        counter = 0

        for (i in 0 until main.childCount) {
            val view = main.getChildAt(i)
            if (view is Button) {
                view.text = ""
                view.isEnabled = true
            }
        }
    }

    private fun putPlayerCodeInBoardState(buttonId: Int, playerCode: String) {
        val buttonIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8
        )

        val index = buttonIds.indexOf(buttonId)
        if (index != -1) {
            boardState[index] = playerCode
        } else {
            Log.e("XOGame", "Invalid move: Button ID not found")
        }
    }

    fun checkWinner(playerCode: String): Boolean {
        for (i in 0..2) {
            if (boardState[i] == playerCode && boardState[i + 3] == playerCode && boardState[i + 6] == playerCode) {
                return true
            }
        }
        for (i in 0..6 step 3) {
            if (boardState[i] == playerCode && boardState[i + 1] == playerCode && boardState[i + 2] == playerCode) {
                return true
            }
        }
        if ((boardState[0] == playerCode && boardState[4] == playerCode && boardState[8] == playerCode) ||
            (boardState[2] == playerCode && boardState[4] == playerCode && boardState[6] == playerCode)
        ) {
            return true
        }
        return false
    }

    private fun showWinToast(player: String) {
        val toast = Toast(this)
        val cardView = CardView(this).apply {
            radius = 40f
            setCardBackgroundColor(Color.WHITE)
            cardElevation = 8f
            layoutParams = ViewGroup.LayoutParams(300, 300)
        }
        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.winner)
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        cardView.addView(imageView)
        toast.view = cardView
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun onPlayerClick(view: View) {
        if (view is Button) {
            val clickedButton = view
            if (clickedButton.text.isNotEmpty()) {
                return
            }
            if (counter % 2 == 0) {
                clickedButton.text = "X"
                putPlayerCodeInBoardState(clickedButton.id, "X")
            } else {
                clickedButton.text = "O"
                putPlayerCodeInBoardState(clickedButton.id, "O")
            }
            counter++
            if (checkWinner("X")) {
                player1Score += 5
                player1Tv.text = player1Score.toString()
                showWinToast("X")
                initializeBoardState()
            } else if (checkWinner("O")) {
                player2Score += 5
                player2Tv.text = player2Score.toString()
                showWinToast("O")
                initializeBoardState()
            } else if (counter == 9) {
                player1Score += 2
                player2Score += 2
                player1Tv.text = player1Score.toString()
                player2Tv.text = player2Score.toString()
                Toast.makeText(this, "Two Players Draw", Toast.LENGTH_LONG).show()
                initializeBoardState()
            }
        }
    }
}