package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding!!.toolbarHistoryActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.userDao()
        getAllCompletedDates(dao)
    }
    private fun getAllCompletedDates(userDao: UserDao) {
        lifecycleScope.launch { // chay dưới nền
            userDao.fetchAllDate().collect { allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvListTime?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvListTime?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    // vi adapter can 1 list, vi vậy ta cần tạo một list
                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }
                    // khởi tạo adapter danh sách thời gian
                    val historyAdapter = HistoryAdapter(dates)
                    // dùng rv để hứng adapter từ danh sách đã khởi tạo
                    binding?.rvListTime?.adapter = historyAdapter
                }
                else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvListTime?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}