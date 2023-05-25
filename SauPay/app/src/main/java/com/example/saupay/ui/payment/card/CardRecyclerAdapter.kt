package com.example.saupay.ui.payment.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saupay.R
import com.example.saupay.model.card.Card
import com.example.saupay.model.transaction.Transaction
import com.example.saupay.ui.transactions.TransactionRecyclerAdapter

class CardRecyclerAdapter(val cardList:List<Card>) : RecyclerView.Adapter<CardRecyclerAdapter.PostViewHolder>(){


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bankName: TextView = itemView.findViewById<TextView?>(R.id.bankNameText)
        val cardNumber: TextView = itemView.findViewById<TextView?>(R.id.cardNumberText)
        val cardHolderName: TextView = itemView.findViewById<TextView?>(R.id.cardHolderNameText)
        val cardExpireDate: TextView = itemView.findViewById<TextView?>(R.id.cardExpireDateText)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_row,parent,false) // ViewHolder için bağlanacak görüntüyü tanımladık
        return  PostViewHolder(itemView) // viewHolder döndürdük --> Recycler View ile Holder bağlandı

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = cardList[position]

        holder.bankName.text = currentItem.bankName!!.split(" ")[1]
        holder.cardNumber.text = currentItem.cardNumber
        holder.cardHolderName.text = currentItem.cardHolderName
        holder.cardExpireDate.text = currentItem.cardExpireDate

/*
        holder.merchantName.text = currentItem.
        holder.transactionAmount.text =  currentItem.amount.toPlainString()
        holder.transactionDate.text = currentItem.date*/

    }
    override fun getItemCount(): Int {
        // rcycler View içerisinde kaç tane satır olacak
        // (val kitapGorselListesi : ArrayList<Bitmap>) ==> bu listenin eleman sayısına ıulaşmak için constructor da listeyi aldık
        return cardList.size
    }
}