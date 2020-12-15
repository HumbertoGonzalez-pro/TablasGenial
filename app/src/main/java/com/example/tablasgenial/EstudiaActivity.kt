package com.example.tablasgenial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_estudia.*
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import java.util.*

class EstudiaActivity : AppCompatActivity(), TextToSpeech.OnInitListener, AdapterView.OnItemClickListener {
    var tts: TextToSpeech? = null
    var listaElementos: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudia)

        listaElementos = mutableListOf<String>()//este es el tipo de datos correcto
        tts = TextToSpeech(this, this)
        Log.i("lenguajes", Locale.getAvailableLocales().toString())

        listaTablas.setOnItemClickListener(this)
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                listaElementos!!.clear()//aquí limpio la lista
                for(i in 0..10){
                    val texto = "$p1 x $i = ${p1*i}"
                    listaElementos!!.add(texto)//aquí añado el nuevo elemento
                }
                val adaptador = ArrayAdapter(application,android.R.layout.simple_list_item_1,listaElementos!!)
                listaTablas.adapter = adaptador
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        val adaptador = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,listaElementos!!)

        listaTablas.adapter = adaptador

    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale("es_MX"))
            if(result != TextToSpeech.LANG_NOT_SUPPORTED && result!=TextToSpeech.LANG_MISSING_DATA)
                Toast.makeText(this, "Todo Correcto", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Lenguaje no soportado", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        if (tts!! != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val textoALeer = listaElementos!!.get(p2)
        tts!!.speak(textoALeer,TextToSpeech.QUEUE_FLUSH,null,null)
    }
}
