package com.embed.pashudhan.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.embed.pashudhan.Activities.PashuSalesActivity
import com.embed.pashudhan.Adapters.BazaarAdapter
import com.embed.pashudhan.DataModels.Pashubazaar
import com.embed.pashudhan.R
import com.google.firebase.firestore.*

class PashuBazaarFragment : Fragment() {
    private lateinit var mPashuSalesCTA: Button
    private lateinit var mBazaarRecyclerView: RecyclerView
    private lateinit var mBazaarAdapter: BazaarAdapter
    private lateinit var mAnimalList: ArrayList<Pashubazaar>
    private lateinit var PashudhanDB: FirebaseFirestore
    private lateinit var mActivity: FragmentActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pashubazaar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity()
        mBazaarRecyclerView = view.findViewById(R.id.pashuBazaar_recView)
        mBazaarRecyclerView.layoutManager = LinearLayoutManager(mActivity)


        mBazaarRecyclerView.setHasFixedSize(true)
        mAnimalList = arrayListOf()


        mBazaarAdapter = BazaarAdapter(mAnimalList, requireContext())
        mBazaarRecyclerView.adapter = mBazaarAdapter

        mPashuSalesCTA = view.findViewById(R.id.pashuSalesCTA)
        mPashuSalesCTA.setOnClickListener {
            val intent = Intent(requireContext(), PashuSalesActivity::class.java)
            startActivity(intent)
        }
        EventChangeListener()
    }

    private fun EventChangeListener() {
        PashudhanDB = FirebaseFirestore.getInstance()
        PashudhanDB.collection("Pashubazaar")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            mAnimalList.add(dc.document.toObject(Pashubazaar::class.java))
                        }
                    }
                    mBazaarAdapter.notifyDataSetChanged()
                }
            })

    }


}