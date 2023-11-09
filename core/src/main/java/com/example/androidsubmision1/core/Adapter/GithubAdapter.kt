package com.example.androidsubmision1

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmission1.core.R

class GithubAdapter(private val userList: List<ItemsItemModel>, private val context: Context) :
    RecyclerView.Adapter<GithubAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userAvatar: ImageView = view.findViewById(R.id.user_avatar)
        val userName: TextView = view.findViewById(R.id.user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false))

    override fun getItemCount(): Int = userList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(userList[position].avatarUrl).into(holder.userAvatar)
        holder.userName.text = userList[position].login

        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(userList[position].login) }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }


}