package com.ironelder.kotlintodolist.component

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ironelder.kotlintodolist.R
import com.ironelder.kotlintodolist.data.RequestTodoRemoteApi
import com.ironelder.kotlintodolist.data.TodoModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                }

            })
        }
    }
}
