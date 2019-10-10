package com.snk.wolly.wolly

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Explode
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_recycle.*
import kotlinx.android.synthetic.main.content_add_recycle.*

class AddRecycle : AppCompatActivity(){
    private var count = 0
    var name: String? = ""
    var scoreKG: Double?= 0.0
    var scoreUnit: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
//            sharedElementEnterTransition = Fade()

            sharedElementExitTransition = Explode()
        }
        postponeEnterTransition()
        setContentView(R.layout.activity_add_recycle)

        val bundle = intent.extras;

        name = bundle?.getString("name")
        val image = bundle?.getInt("image")
        scoreKG = bundle?.getDouble("scoreKG")
        scoreUnit = bundle?.getDouble("scoreUnit")

//        tvAddName.text =
        val positon = bundle?.get("position")

        tvAddName.text = name.toString()

        Glide.with(this)
                .load(image)
                .dontTransform()
                .into(ivProfileAdd)
        startPostponedEnterTransition()
        val clickListener : View.OnClickListener = View.OnClickListener { view ->
            when (view.id){
                R.id.bCantidadMenos -> {
                    count--
                }
                R.id.bCantidadMas-> {
                    count++
                }
            }
            etCantidad.setText("$count")
        }
        bCantidadMas.setOnClickListener(clickListener)
        bCantidadMenos.setOnClickListener(clickListener)

        etCantidad.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                etPeso.isEnabled = s.isEmpty()

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    setCount(s.toString().toInt())
                    val value : Double = try{s.toString().toDouble()} catch (e: NumberFormatException){ 0.0 }
                    updatePointCounter(value, false)
                }else{
                    setCount(0)
                    updatePointCounter(0.0, false)
                }
            }


        })
        etPeso.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {
                etCantidad.isEnabled = p0.isEmpty()
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val value : Double = try{s.toString().toDouble()} catch (e: NumberFormatException){ 0.0 }

                updatePointCounter(value, true)

            }

        })
        fabAdd.setOnClickListener {
            if( etCantidad.text.isNullOrEmpty() && etPeso.text.isNullOrEmpty()){
                Snackbar.make(fabAdd, "No puedes dejar ambas opciones vacias", Snackbar.LENGTH_SHORT);
            }else if( !etCantidad.text.isNullOrEmpty() && etPeso.text.isNullOrEmpty() ){ // Cantidad
                val intent = Intent()
                intent.putExtra("name", name)
                intent.putExtra("scoreUnit", scoreUnit)
                intent.putExtra("unitCount", etCantidad.text.toString().toDouble())
                setResult(PickRecyclable.UNIT_SUCCESS_RESULT, intent)
                finishAfterTransition()
            }else if( etCantidad.text.isNullOrEmpty() && !etPeso.text.isNullOrEmpty() ){ // Peso
                val intent = Intent()
                intent.putExtra("name", name)
                intent.putExtra("scoreKg", scoreKG)
                intent.putExtra("kgCount", (etPeso.text.toString().toDouble()))

                setResult(PickRecyclable.KG_SUCCESS_RESULT, intent)
                finishAfterTransition()
            }else{
                Snackbar.make(fabAdd, "How did you even manage this?", Snackbar.LENGTH_SHORT);
            }

        }
    }
    private fun setCount(i : Int){
        count = i
    }
    private fun updatePointCounter(count : Double, isWeighable : Boolean){
        val text: String
        if(isWeighable){
            text = "+" + count*scoreKG!! + " puntos!"
        }else{
            text = "+" + count*scoreUnit!! + " puntos!"

        }
        tvPointCounter.text = text

    }

}