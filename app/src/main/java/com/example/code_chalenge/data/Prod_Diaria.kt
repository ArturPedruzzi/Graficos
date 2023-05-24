package com.example.code_chalenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prod_Diaria(
    @PrimaryKey
    val id: String,
    val total_animais: Int,
    val um_ordenha: Float,
    val dois_ordenha: Float,
    val total_litros: Float,
    val mediaLitros: Float,
    val data: String
)


//ID,TOTAL DE ANIMAIS,1° ORDENHA,2° ORDENHA,TOTAL LITROS DIA,MÉDIA

// Irá inserir esses dados na tabela