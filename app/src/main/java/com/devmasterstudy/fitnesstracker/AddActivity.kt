package com.devmasterstudy.fitnesstracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.devmasterstudy.fitnesstracker.model.Mart

class AddActivity : AppCompatActivity() {

    private lateinit var editFood: EditText
    private lateinit var editAmount: EditText
    private lateinit var toolbar: Toolbar
    private lateinit var radioAmount: RadioGroup
    private lateinit var selectedItem: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        editFood = findViewById(R.id.edit_add_food)
        editAmount = findViewById(R.id.edit_add_amount)
        radioAmount = findViewById(R.id.radio_group)

        toolbar = findViewById(R.id.toolbar_add)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val btnSend: Button = findViewById(R.id.btn_food_send)


        radioAmount.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedItem = radioButton.text.toString()
        }


        btnSend.setOnClickListener {
            if (!validate()){
                Toast.makeText(this,R.string.fields_message,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val food = editFood.text.toString()
            val amount = editAmount.text.toString()


            AlertDialog.Builder(this)
                .setTitle(getString(R.string.food_response_title))
                .setMessage(getString(R.string.food_response_message,food,amount,selectedItem))
                .setPositiveButton("Voltar"
            ) { dialog, _ -> dialog.dismiss() }
                .setNegativeButton(R.string.add) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.martDao()
                        val updateId = intent.extras?.getInt("updateId")
                        if (updateId != null) {
                            dao.update(Mart(id = updateId, food = food, amount = amount, unit = selectedItem))
                        } else {
                            dao.insert(Mart(food = food, amount = amount, unit = selectedItem))
                        }

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()
                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken,0)

        }
    }

    private fun openListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }


    private fun validate() : Boolean {
        return (editFood.text.toString().isNotEmpty()
                && editAmount.text.toString().isNotEmpty()
                && !editFood.text.toString().startsWith("0")
                && !editAmount.text.toString().startsWith("0"))
    }
}