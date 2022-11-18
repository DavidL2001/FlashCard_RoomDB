package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.au22_flashcard.databinding.ActivityAddWordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWordBinding
private lateinit var appDb : AppDatabase
lateinit var backBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getInstance(this)

        binding.saveBtn.setOnClickListener {
            addData()
        }


        backBtn = findViewById(R.id.backBtn)

        backBtn.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}
    }

    private fun addData(){

        val englishWord = binding.newWordEnglish.text.toString()
        val swedishWord = binding.newWordSwedish.text.toString()
        val rollNo = binding.rollNoBtn.text.toString()

        if (englishWord.isNotEmpty() && swedishWord.isNotEmpty() && rollNo.isNotEmpty()) {

            val word = Word(
                null, englishWord, swedishWord, rollNo.toInt()
            )
            GlobalScope.launch (Dispatchers.IO){
                appDb.wordDao().insert(word)

            }
            binding.newWordEnglish.text.clear()
            binding.newWordSwedish.text.clear()
            binding.rollNoBtn.text.clear()

            Toast.makeText(this@AddWordActivity, "korrekt inskrivet", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AddWordActivity, "inkorrekt inskrivet", Toast.LENGTH_SHORT).show()
        }



            }



        }
