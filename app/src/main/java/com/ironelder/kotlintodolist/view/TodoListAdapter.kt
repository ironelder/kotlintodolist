package com.ironelder.kotlintodolist.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironelder.kotlintodolist.data.TodoModel

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoItemHolder>() {
    private var mTodoItemList = arrayListOf<TodoModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemHolder {
        val todoItemView = TodoItem(parent.context)
        return TodoItemHolder(todoItemView)
    }

    override fun getItemCount() = mTodoItemList.size

    override fun onBindViewHolder(holder: TodoItemHolder, position: Int) {
        holder.setData(mTodoItemList[position])
    }

    fun setTodoItemList(todoItemList: ArrayList<TodoModel>?) {
        mTodoItemList.clear()
        if (!todoItemList.isNullOrEmpty()) {
            mTodoItemList.addAll(todoItemList)
        }
        notifyDataSetChanged()
    }

    fun clearTodoItemList() {
        mTodoItemList.clear()
        notifyDataSetChanged()
    }

    fun addTodoItem(todoItem: TodoModel) {
        mTodoItemList.add(todoItem)
        notifyItemInserted(mTodoItemList.size - 1)
    }

    fun addTodoItemList(todoItemList: ArrayList<TodoModel>?) {
        mTodoItemList.addAll(todoItemList ?: arrayListOf())
        notifyItemRangeInserted(mTodoItemList.size - 1, todoItemList?.size ?: 0)
    }

    inner class TodoItemHolder(view: TodoItem) : RecyclerView.ViewHolder(view) {
        private val todoItemView: TodoItem by lazy {
            view
        }

        fun setData(todoData: TodoModel) {
            todoItemView.setData(todoData)
        }
    }


}