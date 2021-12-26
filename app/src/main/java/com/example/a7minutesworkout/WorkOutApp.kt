package com.example.a7minutesworkout

import android.app.Application

class WorkOutApp:Application() {

    val db by lazy {
        UserDatabase.getInstance(this)
    }

}