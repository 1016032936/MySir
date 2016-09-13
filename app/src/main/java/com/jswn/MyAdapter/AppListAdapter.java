package com.jswn.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jswn.MyBean.AppInfo;
import com.jswn.mysir.R;

import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class AppListAdapter extends BaseAdapter {
    List<AppInfo> mList_app;
    Context mContext;
    public AppListAdapter(Context context,List<AppInfo> list_app) {
        mList_app = list_app;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList_app.size();
    }

    @Override
    public Object getItem(int position) {
        return mList_app.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.applist_item,null);
            holder.app_icon = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.app_name_tv = (TextView) convertView.findViewById(R.id.app_name_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.app_icon.setImageDrawable(mList_app.get(position).icon);
        holder.app_name_tv.setText(mList_app.get(position).name);
        return convertView;
    }

    class ViewHolder {
        public ImageView app_icon;
        public TextView app_name_tv;
    }
}
