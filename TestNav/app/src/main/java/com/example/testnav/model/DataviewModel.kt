package com.example.testnav.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataviewModel : ViewModel() {
    val state = mutableStateOf<List<Folder>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getDataFromFireStore()
        }
    }
}
suspend fun getDataFromFireStore(): List<Folder> {
    val db = FirebaseFirestore.getInstance()
    var folders = emptyList<Folder>()

    try {
        val querySnapshot = db.collection("folders").get().await()

        folders = querySnapshot.documents.map { document ->
            document.toObject(Folder::class.java) ?: Folder()
        }
    } catch (e: FirebaseFirestoreException) {
        android.util.Log.d("error", "getDataFromFireStore: $e")
    }

    return folders
}