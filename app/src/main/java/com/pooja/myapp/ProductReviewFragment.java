package com.pooja.myapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductReviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "pooja's log";
    private static  final String REVIEWS = "reviews";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CharSequence[] mReviews;

    private OnFragmentInteractionListener mListener;

    public ProductReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductReviewFragment newInstance(String param1, String param2,CharSequence[] param3) {
        ProductReviewFragment fragment = new ProductReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putCharSequenceArray(REVIEWS, param3);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: param3= "+param3.toString());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mReviews = getArguments().getCharSequenceArray(REVIEWS);
            Log.d(TAG, "onCreate: mReviews="+mReviews.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_product_review, container, false);
        if(getArguments()!= null) {
            String[] reviewstr = new String[mReviews.length];
            for(int i=0; i<mReviews.length;i++){
                reviewstr[i] = mReviews[i].toString();
                Log.d(TAG, "onActivityCreated: reviewstr"+reviewstr[i]);
            }

            ListView list = (ListView) getActivity().findViewById(R.id.reviews);
            List<String> reviews = new ArrayList<String>();
            for (int i=0; i< reviewstr.length;i++){
                reviews.add(reviewstr[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, reviews);
            list.setAdapter(adapter);

        }
        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
