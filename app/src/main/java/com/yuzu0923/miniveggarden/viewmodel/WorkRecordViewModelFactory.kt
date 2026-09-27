package com.yuzu0923.miniveggarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuzu0923.miniveggarden.data.local.WorkRecordDao


class WorkRecordViewModelFactory(
    private val workRecordDao: WorkRecordDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(WorkRecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkRecordViewModel(workRecordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}