package com.pooja.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewCartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCartFragment newInstance(String param1, String param2) {
        ViewCartFragment fragment = new ViewCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_cart, container, false);

        ShoppingCartDB cartDB = new ShoppingCartDB(getContext());
        SQLiteDatabase db = cartDB.getReadableDatabase();

        String[] results = {
                cartDB.getProductName(), cartDB.getPrice(), cartDB.getQuantity(),cartDB.getImage()
        };

        Cursor c = db.query(
                cartDB.getTableName(),                     // The table to query
                results,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        Item[] cartitems = new Item[c.getCount()];
        c.moveToFirst();
        for (int i=0; i< c.getCount(); i++){
            c.move(i);
            cartitems[i] = new Item(c.getString(c.getColumnIndexOrThrow(cartDB.getProductName())),
                                    c.getString(c.getColumnIndexOrThrow(cartDB.getPrice())),
                                    c.getString(c.getColumnIndexOrThrow(cartDB.getQuantity())),
                                    c.getString(c.getColumnIndexOrThrow(cartDB.getImage())));
            Log.d("poojas log", "onCreateView: cartitem from db = "+ cartitems[i].print());

        }

        ListView list = (ListView) view.findViewById(R.id.cartitems);
        //List<Item> cartlist = new ArrayList<Item>(Arrays.asList(cartitems));
        CartAdapter adapter = new CartAdapter(getActivity(),R.layout.cartitems,cartitems);
        list.setAdapter(adapter);




        return view;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        public void checkout(View v);
    }


}
