package com.jonathanl.cartracktestapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonathanl.cartracktestapp.data.model.LoggedInUser

@Database(entities = [LoggedInUser::class], version = 1)
abstract class LoginDatabase: RoomDatabase() {

    abstract fun getLoginDAO(): LoginDAO

}