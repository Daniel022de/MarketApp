package com.devmasterstudy.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmasterstudy.fitnesstracker.model.Mart

class ListActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: AdapterList
    private lateinit var result: MutableList<Mart>
    private lateinit var emptyTextView: TextView
    private lateinit var buttonAddList: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val rvList = findViewById<RecyclerView>(R.id.rv_list)
        emptyTextView = findViewById(R.id.tv_list_empty)
        buttonAddList = findViewById(R.id.button_add_list)
        toolbar = findViewById(R.id.toolbar_list)
        result = mutableListOf()


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)



        adapter = AdapterList(result, this)

        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)

        rvList.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                emptyTextView.visibility =
                    if (rvList.adapter?.itemCount == 0) View.VISIBLE else View.GONE
                buttonAddList.visibility =
                    if (rvList.adapter?.itemCount == 0) View.VISIBLE else View.GONE
            }
        })

        buttonAddList.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
        }

        Thread {
            val app = application as App
            val dao = app.db.martDao()
            val response = dao.getRegisterByType()
            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()


    }

    private inner class AdapterList(
        private val mart: List<Mart>,
        private val listener: OnListClickListener
    ) : RecyclerView.Adapter<AdapterList.CalcViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalcViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return CalcViewHolder(view)
        }

        override fun getItemCount(): Int = mart.size

        override fun onBindViewHolder(holder: CalcViewHolder, position: Int) {
            val itemCurrent = mart[position]
            holder.bind(itemCurrent)
        }

        private inner class CalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Mart) {

                val food = item.food

                val amount = item.amount

                val unit = item.unit

                val tv = itemView.findViewById<TextView>(R.id.tv_item_list)

                tv.text = Html.fromHtml(
                    getString(R.string.list_response, food, amount, unit),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )



                tv.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, item)
                    true
                }


                tv.setOnClickListener {
                    listener.onClick(item.id)
                }

            }


        }

    }

    override fun onClick(id: Int) {

        val intent = Intent(this, AddActivity::class.java)

        intent.putExtra("updateId", id)
        startActivity(intent)
        finish()
    }

    override fun onLongClick(position: Int, mart: Mart) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .setPositiveButton("Sim") { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.martDao()

                    val response = dao.delete(mart)

                    if (response > 0) {
                        runOnUiThread {
                            result.removeAt(position)
                            adapter.notifyItemRemoved(position)
                            emptyTextView.visibility =
                                if (result.isEmpty()) View.VISIBLE else View.GONE
                            buttonAddList.visibility =
                                if (result.isEmpty()) View.VISIBLE else View.GONE
                        }
                    }
                }.start()

            }
            .create()
            .show()
    }
}