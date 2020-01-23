package com.example.genericadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T: AdapterObject>(val mList : List<T>) :
    RecyclerView.Adapter<GenericAdapter<T>.ViewHolder<ViewDataBinding>>(), Filterable {

    private var searchList : List<T>
    var listener : AppAdapterListener<T>? = null
    interface AppAdapterListener<T> {
        fun onItemClick(model: T, position: Int)
    }

    init{
        searchList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder<ViewDataBinding> {
        val bind : ViewDataBinding =
            DataBindingUtil.bind(LayoutInflater.from(parent.context).inflate(viewType, parent, false))!!
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position : Int) {
        val model : T = searchList[position]
        holder.getBinding().apply {
            setVariable(BR.model, model)
            executePendingBindings()
            root.setOnClickListener{
                listener?.onItemClick(model,position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return searchList.get(position).layoutId()
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if(charString.isEmpty()) {
                    searchList = mList
                } else {
                    val filteredList = ArrayList<T>()
                    mList.forEach {row ->
                        if (row.isFilterable(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results?.values as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder<V : ViewDataBinding>(private val view : V) : RecyclerView.ViewHolder(view.root){
         fun getBinding() : V {
            return view
        }
    }
}