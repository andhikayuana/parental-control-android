package com.kodemetro.yuana.parentalcontrol;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaftarAplikasiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaftarAplikasiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaftarAplikasiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecyclerView mRecView;

    private List<ListApp> listApps;
    private ItemAdapter mAdapter;

    public class ListApp {
        String name, desc;
        int imgIcon;

        ListApp(String name, String desc, int imgIcon){
            this.name       = name;
            this.desc       = desc;
            this.imgIcon    = imgIcon;
        }
    }

    public DaftarAplikasiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaftarAplikasiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaftarAplikasiFragment newInstance(String param1, String param2) {
        DaftarAplikasiFragment fragment = new DaftarAplikasiFragment();
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
        View root = inflater.inflate(R.layout.fragment_daftar_aplikasi, container, false);

        mRecView = (RecyclerView) root.findViewById(R.id.recView);
        mRecView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        mRecView.setLayoutManager(llm);

        listApps = new ArrayList<>();
        listApps.add(new ListApp("Nama aplikasi 1", "Deskripsi aplikasi 1", R.drawable.ic_content));
        listApps.add(new ListApp("Nama aplikasi 2", "Deskripsi aplikasi 2", R.drawable.ic_help));
        listApps.add(new ListApp("Nama aplikasi 3", "Deskripsi aplikasi 3", R.drawable.ic_list));

        mAdapter = new ItemAdapter(listApps);
        mRecView.setAdapter(mAdapter);



        return root;
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
/*        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtName;
        public TextView txtDesc;
        public ImageView imgIcon;

        public View root;

        public ViewHolder(View v){
            super(v);

            root = v;

            txtName     = (TextView) v.findViewById(R.id.txtName);
            txtDesc     = (TextView) v.findViewById(R.id.txtDesc);
            imgIcon     = (ImageView)v.findViewById(R.id.imgIcon);


        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{

        public List<ListApp> mApp;

        ItemAdapter(List<ListApp> itemApp){
            this.mApp = itemApp;

        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            ListApp la = listApps.get(position);

            holder.txtName.setText(la.name);
            holder.txtDesc.setText(la.desc);

            holder.root.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return listApps.size();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity().getApplicationContext(), "Halo", Toast.LENGTH_SHORT).show();
        }
    }
}
