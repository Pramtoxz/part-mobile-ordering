package ahm.parts.ordering.ui.home.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ahm.parts.ordering.R;

public class WheelCalendarAdapter<T extends Displayable> extends BaseAdapter {
    private Activity context;
    private ArrayList<T> datas;

    public WheelCalendarAdapter(Activity context, ArrayList<T> datas) {
        this.datas = datas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            //Create
            convertView = context.getLayoutInflater().inflate(R.layout.item_wheel_calendar, parent, false);
            convertView.setTag(holder = new ViewHolder(convertView));
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvText.setText(getItem(position).display());
        return convertView;
    }

    public class ViewHolder {
        private TextView mTvText;
        private View mRootView;

        public ViewHolder(View rootView) {
            this.mRootView = rootView;
            this.mTvText = (TextView) mRootView.findViewById(R.id.tvWheel);
        }
    }
}