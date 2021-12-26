package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding!!.toolbarFinishExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dao = (application as WorkOutApp).db.userDao()
        addDateToDatabase(dao)
    }
    private fun addDateToDatabase(userDao: UserDao) {
        val c = Calendar.getInstance() // lay cai dong ho thoi gian thuc
        val dateTime = c.time // lay thoi gian
        Log.e("Date: ", "" + dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Khoi tao dinh dang lai thoi gian
        val date = sdf.format(dateTime) // dung sdf de dinh dang lai thoi dan
        Log.e("Format Date: ", "" + date)
        lifecycleScope.launch {
            userDao.insert(UserEntity(date))
        }
    }
}