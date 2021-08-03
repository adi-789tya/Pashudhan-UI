package com.embed.pashudhan.Adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.embed.pashudhan.DataModels.Pashubazaar
import com.embed.pashudhan.Fragments.PashuBazaarFragment
import com.embed.pashudhan.R

class BazaarAdapter(
    private val mAnimalList: ArrayList<Pashubazaar>,
    var clickListner: OnBazaarItemClickListner,
    private val mContext: PashuBazaarFragment
) :
    RecyclerView.Adapter<BazaarAdapter.MyViewHolder>() {

    companion object {
        private val TAG = "BazaarAdapter==>"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemview =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pashubazar_card_item, parent, false)
        return MyViewHolder(itemview)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val animalData: Pashubazaar = mAnimalList[position]
//        holder.animalTypeBreed.text = "${animalData.animalType}, ${animalData.animalBreed}"
//        holder.animalPrice.text = "${animalData.animalPrice}"
//        holder.animalDistance.text = "${animalData.location?.get(1)}"
        holder.initialize(mAnimalList.get(position), clickListner)
        val imageUri = Uri.parse(animalData.animalImages?.get(1))
        Log.d(TAG, imageUri.toString())
        Glide.with(mContext).load(imageUri).placeholder(R.drawable.download)
            .into(holder.animalImages)


    }

    override fun getItemCount(): Int {
        return mAnimalList.size
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val animalAge: TextView = itemview.findViewById(R.id.pashuBazaar_animalAge)
        val animalTypeBreed: TextView = itemview.findViewById(R.id.pashuBazaar_cardHeading)
        val animalByaat: TextView = itemview.findViewById(R.id.pashuBazaar_animalByaat)
        val animalImages: ImageView = itemview.findViewById(R.id.pashuBazaar_cardImageView)
        val animalMilkCapacity : TextView = itemview.findViewById(R.id.pashuBazaar_animalMilkCapacity)
        val animalMilkQuantity : TextView = itemview.findViewById(R.id.pashuBazaar_animalMilkQuantity)

        val animalPrice: TextView = itemview.findViewById(R.id.pashuBazaar_cardPrice)

        val animalType : TextView = itemview.findViewById(R.id.pashuBazaar_animalType)
        val user_uuid : TextView = itemview.findViewById(R.id.pashuBazaar_user_uuid)
        val animalDistance: TextView = itemview.findViewById(R.id.pashuBazaar_cardDistance)

        fun initialize(item: Pashubazaar, action: OnBazaarItemClickListner)
        {

            animalAge.text = item.animalAge
            animalByaat.text = item.animalByaat
            animalMilkQuantity.text = item.animalMilkQuantity
            animalType.text = item.animalType
            user_uuid.text = item.user_uuid
            animalTypeBreed.text = item.animalBreed
            animalPrice.text = item.animalPrice
            animalMilkCapacity.text = item.animalMilkCapacity
            //animalImages.setImageBitmap(item.animalImages)

            itemView.setOnClickListener{
                action.onItemclick(item, adapterPosition)
            }

        }
    }

}

interface OnBazaarItemClickListner{
    fun onItemclick(item: Pashubazaar, position: Int)
}