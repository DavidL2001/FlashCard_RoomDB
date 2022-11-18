package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.au22_flashcard.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var wordView : TextView
    var currentWord : Word? = null
    val wordList = WordList()
    private lateinit var job : Job
    private lateinit var binding: ActivityMainBinding
    lateinit var db : AppDatabase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addWordBtn = findViewById<Button>(R.id.addWordBtn)
        addWordBtn.setOnClickListener{

            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)}

        db = Room.databaseBuilder(applicationContext,
        AppDatabase::class.java,
        "words_db")
            .fallbackToDestructiveMigration()
            .build()

        job = Job()



        val list = loadAllWords()

        launch {
            val wordsList = list.await()

            for (word in wordsList) {
                Log.d("!!!", "word: $word")
            }
        }


        db = AppDatabase.getInstance(this)

        wordView = findViewById(R.id.wordTextView)

        showNewWord()

        wordView.setOnClickListener {
            revealTranslation()
        }
        binding.readBtn.setOnClickListener {
            readData()
        }


    }
    fun saveWord(word : Word) {
        launch(Dispatchers.IO) {
            db.wordDao().insert(word)
        }
    }

    fun revealTranslation() {
        wordView.text = currentWord?.english
    }


    fun showNewWord() {

        currentWord = wordList.getNewWord()
        wordView.text = currentWord?.swedish
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }

        return true
    }

    private suspend fun displayData(word: Word){
        withContext(Dispatchers.Main){
            binding.tvEnglishWord.text = word.english
            binding.tvSwedishWord.text = word.swedish

        }
    }

    private fun readData(){

        val rollNo = binding.rollNoBtn2.text.toString()

        if (rollNo.isNotEmpty()){

            lateinit var word: Word

            GlobalScope.launch {
                word = db.wordDao().findByRoll(rollNo.toInt())
                displayData(word)

            }

        }


    }

    fun loadAllWords() : Deferred<List<Word>> =
        async (Dispatchers.IO) {
            db.wordDao().getAll()
        }

}


//Vad ska göras:

//1. skapa en ny aktivitet där ett nytt ord får skrivas in
//2. spara det nya ordet i databasen.

//3. I main activity läs in alla ord från databasen

// (använd coroutiner när ni läser och skriver till databasen se tidigare exempel)











