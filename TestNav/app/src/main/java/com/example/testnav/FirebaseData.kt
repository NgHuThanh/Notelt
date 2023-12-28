package com.example.testnav

data class FirebaseData(
    val noe:String
)
data class Folder(val title: String, val vocabs: List<Vocab>?)
data class Vocab(val word: String, val definition: String, val heart: Boolean)


