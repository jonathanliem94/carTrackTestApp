package com.jonathanl.cartracktestapp.data

import androidx.room.*
import com.jonathanl.cartracktestapp.data.model.LoggedInUser

@Dao
interface LoginDAO {

    @Query("SELECT * FROM loggedinuser WHERE username LIKE :usernameQuery AND " +
            "password LIKE :passwordQuery LIMIT 1")
    suspend fun findUser(usernameQuery: String, passwordQuery: String): LoggedInUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: LoggedInUser)

    @Delete
    suspend fun delete(user: LoggedInUser)

}