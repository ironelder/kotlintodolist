package com.ironelder.kotlintodolist.view

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ironelder.kotlintodolist.R
import com.ironelder.kotlintodolist.data.TodoModel
import kotlinx.android.synthetic.main.layout_todo_item.view.*

class TodoItem(context:Context?) : ConstraintLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_todo_item, this, true)
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }
    fun setData(todoItem:TodoModel){
        et_todo_content.setText(todoItem.content)
        tv_todo_date.text = todoItem.updatedAt?:todoItem.createdAt
    }
}