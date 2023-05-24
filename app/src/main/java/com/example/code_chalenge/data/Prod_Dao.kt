package com.example.code_chalenge.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Prod_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // Inserir dados
    fun insert(prodDiaria: Prod_Diaria)


    @Query("Select * from prod_diaria")
    fun getAll(): LiveData<List<Prod_Diaria>>    // Selecionar todos os dados e lista-los



}

// Acessará os dados