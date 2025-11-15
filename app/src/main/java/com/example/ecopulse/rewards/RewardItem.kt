data class RewardItem(
    val partner: String,
    val title: String,
    val expires: String,
    val points: Int,
    val imageUrl: String,
    val description: String,
    val category: String   // "food", "services", "discounts"
)
