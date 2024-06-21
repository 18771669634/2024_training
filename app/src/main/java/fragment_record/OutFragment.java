package fragment_record;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accountbook.R;

import java.util.List;

import database.AccountBookHelper;
import javabean.Type;


public class OutFragment extends BaseRecordFragment {

    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac.setTypename("餐饮");
        ac.setsImageId(R.mipmap.food_fs);

        // 检索传递过来的 username 信息
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        ac.setUsername(username);
    }

    // 子类继承父类并重写父类方法
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        // 获取数据库中的数据源
        List<Type> outlist = AccountBookHelper.getTypeList(0);
        typeList.addAll(outlist);
        typeAdapter.notifyDataSetChanged();
        typeTv.setText("餐饮");
        typeIv.setImageResource(R.mipmap.food_fs);
    }

    @Override
    public void saveAccountToDB() {
        ac.setKind(0);
        // 备注还未设置
        long insert_re = AccountBookHelper.insertRecord(ac);
    }

}