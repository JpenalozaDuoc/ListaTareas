package com.example.listatareas.model

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

class StoreApp : Application(){
    companion object{
        lateinit var database: UsuarioDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, UsuarioDatabase::class.java,"store_database").build()
    }
}