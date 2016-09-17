package com.jswn.MyAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jswn.MyBean.Message_tuling;
import com.jswn.UtilTools.HttpImageUtils;
import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.mysir.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class MyAdapter_list extends BaseAdapter {
    List<Message_tuling> mlist_msg;
    Context context;

    public MyAdapter_list(Context cxn, List<Message_tuling> list_msg) {
        mlist_msg = list_msg;
        context = cxn;
    }

    @Override
    public int getCount() {
        return mlist_msg.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist_msg.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Message_tuling message_tuling = mlist_msg.get(position);
        if (message_tuling.getType() == Message_tuling.Type.INCOMING) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = View.inflate(context, R.layout.sen_msg_item, null);
                viewHolder = new ViewHolder();
                viewHolder.msg = (TextView) convertView.findViewById(R.id.send_msg_tv);

            } else {
                convertView = View.inflate(context, R.layout.from_msg_item, null);
                viewHolder = new ViewHolder();
                viewHolder.msg = (TextView) convertView.findViewById(R.id.form_msg_tv);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.msg.setText(mlist_msg.get(position).getMsg());
        return convertView;
    }

    class ViewHolder {
        TextView msg;
        WebView web_intent;
    }
}
