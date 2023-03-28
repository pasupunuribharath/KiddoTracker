import android.net.Uri

data class ChildData(
    var id: String="",
    var name:String="",
    var parentId:String="",
    var email:String="",
    var password:String="",
    var isActive:Boolean=false,
    var image:String="",
    var parent_email:String="",
    var parent_password:String=""
)