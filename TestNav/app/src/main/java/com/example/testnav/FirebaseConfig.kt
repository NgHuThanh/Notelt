package com.example.testnav

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val _collectionList = mutableStateOf<List<String>>(emptyList())
    val collectionList: State<List<String>> = _collectionList

    init {
        // Load collection names from Firestore
        loadCollections()
    }
    private fun loadCollections() {
        firestore.collectionGroup("") // Thay thế bằng tên top-level collection của bạn
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val collections = snapshot.documents.mapNotNull { document ->
                        document.reference.parent?.id
                    }
                    _collectionList.value = collections.distinct()
                }
            }
    }
}
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

fun addItemToFirebase(title: String) {
    val db = FirebaseFirestore.getInstance()

    // Convert Folder data class to HashMap
    val item = hashMapOf(
        "title" to title
        // Add other fields as needed
    )

    // Access the "folders" collection and add the folder
    db.collection("folders")
        .add(item)
        .addOnSuccessListener { documentReference ->
            // Handle success
            Log.d("TAG", "Folder added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            // Handle failure
            Log.w("TAG", "Error adding folder", e)
        }
}

class FolderViewModel : ViewModel() {

    // MutableStateFlow để theo dõi danh sách các folder
    val folders = MutableStateFlow<List<Folder>>(emptyList())

    private val db = FirebaseFirestore.getInstance()

    init {
        // Gọi hàm để lấy dữ liệu từ Firebase khi ViewModel được tạo
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        // Truy vấn tất cả các documents từ collection "folders"
        db.collection("folders")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Xử lý lỗi nếu có
                    return@addSnapshotListener
                }

                // Chuyển đổi dữ liệu từ Firebase thành danh sách các Folder
                val foldersList = value?.documents?.mapNotNull {
                    it.toObject(Folder::class.java)
                } ?: emptyList()

                // Cập nhật MutableStateFlow với danh sách mới
                viewModelScope.launch {
                    folders.emit(foldersList)
                }
            }
    }
}