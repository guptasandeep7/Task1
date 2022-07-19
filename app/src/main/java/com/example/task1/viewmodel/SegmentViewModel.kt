package com.example.task1.viewmodel

import androidx.lifecycle.*
import com.example.task1.model.Segment
import com.example.task1.repo.SegmentRepository
import kotlinx.coroutines.launch

class SegmentViewModel(private val segmentRepository: SegmentRepository) : ViewModel() {

    val getAllSegments: LiveData<List<Segment>> = segmentRepository.allSegments.asLiveData()

    fun insertAll(segmentList: MutableList<Segment>) = viewModelScope.launch {
        segmentRepository.insertAll(segmentList)
    }

    fun deleteAll() = viewModelScope.launch {
        segmentRepository.deleteAll()
    }
}


class SegmentViewModelFactory(private val repository: SegmentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SegmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SegmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}