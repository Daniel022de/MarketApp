package com.devmasterstudy.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MartDao {

    @Insert
    fun insert(mart: Mart)


    @Query("SELECT * FROM Mart")
    fun getRegisterByType() : List<Mart>

    @Delete
    fun delete(mart: Mart): Int // FIXME: retorna 1 se deu sucesso

    @Update
    fun update(mart: Mart)

}