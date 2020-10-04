package com.jonathanl.cartracktestapp.ui.userdisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonathanl.cartracktestapp.R
import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import kotlinx.android.synthetic.main.recycler_list_view.view.*

class UserDisplayViewAdapter: RecyclerView.Adapter<UserDisplayViewAdapter.ViewHolder>() {

    private var dataSet: List<WebsiteUser> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_view, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setNewDataSet(newData: List<WebsiteUser>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private val cardView: CardView): RecyclerView.ViewHolder(cardView) {
        internal fun bind(userdata: WebsiteUser) {
            cardView.apply {
                name_text.text = resources.getString(R.string.display_name, userdata.name)
                username_text.text = resources.getString(R.string.display_username, userdata.username)
                email_text.text = resources.getString(R.string.display_email, userdata.email)
                phone_text.text = resources.getString(R.string.display_phone, userdata.phoneNumber)
                website_text.text = resources.getString(R.string.display_website, userdata.website)
                company_name_text.text = resources.getString(
                    R.string.display_company_name,
                    userdata.companyData?.companyName
                )
                company_phrase_text.text = resources.getString(
                    R.string.display_company_phrase,
                    userdata.companyData?.companyCatchPhrase
                )
                company_bs_text.text = resources.getString(
                    R.string.display_company_bs,
                    userdata.companyData?.companyBusiness
                )
                address_full_text.text = resources.getString(
                    R.string.display_address_full,
                    userdata.addressData?.street,
                    userdata.addressData?.suite,
                    userdata.addressData?.city,
                    userdata.addressData?.zipCode
                )
                address_lat_lng_text.text = resources.getString(
                    R.string.display_address_lat_lng,
                    userdata.addressData?.locationData?.lat,
                    userdata.addressData?.locationData?.lng,
                )
            }
        }
    }

}