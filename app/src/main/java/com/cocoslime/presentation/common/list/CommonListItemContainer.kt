package com.cocoslime.presentation.common.list

import androidx.recyclerview.widget.DiffUtil

sealed interface CommonListItemContainer {
    val identifier: String

    data object Header : CommonListItemContainer {
        override val identifier: String = this::class.java.simpleName
    }

    data class Entry(
        val id: Long,
        val imageUrl: String,
        val content: String,
    ): CommonListItemContainer {
        override val identifier: String = this::class.java.simpleName + id
    }

    data class Footer(
        val message: String,
    ): CommonListItemContainer {
        override val identifier: String = this::class.java.simpleName
    }
}

class ListItemCallback : DiffUtil.ItemCallback<CommonListItemContainer>() {
    override fun areItemsTheSame(
        oldItem: CommonListItemContainer,
        newItem: CommonListItemContainer
    ): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(
        oldItem: CommonListItemContainer,
        newItem: CommonListItemContainer
    ): Boolean {
        return oldItem == newItem
    }
}
