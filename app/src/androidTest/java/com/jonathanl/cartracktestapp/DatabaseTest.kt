package com.jonathanl.cartracktestapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jonathanl.cartracktestapp.data.LoginDAO
import com.jonathanl.cartracktestapp.data.LoginDatabase
import com.jonathanl.cartracktestapp.data.model.LoggedInUser
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var loginDAO: LoginDAO
    private lateinit var db: LoginDatabase
    private val testUser1 = LoggedInUser("francois", "klsjdiur", "France")

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LoginDatabase::class.java)
            .build()
        loginDAO = db.getLoginDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertUserCorrectly() = runBlocking {
        loginDAO.insert(testUser1)
        val result = loginDAO.findUser("francois", "klsjdiur")
        assertThat(result, CoreMatchers.equalTo(testUser1))
    }

    @Test
    fun findNonExistentUserCorrectly() = runBlocking {
        loginDAO.insert(testUser1)
        val result = loginDAO.findUser("someoneelse", "wrongpass")
        Assert.assertEquals(result, null)
    }

    @Test
    fun userDeletedCorrectly() = runBlocking {
        loginDAO.insert(testUser1)
        loginDAO.delete(testUser1)
        val result = loginDAO.findUser("francois", "klsjdiur")
        Assert.assertEquals(result, null)
    }
}