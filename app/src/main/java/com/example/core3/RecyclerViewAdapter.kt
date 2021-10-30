package com.example.core3

import android.content.SharedPreferences
import android.graphics.Color
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.coroutines.coroutineContext

lateinit var shared : SharedPreferences

class RecyclerViewAdapter(val CountryList: ArrayList<Country>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
       val country = CountryList[position]
        with(holder) {
            CountryNameTv?.let {
                it.text = country.CountryName
            }
            IOCcodeTv?.text = country.IOCcode
            if (country.Gold > 100){
                holder.itemView.setBackgroundColor(Color.GRAY)
            }else{
                holder.itemView.setBackgroundColor(Color.WHITE)
            }

            //add event click for each line
            this.itemView.setOnClickListener{
               val snackbar = Snackbar.make(it,"Gold ="+country.Gold.toString()+" Silver ="+country.Silver.toString()+" Bronze ="+country.Bronze.toString(), Snackbar.LENGTH_LONG).setAction("Action",null)
                snackbar.setActionTextColor(Color.WHITE)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.BLACK)
                snackbar.show()
                val preferences = itemView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = preferences.edit()
                editor.putString("Selected",country.CountryName)
                editor.apply()
            }
        }
    }


    override fun getItemCount(): Int {
        return CountryList.count()
    }


    class ViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView) {
        val CountryNameTv = itemView.findViewById<TextView>(R.id.CountryNameTv)
        val IOCcodeTv = itemView.findViewById<TextView>(R.id.IOCcodeTv)
    }
}
