package com.jswn.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jswn.mysir.R;

import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class write_adapter extends BaseAdapter {
    private Context mContext;
    private List<String> list_text;
    public write_adapter(Context context,List<String> list_text){
        mContext = context;
        this.list_text = list_text;
    }
    @Override
    public int getCount() {
        return list_text.size();
    }

    @Override
    public Object getItem(int position) {
        return list_text.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder hodler;
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.write_bw_list_item,null);
            hodler = new ViewHolder();
            hodler.showtext = (TextView) convertView.findViewById(R.id.bw_text);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHolder) convertView.getTag();
        }

        hodler.showtext.setText(list_text.get(position));
        return convertView;
    }

    class ViewHolder{
        TextView showtext;
    }
}
