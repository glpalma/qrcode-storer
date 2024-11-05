package com.example.qrcode_storer.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qrcode_storer.CodeReadingApplication
import com.example.qrcode_storer.model.Code
import com.example.qrcode_storer.model.CodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val codeRepository: CodeRepository) : ViewModel() {
    fun getAll(): Flow<List<Code>> = codeRepository.getAll()

    fun insertCode(data: String) = viewModelScope.launch(Dispatchers.IO) {
        codeRepository.insertCode(Code(data = data))
    }

    fun delete(code: Code) = viewModelScope.launch(Dispatchers.IO) {
        codeRepository.delete(code)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CodeReadingApplication)
                FavoritesViewModel(application.container.codeRepository)
            }
        }
    }
}