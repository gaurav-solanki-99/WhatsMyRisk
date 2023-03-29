package com.example.whatsmyrisk.MyAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.whatsmyrisk.R
import java.util.*

class DropDownAdapter(context: Context?, viewResourceId: Int, items: ArrayList<String>) : ArrayAdapter<String?>(context!!, viewResourceId, items as List<String?>) {
    private val MY_DEBUG_TAG = "CustomerAdapter"
    private val items: ArrayList<String>
    private val itemsAll: ArrayList<String>
    private val suggestions: ArrayList<String>
    private val viewResourceId: Int
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val customer: String = items[position]
        if (customer != null) {
            val customerNameLabel = v!!.findViewById<View>(R.id.auto_heading) as TextView

        }
        return v!!
    }

//    override fun getFilter(): Filter {
//        return nameFilter
//    }
//
//    var nameFilter: Filter = object : Filter() {
//        override fun convertResultToString(resultValue: Any): String {
//            return (resultValue as String).getName()
//        }
//
//        override fun performFiltering(constraint: CharSequence): FilterResults {
//            return if (constraint != null) {
//                suggestions.clear()
//                for (customer in itemsAll) {
//                    if (customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(customer)
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = suggestions
//                filterResults.count = suggestions.size
//                filterResults
//            } else {
//                FilterResults()
//            }
//        }
//
//        override fun publishResults(constraint: CharSequence, results: FilterResults) {
//            val filteredList: ArrayList<String> = results.values as ArrayList<String>
//            if (results != null && results.count > 0) {
//                clear()
//                for (c in filteredList) {
//                    add(c)
//                }
//                notifyDataSetChanged()
//            }
//        }
//    }

    init {
        this.items = items
        itemsAll = items.clone() as ArrayList<String>
        suggestions = ArrayList<String>()
        this.viewResourceId = viewResourceId
    }
}