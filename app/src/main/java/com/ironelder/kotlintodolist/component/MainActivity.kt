package com.ironelder.kotlintodolist.component

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ironelder.kotlintodolist.R
import com.ironelder.kotlintodolist.common.hideKeyboard
import com.ironelder.kotlintodolist.data.RequestTodoRemoteApi
import com.ironelder.kotlintodolist.data.TodoModel
import com.ironelder.kotlintodolist.view.TodoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(rv_todo_listview) {
            adapter = TodoListAdapter()
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            )
        }
        val requestTodoList = RequestTodoRemoteApi.todoRemoteApi.requestGetAllTodoList()
        requestTodoList.enqueue(object : Callback<ArrayList<TodoModel>> {
            override fun onFailure(call: Call<ArrayList<TodoModel>>, t: Throwable) {
                Log.d(LOG_TAG, "Fail Response , ${t.message}, ${t.cause}")
            }

            override fun onResponse(
                call: Call<ArrayList<TodoModel>>,
                response: Response<ArrayList<TodoModel>>
            ) {
                (rv_todo_listview.adapter as? TodoListAdapter)?.setTodoItemList(response.body())
            }

        })
        btn_test_button.setOnClickListener {
            val requestTest = RequestTodoRemoteApi.todoRemoteApi.requestGetAllTodoList()
            requestTest.enqueue(object : Callback<ArrayList<TodoModel>> {
                override fun onFailure(call: Call<ArrayList<TodoModel>>, t: Throwable) {
                    Log.d(LOG_TAG, "Fail Response , ${t.message}, ${t.cause}")
                }

                override fun onResponse(
                    call: Call<ArrayList<TodoModel>>,
                    response: Response<ArrayList<TodoModel>>
                ) {
                    Log.d(LOG_TAG, "Call Response Success")
                    (rv_todo_listview.adapter as? TodoListAdapter)?.setTodoItemList(response.body())
                }

            })
        }

        et_input_todo.setOnEditorActionListener { v, actionId, _event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    RequestTodoRemoteApi.todoRemoteApi.requestCreateTodoItem(v.text.toString())
                        .enqueue(object : Callback<TodoModel> {
                            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                                println("Error ${t.message}")
                                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(
                                call: Call<TodoModel>,
                                response: Response<TodoModel>
                            ) {
                                (rv_todo_listview.adapter as? TodoListAdapter)?.addTodoItem(response.body())
                                hideKeyboard(et_input_todo)
                            }

                        })
                }
                else -> {
                    return@setOnEditorActionListener false
                }
            }
            true
        }
    }
}
