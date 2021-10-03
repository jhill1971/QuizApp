package com.jameshill.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.Toast
import com.jameshill.quizapp.constants.USER_NAME
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //input validation for EditText field
        btn_start.setOnClickListener {
            if (et_name.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
            }else {
                // Create an instance of Intent. Moves from this to the target activity
                val intent = Intent (this, QuizQuestionsActivity::class.java)
                intent.putExtra(constants.USER_NAME, et_name.text.toString())
                //Start the target activity using the intent defined above.
                startActivity(intent)
                //finish the current activity
                finish()
            }
        }
    }
}