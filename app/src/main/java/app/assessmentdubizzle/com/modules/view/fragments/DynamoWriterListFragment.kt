package app.assessmentdubizzle.com.modules.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import app.assessmentdubizzle.com.R
import app.assessmentdubizzle.com.adapter.DynamoWriterListAdapter
import app.assessmentdubizzle.com.databinding.FragmentDynamoWriterListBinding
import app.assessmentdubizzle.com.di.listener.RVItemClickListener
import app.assessmentdubizzle.com.modules.models.responseModel.ResultsItem
import app.assessmentdubizzle.com.modules.view.viewModels.DynamoWriterVM
import app.assessmentdubizzle.com.utils.MDToast
import kotlinx.android.synthetic.main.fragment_dynamo_writer_list.*
import java.util.*


class DynamoWriterListFragment : Fragment(), RVItemClickListener {

    private lateinit var mdToast: MDToast
    private var _binding: FragmentDynamoWriterListBinding? = null
    private lateinit var dynamoVM: DynamoWriterVM
    private lateinit var dynamoWriterListAdapter: DynamoWriterListAdapter

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dynamoVM = ViewModelProvider(this).get(DynamoWriterVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDynamoWriterListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.lifecycleOwner = this
        _binding?.dynamoVM = dynamoVM
        activity?.title = getString(R.string.title_market)

        initAdapter()

        dynamoVM.getData().observe(viewLifecycleOwner, {
            if (it.results.isNullOrEmpty()){
                mdToast =
                    MDToast.makeText(_binding?.root?.context, _binding?.root?.context?.resources?.getString(R.string.no_data_found), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS)
                mdToast.show()
            }else {
                _binding?.shimmerFrameLayout?.visibility = View.GONE
                _binding?.rvDynamoWriter?.visibility = View.VISIBLE
                dynamoWriterListAdapter.setDynamoWriterListData(it.results as ArrayList<ResultsItem>?)
            }
        })

        dynamoVM.getShowLoadingResult().observe(viewLifecycleOwner, {
            if (it){
                shimmerFrameLayout.startShimmerAnimation()
            }else {
                if (shimmerFrameLayout.isAnimationStarted) {
                    shimmerFrameLayout.stopShimmerAnimation()
                }
            }
        })

        dynamoVM.getErrorResult().observe(viewLifecycleOwner, {
            mdToast =
                MDToast.makeText(_binding?.root?.context, it, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR)
            mdToast.show()
        })
    }

    private fun initAdapter() {
        _binding?.rvDynamoWriter?.apply {
            val mLayoutManager = LinearLayoutManager(_binding?.root?.context)
            setHasFixedSize(true)
            _binding?.rvDynamoWriter?.layoutManager = mLayoutManager
            _binding?.rvDynamoWriter?.itemAnimator = DefaultItemAnimator()
            _binding?.rvDynamoWriter?.setHasFixedSize(true)
        }
        dynamoWriterListAdapter = DynamoWriterListAdapter(this, _binding?.root?.context)
        _binding?.rvDynamoWriter?.adapter = dynamoWriterListAdapter
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onAdapterClickListener(position: Int, view: View, resultItem: ResultsItem?) {
        Navigation.findNavController(view).navigate(DynamoWriterListFragmentDirections.actionDynamoWriterListFragmentToDynamoWriterDetailsFragment(resultItem))
    }


}