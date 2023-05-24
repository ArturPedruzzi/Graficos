package com.example.code_chalenge

import android.app.Application
import androidx.room.Room
import com.example.code_chalenge.data.AppDataBase

class CodeApplication : Application() {

    private lateinit var dataBase: AppDataBase

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "Prod_Diária"
        ).build()
    }

    fun getAppDataBase(): AppDataBase {
        return dataBase
    }
}
