package com.example.testnav.model
import android.app.AlertDialog
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.Vocab
import com.example.testnav.getDataFromFireStore

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
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
    suspend fun createFolder(title: String) {
        val db = FirebaseFirestore.getInstance()
        try {
            // Tạo một bản ghi mới với title được cung cấp
            val newFolder = hashMapOf(
                "title" to title
                //Thêm các trường khác của Folder nếu có
            )

            // Thêm bản ghi mới vào Firestore
            db.collection("folders").add(newFolder).await()
        } catch (e: FirebaseFirestoreException) {
            android.util.Log.d("error", "createFolder: $e")
        }

        // Sau khi tạo mới, cập nhật lại state
        state.value = getDataFromFireStore()
    }
    suspend fun deleteFoldersWithTitleNone(title:String) {
        val db = FirebaseFirestore.getInstance()
        try {
            val querySnapshot = db.collection("Notelt")
                .whereEqualTo("title", title)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                db.collection("Notelt").document(document.id).delete().await()
            }
        } catch (e: FirebaseFirestoreException) {
            android.util.Log.d("error", "deleteFoldersWithTitleNone: $e")
        }

        // Sau khi xóa, cập nhật lại state
        state.value = getDataFromFireStore()
    }
    suspend fun updateFoldersWithTitleNone(newTitle: String,oldTitle:String) {
        val db = FirebaseFirestore.getInstance()
        try {
            val querySnapshot = db.collection("folders")
                .whereEqualTo("title", oldTitle)
                .get()
                .await()
            for (document in querySnapshot.documents) {
                val documentReference = db.collection("folders").document(document.id)
                documentReference.update("title", newTitle).await()
            }
        } catch (e: FirebaseFirestoreException) {
            android.util.Log.d("error", "updateFoldersWithTitleNone: $e")
        }
        // Sau khi cập nhật, cập nhật lại state
        state.value = getDataFromFireStore()
    }


}
suspend fun getVocabsFromDocument(document: DocumentSnapshot): List<Vocab> {
    val vocabs = mutableListOf<Vocab>()

    // Kiểm tra xem document có thuộc tính "vocabs" hay không
    if (document.contains("vocab")) {
        // Lấy dữ liệu từ field "vocabs"
        val vocabArray = document.get("vocab") as? ArrayList<*>

        // Xử lý từng item trong array "vocabs"
        vocabArray?.forEach { vocabItem ->
            if (vocabItem is Map<*, *>) {
                val word = vocabItem["word"] as? String ?: ""
                val definition = vocabItem["definition"] as? String ?: ""
                val heart = vocabItem["heart"] as? Boolean ?: false

                // Tạo đối tượng Vocab và thêm vào danh sách
                val vocab = Vocab(word, definition, heart)
                vocabs.add(vocab)
            }
        }
    }

    return vocabs
}
suspend fun getVocabsForFolderWithTitle(title: String): List<Vocab> {
    val vocabs = mutableListOf<Vocab>()
    try {
        // Thực hiện truy vấn để lấy document của folder có title là "morning"
        val folderQuerySnapshot =
            Firebase.firestore.collection("folders").whereEqualTo("title", title).get().await()

        // Lấy danh sách document tương ứng
        val folderDocuments = folderQuerySnapshot.documents

        // Kiểm tra xem có document nào thỏa mãn điều kiện không
        if (folderDocuments.isNotEmpty()) {
            // Lấy document đầu tiên (giả sử chỉ có một folder có title là "morning")
            val folderDocument = folderDocuments[0]

            // Lấy danh sách vocabs từ document này
            vocabs.addAll(getVocabsFromDocument(folderDocument))
        }
    } catch (e: Exception) {
        android.util.Log.e("Firestore", "Error getting vocabs for folder: $e")
    }
    return vocabs
}
