package com.example.sep

class UserData {
    var username: String = ""
    var useremail: String = ""
    var studentid: String = ""
    var userpassword: String = ""
    fun set(name:String,email:String,id:String,password:String){
        username = name
        useremail = email
        studentid = id
        userpassword = password
    }
    fun reset(){
        username = ""
        useremail = ""
        studentid = ""
        userpassword = ""
    }
    
}
