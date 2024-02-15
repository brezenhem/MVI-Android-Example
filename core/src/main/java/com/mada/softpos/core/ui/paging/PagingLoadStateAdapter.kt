package com.mada.softpos.core.ui.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mada.softpos.core.R

/**
 * Default adapter for implementing footer for all adapters based on Paging 3 library.
 * Includes the loading progress indicator and the "Retry" button
 */
class PagingLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress)
        private val retry: Button = itemView.findViewById<Button>(R.id.retry)
            .also { it.setOnClickListener { retry.invoke() } }

        fun bind(loadState: LoadState) {
            progressBar.visibility = toVisibility(loadState is LoadState.Loading)
            retry.visibility = toVisibility(loadState !is LoadState.Loading)
        }

        private fun toVisibility(constraint: Boolean): Int = if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}