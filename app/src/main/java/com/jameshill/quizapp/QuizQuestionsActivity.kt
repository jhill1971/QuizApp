package com.jameshill.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1 //First Question Position
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Calls the parent constructor
        super.onCreate(savedInstanceState)
        //Aligns the xml view to this class
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(constants.USER_NAME)

        mQuestionsList = constants.getQuestion()
        //Log.i("Questions Size", "${questionsList.size}")

        setQuestion()

        //set onClickListeners for responses
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        //set onClickListener for button
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four, 4)
            }
            // Click event for submit button and change question.
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(constants.USER_NAME, mUserName)
                            intent.putExtra(constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    //check if answer is wrong
                    if (question!!.correctOption != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++ //increment correct answers counter
                    }
                    // This is for correct answer
                    answerView(question.correctOption, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }

            }
        }

    }

    private fun setQuestion() {
        //function for setting the question to UI components
        //mCurrentPosition = 1
        val question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        //Check here if the position of question is last then
        // change the text of the button.

        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        }else {
            btn_submit.text = "SUBMIT"
        }

        progress_bar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition/${progress_bar.max}"

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        //A function to set the view of selected option view.
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)

    }

    private fun defaultOptionsView() {
        //A function to set default options view when
        // the new question is loaded or when the answer is reselected.
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

    }


    private fun answerView(answer: Int, drawableView: Int) {
        //A function for answer view which is used to highlight whether
        // the answer is wrong or right.
        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, drawableView)
            }

        }
    }

}
