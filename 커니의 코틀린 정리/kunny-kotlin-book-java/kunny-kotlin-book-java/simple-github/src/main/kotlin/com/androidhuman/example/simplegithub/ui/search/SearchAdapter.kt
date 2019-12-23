package com.androidhuman.example.simplegithub.ui.search

import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.api.model.GithubRepo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidhuman.example.simplegithub.ui.GlideApp
import kotlinx.android.synthetic.main.item_repository.view.*

import java.util.ArrayList

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.RepositoryHolder>() {

    private var items: MutableList<GithubRepo> = ArrayList()

    private val placeholder = ColorDrawable(Color.GRAY)

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(parent)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repo = items[position]

        with(holder.itemView) {
            GlideApp.with(context)
                    .load(repo.owner.avatarUrl)
                    .placeholder(placeholder)
                    .into(ivItemRepositoryProfile)

            tvItemRepositoryName.text = repo.fullName
            tvItemRepositoryLanguage.text = if (TextUtils.isEmpty(repo.language))
                context.getText(R.string.no_language_specified)
            else
                repo.language

            setOnClickListener {
                if (null != listener) {
                    listener!!.onItemClick(repo)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<GithubRepo>) {
        this.items = items.toMutableList()
    }

    fun setItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun clearItems() {
        this.items.clear()
    }

    class RepositoryHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false))

    interface ItemClickListener {
        fun onItemClick(repository: GithubRepo)
    }
}