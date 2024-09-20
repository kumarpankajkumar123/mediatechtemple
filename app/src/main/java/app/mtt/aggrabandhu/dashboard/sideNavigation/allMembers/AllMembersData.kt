package app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers

data class AllMembersData(
    val totalItems : Int,
    val totalPages : Int,
    val currentPage : Int,
    val data : List<AllMemberData>
)

data class AllMemberData(
    val id : Int,
    val reference_id : Int,
    val name : String,
    val address : String,
    val district : String,
    val state : String,
    val profileUrl : String ?= null,
)

data class MemberDetails(
    val id : Int,
    val name : String,
    val state : String,
    val district : String,
    val profileUrl : String,
)
