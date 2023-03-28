import android.net.Uri

data class ChildAdapterData(

    var name:String?="",
    var id:String?="",
    var email:String?="",
    var imageUri: Uri?=null,
    var isActive:Boolean=false
)
