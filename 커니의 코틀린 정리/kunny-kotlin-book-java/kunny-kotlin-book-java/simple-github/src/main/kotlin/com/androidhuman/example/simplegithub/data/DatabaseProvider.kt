package com.androidhuman.example.simplegithub.data

import android.arch.persistence.room.Room
import android.content.Context

private var instance : SimpleGithubDatabase? = null

fun provideSearchHistoryDao(context: Context): SearchHistoryDao = provideDatabase(context).searchHistoryDao()

private fun provideDatabase(context: Context) : SimpleGithubDatabase {
    if(instance == null) {
        instance = Room.databaseBuilder(context.applicationContext,
                SimpleGithubDatabase::class.java, "simple_github.db").build()
    }
    return instance!!
}