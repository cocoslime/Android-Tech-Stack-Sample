package com.cocoslime.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil

sealed interface ListItem {
    val identifier: String

    data object Header : ListItem {
        override val identifier: String = this::class.java.simpleName
    }

    data class Entry(
        val id: Long,
        val imageUrl: String,
        val content: String,
    ): ListItem {
        override val identifier: String = this::class.java.simpleName + id
    }

    data class Footer(
        val message: String,
    ): ListItem {
        override val identifier: String = this::class.java.simpleName
    }
}

class ListItemCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(
        oldItem: ListItem,
        newItem: ListItem
    ): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(
        oldItem: ListItem,
        newItem: ListItem
    ): Boolean {
        return oldItem == newItem
    }
}
