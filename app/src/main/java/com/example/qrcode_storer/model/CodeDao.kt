package com.example.qrcode_storer.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CodeDao {

    @Query("SELECT * FROM code")
    fun getAll(): Flow<List<Code>>

    @Insert
    fun insertCode(vararg codes: Code)

//    @Insert
//    fun insertAll(vararg codes: List<Code>)

    @Delete
    fun delete(code: Code)

    @Delete
    fun deleteAll(codes: List<Code>)
}