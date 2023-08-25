package stud.gilmon.presentation.ui.profile.coupons

sealed class CouponsScreenState {

    object Initial : CouponsScreenState()

    object Loading : CouponsScreenState()

    data class Coupons(
        val coupons: List<Int>
    ) : CouponsScreenState()
}
