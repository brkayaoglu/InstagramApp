package com.example.instagramapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramapp.Models.User
import com.example.instagramapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserSearchAdapter(private var mContext : Context,
                        private var userList : List<User>,
                        private var isFragment: Boolean = false) : RecyclerView.Adapter<UserSearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_search, parent,false)
        return UserSearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.username.text = user.getuserName()
        holder.fullname.text = user.getfullName()
        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.profile).into(holder.profileImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        var username : TextView = itemView.findViewById(R.id.username_search)
        var profileImage : CircleImageView = itemView.findViewById(R.id.user_search_profile_image)
        var fullname : TextView = itemView.findViewById(R.id.fullname_search)
    }

}