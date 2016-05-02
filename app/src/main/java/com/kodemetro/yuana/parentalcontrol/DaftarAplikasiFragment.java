package com.kodemetro.yuana.parentalcontrol;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

public class DaftarAplikasiFragment extends Fragment {

    public  RecyclerView            mRecView;
    public LoadApplications         loadApplicationsTask;

    private List<ApplicationInfo>   listApps;
    private ItemAdapter             mAdapter;
    private PackageManager          packageManager;
    private Context                 mContext;


    public DaftarAplikasiFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();

        packageManager = mContext.getPackageManager();

        loadApplicationsTask = new LoadApplications();
        loadApplicationsTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_daftar_aplikasi, container, false);

        mRecView = (RecyclerView) root.findViewById(R.id.recView);
        mRecView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(mContext);

        mRecView.setLayoutManager(llm);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecView.setAdapter(mAdapter);
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

        public List<ApplicationInfo> mApp;

        ItemAdapter(List<ApplicationInfo> itemApp){
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

            ApplicationInfo la = mApp.get(position);

            holder.txtName.setText(la.loadLabel(packageManager));
            holder.txtDesc.setText(la.packageName);
            holder.imgIcon.setImageDrawable(la.loadIcon(packageManager));

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

    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (listApps != null) {
                listApps.clear();
            } else {
                listApps = new ArrayList<ApplicationInfo>();
            }

            listApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            mAdapter = new ItemAdapter(listApps);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mRecView.setAdapter(mAdapter);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
