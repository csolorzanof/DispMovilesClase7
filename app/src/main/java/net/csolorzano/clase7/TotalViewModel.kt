package net.csolorzano.clase7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    var total = MutableLiveData<Int>()

    init {
        total.postValue(0)
    }

    fun sumar() {
        total.postValue((total.value ?: 0) + 1)
    }

    fun getTotal(): LiveData<Int>{
        return total
    }
}