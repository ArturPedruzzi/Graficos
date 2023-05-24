package com.example.code_chalenge.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.code_chalenge.CodeApplication
import com.example.code_chalenge.data.Prod_Dao
import com.example.code_chalenge.data.Prod_Diaria
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.InputStreamReader

class Prod_Diaria_ViewModel(private val prodDao: Prod_Dao): ViewModel() {

    val prodDiariaList: LiveData<List<Prod_Diaria>> = prodDao.getAll()


    fun leitorCVS(context: Context, dataList: MutableList<Prod_Diaria>) {
        val csvStream = context.assets.open("producaodiaria.csv")
        val fileReader = InputStreamReader(csvStream)
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())

        for (percorre in csvParser) {
            val prodDiaria = Prod_Diaria(
                percorre["ID"],
                percorre["TOTAL DE ANIMAIS"].toInt(),
                percorre["1° ORDENHA"].replace(",", ".").toFloat(),
                percorre["2° ORDENHA"].replace(",", ".").toFloat(),
                percorre["TOTAL LITROS DIA"].replace(",", ".").toFloat(),
                percorre["MÉDIA"].replace(",", ".").toFloat(),
                percorre["DATA"]
            )
            dataList.add(prodDiaria)
        }

        insertDadosDatabase(dataList)
    }

    private fun insertDadosDatabase(dataList: List<Prod_Diaria>) {
        viewModelScope.launch(IO) {
            for (prodDiaria in dataList) {
                prodDao.insert(prodDiaria)
            }
        }
    }

    fun dadosBarras(): Pair<List<BarEntry>, List<String>> {
        val dataList = prodDiariaList.value ?: emptyList()

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        for ((index, prodDiaria) in dataList.withIndex()) {
            val barEntry = BarEntry(index.toFloat(), prodDiaria.total_litros)
            entries.add(barEntry)
            labels.add(prodDiaria.id)
        }
        return Pair(entries, labels)
    }



    companion object{

        // application no viewModel
        fun create(application: Application) : Prod_Diaria_ViewModel {
            val dataBaseInstance = (application as CodeApplication).getAppDataBase()
            val dao = dataBaseInstance.userDao()
            return Prod_Diaria_ViewModel(dao)

        }
    }

}
