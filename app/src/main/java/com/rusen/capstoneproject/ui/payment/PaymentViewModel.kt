package com.rusen.capstoneproject.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : BaseViewModel() {

    private val navigateSuccess = MutableLiveData<Boolean>()
    val navigateSuccessEvent: LiveData<Boolean> = navigateSuccess

    fun attemptPayment(date: String, name: String, cartNumber: String) {
        if (cartNumber.length < 16) {
            showResourceMessage.value = R.string.card_number_message
        } else if (date.length < 5) {
            showResourceMessage.value = R.string.card_date_message
        } else if (name.isBlank()) {
            showResourceMessage.value = R.string.card_name_message
        } else {
            navigateSuccess.value = true
        }
    }
}