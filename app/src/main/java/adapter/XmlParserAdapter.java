package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bcp.tspro.R;

import java.util.ArrayList;

import util.XmlEntry;

/**
 * Created by Bill on 2017/4/13.
 * Email androidBaoCP@163.com
 */

public class XmlParserAdapter extends BaseAdapter {

    private ArrayList<XmlEntry> list = null;
    private LayoutInflater mInflater = null;

    public XmlParserAdapter(Context context, ArrayList<XmlEntry> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.xml_content, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.author.setText(list.get(position).getAuthor());
        holder.content.setText(list.get(position).getContent());
        return convertView;
    }

    private final class Holder {
        public TextView title;
        TextView author;
        public TextView content;
    }
}
