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

/* 将一组 Account 对象填充到一个列表视图（如 ListView）中 */
public class AccountAdapter extends BaseAdapter {

    Context context;
    List<Account> mDatas;
    LayoutInflater inflater;
    int year, month, day;

    public AccountAdapter(Context context, List<Account> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);

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
        // ViewHolder 用于缓存视图中的子视图，减少不必要的 findViewById 操作
        ViewHolder holder = null;

        // 如果 convertView 为 null，则表示没有可以重用的视图，需要创建一个新的视图
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ac_mainlv, parent, false);
            holder = new ViewHolder(convertView);

            // 将 ViewHolder 存储在 convertView 中
            convertView.setTag(holder);
        } else {
            // 获取存储在 convertView 中的 ViewHolder
            holder = (ViewHolder)convertView.getTag();
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

    // 缓存子视图的引用，避免多次调用 findViewById，从而提高性能
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
