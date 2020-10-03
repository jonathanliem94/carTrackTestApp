package com.jonathanl.cartracktestapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(primaryKeys = ["username", "country"])
data class LoggedInUser(
        @ColumnInfo(name = "username") val displayName: String,
        @ColumnInfo(name = "password") val password: String,
        @ColumnInfo(name = "country") val country: String
)