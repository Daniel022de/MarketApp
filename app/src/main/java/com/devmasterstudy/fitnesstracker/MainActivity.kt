package com.devmasterstudy.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), OnItemClickListener {


    private lateinit var rvMain: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val items = mutableListOf<MainItem>()
        toolbar = findViewById(R.id.toolbar_main)

        val imageToolbar = toolbar.findViewById<ImageView>(R.id.logo)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val animationDrawable =
            ContextCompat.getDrawable(this, R.drawable.cart_animation) as AnimationDrawable
        animationDrawable.isOneShot = false

        imageToolbar.setImageDrawable(animationDrawable)
        imageToolbar.post { animationDrawable.start() }

        items.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.add_list_cart,
                textStringId = R.string.add,
                color = Color.BLACK
            )
        )
        items.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.list,
                textStringId = R.string.list,
                color = Color.BLACK
            )
        )


        val adapter = MainAdapter(items, this)

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)


    }


    override fun onClick(id: Int) {
        when (id) {
            1 -> startActivity(Intent(this, AddActivity::class.java))
            2 -> startActivity(Intent(this, ListActivity::class.java))
        }
    }


    private inner class MainAdapter(
        private val items: MutableList<MainItem>,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }


        override fun getItemCount(): Int = items.size


        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = items[position]
            holder.bind(itemCurrent)
        }


        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: MainItem) {
                val container: ConstraintLayout = itemView.findViewById(R.id.item_conteiner_imc)
                val img = itemView.findViewById<ImageView>(R.id.item_img_icon)
                val name = itemView.findViewById<TextView>(R.id.item_txt_name)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.onClick(item.id)
                }

            }

        }
    }


}