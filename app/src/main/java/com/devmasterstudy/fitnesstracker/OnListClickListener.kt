package com.devmasterstudy.fitnesstracker

import com.devmasterstudy.fitnesstracker.model.Mart

interface OnListClickListener {
    fun onClick(id: Int)
    fun onLongClick(position: Int, mart: Mart)
}