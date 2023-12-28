package com.example.testnav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.model.Folder
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class VocabModel : ViewModel() {
    val state = mutableStateOf<List<Vocab>>(emptyList())

    private val db = FirebaseFirestore.getInstance()
//    init {
//        getData()
//    }
    fun getData(folder:String) {
        viewModelScope.launch {
            state.value = getHelloDocuments(folder)
        }
    }
    fun getBigData() {
        viewModelScope.launch {
            state.value = getAllDocumentsInNotelt()
        }
    }
    //Test

    suspend fun getHelloDocuments(nameFolder:String): List<Vocab> {
        try {

            val subCollectionPath = "Notelt/${nameFolder}/${nameFolder}"

            // Lấy dữ liệu từ subcollection
            val documents = db.collection(subCollectionPath).get().await()

            // Trả về danh sách các đối tượng Vocab
            return documents.documents.mapNotNull { document ->
                val definition = document.getString("defination")
                val heart = document.getBoolean("heart")
                val word = document.getString("word")

                if (word != null && definition != null && heart != null) {
                    Vocab(word, definition, heart)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace()
            return emptyList()
        }
    }
    //Take all
    suspend fun getAllDocumentsInNotelt(): List<Vocab> {
        try {
            val noteltRef = db.collection("Notelt")

            // Lấy tất cả các documents từ collection "Notelt"
            val noteltSnapshot = noteltRef.get().await()

            // Lặp qua từng document để lấy danh sách các subcollections
            val allDocuments = mutableListOf<Vocab>()
            for (document in noteltSnapshot.documents) {
                val subCollectionPath = document.reference.path
                val subCollectionDocuments = db.collection(subCollectionPath).get().await()

                // Thêm documents từ subcollection vào danh sách chung
                allDocuments.addAll(subCollectionDocuments.documents.mapNotNull { subDocument ->
                    val definition = subDocument.getString("defination")
                    val heart = subDocument.getBoolean("heart")
                    val word = subDocument.getString("word")

                    if (word != null && definition != null && heart != null) {
                        Vocab(word, definition, heart)
                    } else {
                        null
                    }
                })
            }
            return allDocuments
        } catch (e: Exception) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace()
            return emptyList()
        }
    }
}


class NoteltModel : ViewModel() {
    val state = mutableStateOf<List<String>>(emptyList())
    private val db = FirebaseFirestore.getInstance()
    init {
        getData()
    }
    private fun getData() {
        viewModelScope.launch {
            state.value = getHomeCollections()
        }
    }


    //Tạo các thư mục trong cho Notelt
    suspend fun createSubCollection(subCollection: String) {
        val firestore = FirebaseFirestore.getInstance()
        try {
            val parentCollectionRef = firestore.collection("Notelt")
            parentCollectionRef.document(subCollection).set(hashMapOf<String, Any>()).await()
        } catch (e: Exception) {
            // Xử lý lỗi nếu cần
        }
        state.value = getHomeCollections()
    }
//    suspend fun getSubCollectionNames(parentCollection: String): List<String> {
//        val db = FirebaseFirestore.getInstance()
//        var subCollectionNames = emptyList<String>()
//        try {
//            val documents = db.collection(parentCollection).listDocuments().await()
//            subCollectionNames = documents.map { it.id }
//        } catch (e: FirebaseFirestoreException) {
//            android.util.Log.d("error", "getSubCollectionNames: $e")
//        }
//        return subCollectionNames
//    }


    suspend fun getHomeCollections(): List<String> {
        return try{
            val query=db.collection("Notelt").get().await()
            val noteltCollections=query.documents.mapNotNull { it.id }
            noteltCollections
        }
        catch (e: Exception){
            emptyList()
        }
    }

    suspend fun deleteSubCollection(subCollectionName: String) {
        try {
            // Xác định đường dẫn của subcollection cần xóa
            val subCollectionPath = "Notelt/$subCollectionName"

            // Xóa subcollection
            val subCollectionRef = db.document(subCollectionPath)
            val documents = subCollectionRef.collection(subCollectionName).get().await()

            // Xóa tất cả các documents trong subcollection
            for (document in documents) {
                subCollectionRef.collection(subCollectionName).document(document.id).delete().await()
            }
            // Xóa subcollection chính
            subCollectionRef.delete().await()
        } catch (e: Exception) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace()
        }
        state.value = getHomeCollections()
    }
    suspend fun addDocumentToSubCollection(subCollectionName: String, documentData: Map<String, Any>) {
        try {
            // Xác định đường dẫn của subcollection
            val subCollectionPath = "Notelt/$subCollectionName"

            // Thêm document vào subcollection
            val subCollectionRef = db.document(subCollectionPath)
            subCollectionRef.collection(subCollectionName).add(documentData).await()
        } catch (e: Exception) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace()
        }
    }








    suspend fun getHelloDocuments(): List<Vocab> {
        try {
            // Xác định đường dẫn của subcollection "hello"
            val subCollectionPath = "Notelt/hello/hello"

            // Lấy dữ liệu từ subcollection
            val documents = db.collection(subCollectionPath).get().await()

            // Trả về danh sách các đối tượng Vocab
            return documents.documents.mapNotNull { document ->
                val word = document.getString("word")
                val definition = document.getString("definition")
                val heart = document.getBoolean("heart")

                if (word != null && definition != null && heart != null) {
                    Vocab(word, definition, heart)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace()
            return emptyList()
        }
    }

}
val db = FirebaseFirestore.getInstance()
suspend fun getDataFromFireStore(): List<Folder> {
    val db = FirebaseFirestore.getInstance()
    var folders = emptyList<Folder>()
    try {
        val querySnapshot = db.collection("Notelt").get().await()
        folders = querySnapshot.documents.map { document ->
            document.toObject(Folder::class.java) ?: Folder()
        }
    } catch (e: FirebaseFirestoreException) {
        android.util.Log.d("error", "getDataFromFireStore: $e")
    }
    return folders
}
suspend fun updateHeartStatusBasedOnWord(nameFolder: String, targetWord: String, newHeartStatus: Boolean) {
    try {
        val subCollectionPath = "Notelt/$nameFolder/$nameFolder"

        // Lấy danh sách các tài liệu trong subcollection
        val documents = db.collection(subCollectionPath).get().await()

        // Duyệt qua từng tài liệu và cập nhật giá trị "heart" dựa trên điều kiện
        for (document in documents.documents) {
            val word = document.getString("word")

            if (word == targetWord) {
                val documentRef = db.collection(subCollectionPath).document(document.id)
                documentRef.update("heart", newHeartStatus).await()
            }
        }
    } catch (e: Exception) {
        // Xử lý nếu có lỗi xảy ra
        e.printStackTrace()
    }
}


