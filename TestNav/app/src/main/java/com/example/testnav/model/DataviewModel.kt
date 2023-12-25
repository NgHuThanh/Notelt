package com.example.testnav.model
import android.app.AlertDialog
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataviewModel : ViewModel() {
    val state = mutableStateOf<List<Folder>>(emptyList())

    private val firestore = FirebaseFirestore.getInstance()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getDataFromFireStore()
        }
    }

    suspend fun deleteFoldersWithTitleNone() {
        val db = FirebaseFirestore.getInstance()

        try {
            val querySnapshot = db.collection("folders")
                .whereEqualTo("title", "hh78")
                .get()
                .await()

            for (document in querySnapshot.documents) {
                db.collection("folders").document(document.id).delete().await()
            }
        } catch (e: FirebaseFirestoreException) {
            android.util.Log.d("error", "deleteFoldersWithTitleNone: $e")
        }

        // Sau khi xóa, cập nhật lại state
        state.value = getDataFromFireStore()
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