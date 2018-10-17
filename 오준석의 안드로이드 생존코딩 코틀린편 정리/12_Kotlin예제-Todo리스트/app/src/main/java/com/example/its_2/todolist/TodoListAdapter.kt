package com.example.its_2.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import android.text.format.DateFormat

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>) : RealmBaseAdapter<Todo>(realmResult) {

    // 아이템에 표시하는 뷰를 구성합니다
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val viewHolder: ViewHolder
        val view: View

        // convertView는 아이템을 작성하기 전에는 null이고 그 이후에는 작성했던 view
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todo, parent, false)

            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        if (adapterData != null) {
            val item = adapterData!![position]
            viewHolder.textTextView.text = item.title
            viewHolder.dateTextView.text = DateFormat.format("yyyy/MM/dd", item.date)
        }

        return view
    }

    override fun getItemId(position: Int): Long {
        if (adapterData != null) {
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }

    class ViewHolder(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.text1)
        val textTextView: TextView = view.findViewById(R.id.text2)
    }
}