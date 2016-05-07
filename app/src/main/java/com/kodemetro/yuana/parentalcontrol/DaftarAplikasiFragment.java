package com.kodemetro.yuana.parentalcontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kodemetro.yuana.parentalcontrol.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class DaftarAplikasiFragment extends Fragment {

    public  RecyclerView        mRecView;
    public LoadApplications     loadApplicationsTask = null;

    private List<AppInfo>       listApps = null;
    private ItemAdapter         mAdapter;
    private PackageManager      packageManager = null;
    private Context             mContext;
    private ProgressDialog      progress = null;


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

    @Override
    public void onPause() {
        super.onPause();
        progress.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        progress.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progress.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
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

        public List<AppInfo> mApp;

        ItemAdapter(List<AppInfo> itemApp){
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

            AppInfo la = mApp.get(position);

            holder.txtName.setText(la.getAppName());
            holder.txtDesc.setText(la.getVersionName());
            holder.imgIcon.setImageDrawable(la.getAppIcon());

            holder.root.setTag(position);

            holder.root.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return mApp.size();
        }

        @Override
        public void onClick(View view) {
            AppInfo i = mApp.get((int) view.getTag());

            Toast.makeText(mContext, String.valueOf(i), Toast.LENGTH_SHORT).show();
        }


    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{
                if (listApps != null){
                    listApps.clear();
                }
                else{
                    listApps = new ArrayList<AppInfo>();
                }

                List<PackageInfo> packages = packageManager.getInstalledPackages(0);
                for (PackageInfo pkgInfo : packages){
                    AppInfo tmpInfo = new AppInfo();
                    tmpInfo.setAppName(pkgInfo.applicationInfo.loadLabel(packageManager).toString());
                    tmpInfo.setPackageName(pkgInfo.packageName);
                    tmpInfo.setVersionCode(pkgInfo.versionCode);
                    tmpInfo.setVersionName(pkgInfo.versionName);
                    tmpInfo.setAppIcon(pkgInfo.applicationInfo.loadIcon(packageManager));
                    tmpInfo.setSelected(false);
                    tmpInfo.print();
                    listApps.add(tmpInfo);
                }
                mAdapter = new ItemAdapter(listApps);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            mRecView.setAdapter(mAdapter);

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), null, "Loading...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
