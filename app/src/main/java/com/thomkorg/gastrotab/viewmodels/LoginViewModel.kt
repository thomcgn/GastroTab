package com.thomkorg.gastrotab.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel :ViewModel(){

     var userName by mutableStateOf("")
     var userPassword by mutableStateOf("")

     fun onUserNameChanged(changes:String){
          userName = changes
     }

     fun onPasswordChanges(changes: String){
          userPassword = changes
     }
}