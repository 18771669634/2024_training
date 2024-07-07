package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accountbook.R;

import java.util.List;

import javabean.Type;

public class TypeAdapter extends BaseAdapter {

    Context context;
    List<Type> mDatas;
    public int selectPos = 0;  //本次点击的位置
    public TypeAdapter(Context context, List<Type> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size();   // 返回集合长度
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);    // 返回位置
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.recordfrag_gv,parent,false);
        //查找布局当中的控件
        ImageView iv = convertView.findViewById(R.id.recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.record_frag_tv);
        //获取指定位置的数据源
        Type type = mDatas.get(position);
        tv.setText(type.getTypename());

        // 判断当前位置是否为选中位置，如果是选中位置，就设置为红颜色的图片，否则为灰色图片
        if (selectPos == position) {
            iv.setImageResource(type.getSimageId());
        }else{
            iv.setImageResource(type.getImageId());
        }
        return convertView;
    }
}
