package ui.deals.ViewModel

sealed interface DealEvent {
    data object OnAction : DealEvent
    data class OnTitleChange(val value: String) : DealEvent
    data class OnDescriptionChange(val value: String) : DealEvent
    data class OnCategoryChange(val value: List<String>) : DealEvent
    data class OnIsFreeChange(val value: Boolean) : DealEvent
    data class OnPriceChange(val value: String) : DealEvent
    data class OnOfferPriceChange(val value: String) : DealEvent
    data class OnPublishedChange(val value: Boolean) : DealEvent
    data class OnExpirationDateChange(val value: String?) : DealEvent
    data class OnProviderChange(val value: String) : DealEvent
    data class OnProviderUrlChange(val value: String) : DealEvent
    data class OnThumbnailChange(val value: String) : DealEvent
    data class OnImagesChange(val values: List<String>) : DealEvent
    data class OnVideoUrlChange(val value: String) : DealEvent
    data class OnCouponCodeChange(val value: String) : DealEvent
    data class OnTagsChange(val values: List<String>) : DealEvent
    data class OnShippingCostChange(val value: Double) : DealEvent
    data object OnSetDefaultState : DealEvent
}
