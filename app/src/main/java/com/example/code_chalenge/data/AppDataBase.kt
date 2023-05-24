package com.example.code_chalenge.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Prod_Diaria::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao() : Prod_Dao
}


//Criamos a tabela que armazenará os dados localmente, e indicamos que o Dao poderá acessar esses dados