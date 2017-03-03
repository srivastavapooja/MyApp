package com.pooja.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.io.InputStreamReader;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Pooja's log";
    private CatalogAdapter catalogAdapter = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: ");
        GetCatalog getcatalog = new GetCatalog();
        getcatalog.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        GridView cataloggrid = (GridView)rootview.findViewById(R.id.catalog_grid);
        catalogAdapter = new CatalogAdapter(getActivity(), new String[0]);


        cataloggrid.setAdapter(catalogAdapter);

        cataloggrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String  text = v.getContentDescription().toString();
                //Log.d(TAG, text);
                Intent intent = new Intent(getActivity(), CatalogListActivity.class);
                intent.putExtra(intent.EXTRA_TEXT, text);
                startActivity(intent);

            }
        });


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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }


    public class GetCatalog extends AsyncTask<Void, Void, String[]>{

        @Override
        protected String[] doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String catalogdata = null;

            try{
                URL url = new URL("http://www.avadhlaw.com/pooja/App%20Framework/json/catalog.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null){
                    Log.d(TAG, "input stream null");
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                catalogdata = buffer.toString();
                Log.d(TAG, "catalogdata = "+catalogdata);

                try {
                    JSONObject catalogjson = (new JSONObject(catalogdata)).getJSONObject("Catalog");

                    JSONArray jsonArray = catalogjson.getJSONArray("Catagory");
                    String[] catalog = new String[jsonArray.length()];
                    for (int i=0; i< jsonArray.length();i++) {
                        catalog[i] = jsonArray.getString(i);

                    }

                    return catalog;

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }catch (IOException e){
                e.printStackTrace();
                return null;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute (String[] catalog){
            Log.d(TAG, "onPostExecute: ");
            catalogAdapter.clear();
            catalogAdapter.add(catalog);
            catalogAdapter.notifyDataSetChanged();
            Log.d(TAG, "catalogAdapter length = "+catalogAdapter.getCount());
            for (int i=0; i<catalogAdapter.getCount();i++){
                Log.d(TAG, "onPostExecute: catalogAdapter item"+i+catalogAdapter.getItem(i));
            }
        }


    }
}
