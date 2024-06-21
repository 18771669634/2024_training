package fragment_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.accountbook.R;

import java.util.List;

import database.AccountBookHelper;
import javabean.Type;


public class InFragment extends BaseRecordFragment {

    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac.setTypename("薪资");
        ac.setsImageId(R.mipmap.in_wage_fs);

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
        List<Type> inlist = AccountBookHelper.getTypeList(1);
        typeList.addAll(inlist);
        typeAdapter.notifyDataSetChanged();
        typeTv.setText("薪资");
        typeIv.setImageResource(R.mipmap.in_wage_fs);
    }

    @Override
    public void saveAccountToDB() {
        ac.setKind(1);
        // 备注还未设置

        long insert_re = AccountBookHelper.insertRecord(ac);
    }


}