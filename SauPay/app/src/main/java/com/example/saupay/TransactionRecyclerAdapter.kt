package com.example.saupay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saupaymobile.Transaction


class TransactionRecyclerAdapter(val transactionList:List<Transaction>) : RecyclerView.Adapter<TransactionRecyclerAdapter.PostViewHolder>(){


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val merchantName : TextView = itemView.findViewById<TextView?>(R.id.merchant_name_textView)
        val transactionAmount : TextView = itemView.findViewById<TextView?>(R.id.amount_textView)
        val transactionDate : TextView = itemView.findViewById<TextView?>(R.id.date_textView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_row,parent,false) // ViewHolder için bağlanacak görüntüyü tanımladık
        return  PostViewHolder(itemView) // viewHolder döndürdük --> Recycler View ile Holder bağlandı

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = transactionList[position]

        holder.merchantName.text = currentItem.merchantName
        holder.transactionAmount.text =  currentItem.amount.toPlainString()
        holder.transactionDate.text = currentItem.date

    }
    override fun getItemCount(): Int {
        // rcycler View içerisinde kaç tane satır olacak
        // (val kitapGorselListesi : ArrayList<Bitmap>) ==> bu listenin eleman sayısına ıulaşmak için constructor da listeyi aldık
        return transactionList.size
    }
}