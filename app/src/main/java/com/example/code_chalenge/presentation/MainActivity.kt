package com.example.code_chalenge.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.code_chalenge.CodeApplication
import com.example.code_chalenge.data.AppDataBase
import com.example.code_chalenge.data.Prod_Diaria
import com.example.code_chalenge.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class MainActivity : AppCompatActivity() {


    var dadosList = ArrayList<Prod_Diaria>()

    private lateinit var barChart: BarChart


    // viewModel
    private val viewModel: Prod_Diaria_ViewModel by lazy {
        Prod_Diaria_ViewModel.create(application)
    }


    lateinit var dataBase: AppDataBase


    val dao by lazy {
        dataBase.userDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicializando o dataBase
        dataBase = (application as CodeApplication).getAppDataBase()


        barChart = findViewById(R.id.barchart)


        viewModel.leitorCVS(this, dadosList)




        fun createBarras() {
            // lista que armazena as barras do gráfico
            val entries = ArrayList<BarEntry>()
            // armazena o nome de cada barrra do gráfico
            val labels = ArrayList<String>()
            for ((index, prodDiaria) in dadosList.withIndex()) {
                val barEntry = BarEntry(index.toFloat(), prodDiaria.total_litros)
                entries.add(barEntry)
                labels.add(prodDiaria.id)
            }
            // indica qual o dado mostrado no gráfico
            val dataSet = BarDataSet(entries, "Total de litros")
            val data = BarData(dataSet)
            barChart.data = data
            // personalização gráfico
            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            barChart.xAxis.granularity = 1f
            barChart.xAxis.setCenterAxisLabels(true)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.labelRotationAngle = -45f
            barChart.animateY(1000)
            // ações
            barChart.setTouchEnabled(true)
            barChart.isDragEnabled = true
            barChart.setScaleEnabled(true)
            barChart.setPinchZoom(true)
            // margem
            barChart.setDrawBorders(true)
            barChart.setBorderColor(Color.BLACK)
            barChart.setBorderWidth(1f)
            // nmrsY esquerda
            val yAxis = barChart.axisLeft
            yAxis.textColor = Color.BLACK
            yAxis.textSize = 14f

            barChart.invalidate()
        }
    }

    // listagem dos itens
    fun listFromDataBase() {
        val listObserve = Observer<List<Prod_Diaria>> {
            dao.getAll()
        }
        viewModel.prodDiariaList.observe(this@MainActivity, listObserve)
    }


    // Código para criar apenas uma instância da tabela roomDataBase
    override fun onStart() {
        super.onStart()

        dataBase = (application as CodeApplication).getAppDataBase()

        listFromDataBase()
    }

}

