package app.assessmentdubizzle.com.di.listener

import android.view.View
import app.assessmentdubizzle.com.modules.models.responseModel.ResultsItem


interface RVItemClickListener {
    fun onAdapterClickListener(position:Int, view: View, resultItem: ResultsItem?)
}