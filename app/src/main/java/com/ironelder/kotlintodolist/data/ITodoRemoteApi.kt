package com.ironelder.kotlintodolist.data

import retrofit2.Call
import retrofit2.http.*

interface ITodoRemoteApi {

    @GET("todos")
    fun requestGetAllTodoList(): Call<ArrayList<TodoModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("todos")
    fun requestCreateTodoItem(@Field("content") data:String): Call<TodoModel>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("todos/{itemId}")
    fun requestUpdateContentTodoItem(@Path("itemId") itemId:Int, @Field("content") data:String): Call<TodoModel>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @DELETE("todos/{itemId}")
    fun requestDeleteTodoItem(@Path("itemId") itemId:Int): Call<ArrayList<TodoModel>>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("todos/{itemId}")
    fun requestUpdateStatusTodoItem(@Path("itemId") itemId:Int, @Field("done") data:Boolean): Call<TodoModel>

}