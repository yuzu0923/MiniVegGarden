// VegetableViewModelを生成するためのクラス
package com.yuzu0923.miniveggarden.viewmodel
// import
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuzu0923.miniveggarden.data.local.VegetableDao

class VegetableViewModelFactory(
    private val vegetableDao: VegetableDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(VegetableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VegetableViewModel(vegetableDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}