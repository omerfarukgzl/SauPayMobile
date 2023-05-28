package com.example.saupay.ui.payment.choosecard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saupay.R
import com.example.saupay.model.card.Card

class CardRecyclerAdapter(val cardList:List<Card>) : RecyclerView.Adapter<CardRecyclerAdapter.PostViewHolder>(){

    val frgament = ChooseCardFragment()

    interface OnItemClickListener {
        fun onItemClick(data: Card)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

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
        val bankName = currentItem.bankName!!.split(" ")[0] +" "+ currentItem.bankName!!.split(" ")[1]
        holder.bankName.text = bankName
        holder.cardNumber.text = currentItem.cardNumber
        holder.cardHolderName.text = currentItem.cardHolderName
        holder.cardExpireDate.text = currentItem.cardExpireDate

    }

    override fun getItemCount(): Int {
        // rcycler View içerisinde kaç tane satır olacak
        // (val kitapGorselListesi : ArrayList<Bitmap>) ==> bu listenin eleman sayısına ıulaşmak için constructor da listeyi aldık
        return cardList.size
    }

    fun getItem(position: Int): Card? {
        // Verilerinizi temsil eden bir liste ya da dizi varsa, buradan ilgili öğeyi döndürebilirsiniz.
        return cardList.get(position)
    }

}