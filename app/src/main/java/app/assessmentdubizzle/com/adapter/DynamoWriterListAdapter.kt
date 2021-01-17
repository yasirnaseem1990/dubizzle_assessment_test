package app.assessmentdubizzle.com.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.assessmentdubizzle.com.databinding.DynamoWriterAdapterLayoutBinding
import app.assessmentdubizzle.com.di.listener.RVItemClickListener
import app.assessmentdubizzle.com.modules.models.responseModel.ResultsItem
import coil.load
import java.util.*


class DynamoWriterListAdapter(listener: RVItemClickListener, val context: Context?
) : RecyclerView.Adapter<DynamoWriterListAdapter.DynamoListViewHolder>() {
    private var dynamoWriterArrayList: ArrayList<ResultsItem>? = null
    private var mContext: Context? = null

    private var dynamoWriterItemClickListener: RVItemClickListener? = null
    private var selectedCategoryPosition = -1

    init {
        this.dynamoWriterItemClickListener = listener
        this.selectedCategoryPosition = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamoListViewHolder {
        mContext = parent.context

        val binding = DynamoWriterAdapterLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DynamoListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: DynamoListViewHolder, position: Int) {
        val dynamoWriterListArrayData = dynamoWriterArrayList?.get(position)
        holder.layoutDynamoWriterBinding.dynamoPojo = dynamoWriterListArrayData

        if (dynamoWriterListArrayData?.image_urls_thumbnails != null &&
            dynamoWriterListArrayData.image_urls_thumbnails.isNotEmpty()
        ){
            val storeImage = dynamoWriterListArrayData.image_urls_thumbnails[0]
            holder.layoutDynamoWriterBinding.ivPicture.apply {
                load(storeImage)
            }
        }
        holder.layoutDynamoWriterBinding.cvDynamoLayout.setOnClickListener {
            dynamoWriterItemClickListener?.onAdapterClickListener(position, holder.itemView, dynamoWriterListArrayData)
        }

    }

    override fun getItemCount(): Int {
        return if (dynamoWriterArrayList != null) {
            dynamoWriterArrayList!!.size
        } else {
            0
        }
    }
    fun setDynamoWriterListData(marketViewListData: ArrayList<ResultsItem>?
    ) {
        mContext = context
        dynamoWriterArrayList = marketViewListData
        notifyDataSetChanged()
    }


    class DynamoListViewHolder(val layoutDynamoWriterBinding: DynamoWriterAdapterLayoutBinding) :
        RecyclerView.ViewHolder(layoutDynamoWriterBinding.root)

}