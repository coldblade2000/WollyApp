package com.snk.wolly.wolly

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Explode
import android.transition.Fade
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_recycle.*
import kotlinx.android.synthetic.main.content_add_recycle.*

class AddRecycle : AppCompatActivity(){
    private var count = 0
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

        val type = bundle?.get("name")
        val name = bundle?.get("name")
        val image = bundle?.get("image")
        val scoreKG = bundle?.get("scoreKG")
        val scoreUnit = bundle?.get("scoreUnit")

//        tvAddName.text =
        val positon = bundle?.get("position");

        tvAddName.text = name.toString()
//        val mipmap : Int
        /*when(type){
            "ivBottle" -> {
                mipmap = R.mipmap.bottle
                tvAddName.text = "Plastic Bottle"
            }
            "ivCap" -> {
                mipmap = R.mipmap.cap
                tvAddName.text = "Plastic Cap"
            }
            "ivGlassBottle" -> {
                mipmap = R.mipmap.glass
                tvAddName.text = "Glass Bottle"

            }
            "ivPaper" -> {
                mipmap = R.mipmap.paper
                tvAddName.text = "Paper"

            }
            "ivTetrapak" -> {
                mipmap = R.mipmap.tetra
                tvAddName.text = "TetraPak"

            }
            "ivBaterias" -> {
                mipmap = R.mipmap.battery
                tvAddName.text = "Battery"

            }
            else -> {
                mipmap = R.mipmap.tetra
                tvAddName.text = "N U L L"

            }

        }*/
        Glide.with(this)
                .load(image)
                .fitCenter()
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

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    setCount(s.toString().toInt())
                }
            }


        })
        fabAdd.setOnClickListener {

            finishAfterTransition()
        }
    }
    private fun setCount(i : Int){
        count = i
    }

}