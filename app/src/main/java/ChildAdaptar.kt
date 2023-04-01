import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddotracker.ChildPage
import com.example.kiddotracker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ChildAdaptar(private val ChildList:ArrayList<ChildData>): RecyclerView.Adapter<ChildAdaptar.ChildViewHolder>() {


    interface onItemClickListner{

        fun onItemClick(position:Int)
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
      lateinit var mlistener : onItemClickListner

   fun setOnItemClickListner(listner : onItemClickListner)
   {
       mlistener=listner
   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {



        val ItemView= LayoutInflater.from(parent.context).inflate(R.layout.child_item,parent,false)
        return ChildViewHolder(ItemView,mlistener )

    }

    override fun getItemCount(): Int {
        return ChildList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        val currentItem = ChildList[position]



        holder.child_name.text=currentItem.name
        holder.child_active.text=currentItem.Active.toString()

//        firebaseStorage=FirebaseStorage.getInstance()
//        firebaseStorage.getReference("profile"!!).child(currentItem.id!!).child("profile.jpg"!!)
//            .downloadUrl.addOnSuccessListener {
//
//                Picasso.get().load(it).into(holder.child_image)
//
//            }




    }



    class ChildViewHolder(ItemView :View,listner: onItemClickListner ):RecyclerView.ViewHolder(ItemView){

         val child_image :ImageView= ItemView.findViewById(R.id.child_image)
         val child_name:TextView= ItemView.findViewById(R.id.child_name)
         val child_active:TextView=ItemView.findViewById(R.id.child_active)


        init{

            ItemView.setOnClickListener {

                 listner.onItemClick(adapterPosition )
            }


        }


    }

}