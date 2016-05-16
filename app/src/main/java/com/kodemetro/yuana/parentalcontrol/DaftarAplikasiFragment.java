package com.kodemetro.yuana.parentalcontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kodemetro.yuana.parentalcontrol.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuana on 5/3/16.
 */
public class DaftarAplikasiFragment extends Fragment {

    public  RecyclerView        mRecView;
    public LoadApplications     loadApplicationsTask = null;

    private List<AppInfo>       listApps = null;
    private ItemAdapter         mAdapter;
    private PackageManager      packageManager = null;
    private Context             mContext;
    private ProgressDialog      progress = null;
    private SharedPreferences   sPref;


    public DaftarAplikasiFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();

        sPref = ParentalApplication.getInstance().getSharedPreferences();

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
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecView.setLayoutManager(llm);


        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        public CheckBox checkApp;

        public View root;

        public ViewHolder(View v){
            super(v);

            root = v;

            txtName     = (TextView) v.findViewById(R.id.txtName);
            txtDesc     = (TextView) v.findViewById(R.id.txtDesc);
            imgIcon     = (ImageView)v.findViewById(R.id.imgIcon);
            checkApp    = (CheckBox) v.findViewById(R.id.checkApp);

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
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final  AppInfo la   = mApp.get(position);

            holder.txtName.setText(la.getAppName());
            holder.txtDesc.setText(la.getVersionName());
            holder.imgIcon.setImageDrawable(la.getAppIcon());
            holder.checkApp.setChecked(la.isSelected());

            holder.root.setTag(position);
            holder.checkApp.setTag(la);

            holder.checkApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    AppInfo app = (AppInfo) cb.getTag();

                    app.setSelected(cb.isChecked());
                    la.setSelected(cb.isChecked());

                    saveListApps();
                }
            });

            holder.root.setOnClickListener(this);
        }

        public void saveListApps(){

            StringBuilder sb = new StringBuilder();

            for (AppInfo app : mApp){
                if (app.isSelected == true){
                    sb.append(app.getPackageName()).append(";");
                }
            }

            sPref.edit().putString("apps_to_lock", sb.toString()).commit();

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

    public boolean isSystemApp(String packageName) {
        try {
            // Get packageinfo for target application
            PackageInfo targetPkgInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            // Get packageinfo for system package
            PackageInfo sys = packageManager.getPackageInfo(
                    "android", PackageManager.GET_SIGNATURES);
            // Match both packageinfo for there signatures
            return (targetPkgInfo != null && targetPkgInfo.signatures != null && sys.signatures[0]
                    .equals(targetPkgInfo.signatures[0]));
        } catch (PackageManager.NameNotFoundException e) {
            return false;
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

                String[] apps_to_lock = sPref.getString("apps_to_lock", "").split(";");

                List<PackageInfo> packages = packageManager.getInstalledPackages(0);
                for (PackageInfo pkgInfo : packages){

                    if (isSystemApp(pkgInfo.packageName)){
                        continue;
                    }

                    AppInfo tmpInfo = new AppInfo();
                    tmpInfo.setAppName(pkgInfo.applicationInfo.loadLabel(packageManager).toString());
                    tmpInfo.setPackageName(pkgInfo.packageName);
                    tmpInfo.setVersionCode(pkgInfo.versionCode);
                    tmpInfo.setVersionName(pkgInfo.versionName);
                    tmpInfo.setAppIcon(pkgInfo.applicationInfo.loadIcon(packageManager));
                    tmpInfo.setSelected(false);

                    for (String lockApp : apps_to_lock){
                        if (lockApp.equals(pkgInfo.packageName)){
                            tmpInfo.setSelected(true);
                        }
                    }

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
