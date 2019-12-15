package com.ironelder.kotlintodolist.component

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
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
                Log.d("TodoTestButton", "Fail Response , ${t.message}, ${t.cause}")
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
                    Log.d("TodoTestButton", "Fail Response , ${t.message}, ${t.cause}")
                }

                override fun onResponse(
                    call: Call<ArrayList<TodoModel>>,
                    response: Response<ArrayList<TodoModel>>
                ) {
                    Log.d("TodoTestButton", "Call Response Success")
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
                            }

                            override fun onResponse(
                                call: Call<TodoModel>,
                                response: Response<TodoModel>
                            ) {
                                println("success")
                                (rv_todo_listview.adapter as? TodoListAdapter)?.addTodoItem(response.body())
                                hideKeyboard(et_input_todo)
                            }

                        })
                }
                else -> {
                    print("")
                    return@setOnEditorActionListener false
                }
            }
            true
        }
    }
}
