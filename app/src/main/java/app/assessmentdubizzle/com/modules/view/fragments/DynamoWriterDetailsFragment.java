package app.assessmentdubizzle.com.modules.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.assessmentdubizzle.com.R;
import app.assessmentdubizzle.com.databinding.FragmentDynamoWriterDetailsBinding;
import app.assessmentdubizzle.com.modules.models.responseModel.ResultsItem;
import app.assessmentdubizzle.com.utils.DateFormatter;
import app.assessmentdubizzle.com.utils.MDToast;
import coil.Coil;
import coil.ImageLoader;
import coil.request.CachePolicy;
import coil.request.ImageRequest;


public class DynamoWriterDetailsFragment extends Fragment {


    private FragmentDynamoWriterDetailsBinding _binding;
    private MDToast mdToast;
    private ResultsItem resultsItem;
    public DynamoWriterDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentDynamoWriterDetailsBinding.inflate(inflater, container, false);
        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.dynamo_writer_details));

        if (getArguments() != null){
            resultsItem = DynamoWriterDetailsFragmentArgs.fromBundle(getArguments()).getResultItem();

            _binding.tvNameValue.setText(resultsItem.getName());
            _binding.tvPrice.setText(resultsItem.getPrice());
            _binding.tvUID.setText(resultsItem.getUid());
            _binding.tvCreatedAt.setText(DateFormatter.Companion.convertStringToDate(resultsItem.getCreated_at()));

            String pictureUrl = resultsItem.getImage_urls().get(0);

            ImageLoader imageLoader = Coil.imageLoader(_binding.getRoot().getContext());
            ImageRequest imageRequest = new ImageRequest.Builder(_binding.getRoot().getContext())
                    .data(pictureUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .allowHardware(false)
                    .error(R.drawable.ic_error_white_24dp)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .target(_binding.ivPicture)
                    .build();
            imageLoader.enqueue(imageRequest);
        }

    }
}