package com.example.sep

class UserData {
    var username: String = ""
    var useremail: String = ""
    var studentid: String = ""
    var userpassword: String = ""
    var usertype: String = ""
    fun set(name:String,email:String,id:String,password:String,type:String){
        username = name
        useremail = email
        studentid = id
        userpassword = password
        usertype = type
    }
    fun reset(){
        username = ""
        useremail = ""
        studentid = ""
        userpassword = ""
        usertype = ""
    }
    
}
