package com.ludovic.vimont.lydiarandomuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.lydiarandomuser.model.User

class HomeUserAdapter(private val users: ArrayList<User>): RecyclerView.Adapter<HomeUserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user, parent, false
        )
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = users[position]
        holder.imageViewUserPicture.setImageResource(user.picture)
        holder.textViewUsername.text = user.name
        holder.textViewUserEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewUserPicture: ImageView = itemView.findViewById(R.id.image_view_user_picture)
        val textViewUsername: TextView = itemView.findViewById(R.id.text_view_user_name)
        val textViewUserEmail: TextView = itemView.findViewById(R.id.text_view_user_email)
    }
}