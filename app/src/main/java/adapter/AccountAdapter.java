package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accountbook.R;

import java.util.Calendar;
import java.util.List;

import javabean.Account;

public class AccountAdapter extends BaseAdapter {

    Context context;
    List<Account> mDatas;
    LayoutInflater inflater;
    int year, month, day;

    public AccountAdapter(Context context, List<Account> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);    // 这样不用每次都调用

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 得再细看看，明白也没有特别明白
        ViewHolder holder = null;

        // 检查现有的 View 是否可复用，否则创建新的
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ac_mainlv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Account bean = mDatas.get(position);

        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        holder.moneyTv.setText("￥ " + bean.getMoney());

        if (bean.getYear() == year && bean.getMonth() == month && bean.getDay() == day) {
            holder.timeTv.setText("今天");
        } else {
            holder.timeTv.setText(bean.getTime());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView typeIv;
        TextView typeTv, beizhuTv, timeTv, moneyTv;

        public ViewHolder(View view) {
            typeIv = view.findViewById(R.id.item_ac_mainlv_iv);
            typeTv = view.findViewById(R.id.item_ac_main_tv_title);
            timeTv = view.findViewById(R.id.item_ac_main_tv_time);
            beizhuTv = view.findViewById(R.id.item_ac_main_tv_beizhu);
            moneyTv = view.findViewById(R.id.item_ac_main_tv_money);

        }
    }
}
