package com.example.amazonbooks.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Data, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    diffUtil: BaseDiffUtil<T>
) : ListAdapter<T, VH>(diffUtil) {

    private var recyclerView: RecyclerView? = null

    init {
        parentLifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onParentDestroy() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>)
                                .run {
                                    onDestroy()
                                }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onParentStop() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).onStop()
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onParentStart() {
                recyclerView?.run {
                    val first =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val last = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (first in 0..last) {
                        for (i in first..last) {
                            findViewHolderForAdapterPosition(i)?.let {
                                (it as BaseItemViewHolder<*, *>).onStart()
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onStop()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        getItem(position).let {
            holder.bind(it, it.id)
        }
}
