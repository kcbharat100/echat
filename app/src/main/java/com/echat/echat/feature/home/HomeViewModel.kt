package com.echat.echat.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.echat.echat.model.Channel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()


    init {
        Log.d("Firebase", "init")
        getChannels()
    }

    private fun getChannels() {
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            if (it.exists()) {
                for (data in it.children) {
                    val channel = Channel(data.key!!, data.value.toString())
                    list.add(channel!!)
                }
            }
            _channels.value = list
        }
    }
}