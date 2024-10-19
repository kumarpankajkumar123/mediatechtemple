package app.mediatech.aggrabandhu.dashboard.pages.home

data class NotificationData(
    val currentPage: Int,
    val `data`: List<Data>,
    val totalItems: Int,
    val totalPages: Int
)

data class Data(
    val content: String,
    val createdAt: String,
    val id: Int,
    val title: String,
    val updatedAt: String
)