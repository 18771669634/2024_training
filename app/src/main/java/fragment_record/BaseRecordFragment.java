package fragment_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accountbook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.TypeAdapter;
import database.AccountBookHelper;
import javabean.Account;
import javabean.Type;
import utils.BeiZhuDialog;
import utils.KeyBoardUtils;
import utils.SelectTimeDialog;

public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv, beizhuTv, timeTv;
    GridView typeGv;
    List<Type> typeList;
    TypeAdapter typeAdapter;
    Account ac; //将需要插入到记账本中的数据保存成对象

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac = new Account();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_record, container, false);
        initView(view);
        // 初始化时间
        setInitTime();

        // 给GridView填充数据
        loadDataToGV();

        // 设置GridView每一项的点击事件
        setGVListener();
        return view;
    }

    // 获取当前时间，显示在timeTv
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String time = sdf.format(date);
        timeTv.setText(time);
        ac.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        ac.setYear(year);
        ac.setMonth(month);
        ac.setDay(day);
    }

    // 给GridView填充数据的方法
    public void loadDataToGV() {
        typeList = new ArrayList<>();
        typeAdapter = new TypeAdapter(getContext(), typeList);
        typeGv.setAdapter(typeAdapter);
    }

    // 设置GridView每一项的点击事件
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeAdapter.selectPos = position;
                typeAdapter.notifyDataSetInvalidated();  //提示绘制发生变化了，点谁，谁就变成红色
                Type type = typeList.get(position);
                // 更换为点击的那个类别的名称
                String typename = type.getTypename();
                typeTv.setText(typename);

                ac.setTypename(typename);   // 写入Account对象

                // 更换为点击的那个类别的图标
                int simageId = type.getSimageId();
                typeIv.setImageResource(simageId);

                ac.setsImageId(simageId);   // 写入Account对象

            }
        });
    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        typeGv = view.findViewById(R.id.frag_record_gv);

        // 给备注和时间设置点击事件
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        // 显示自定义键盘
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        boardUtils.showKeyboard();

        // 设置接口监听 键盘 “确定” 按钮被点击
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // 点击了确定按钮
                // 获取输入的钱数
                String moneyStr = moneyEt.getText().toString();
                if(TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")) {
                    // 没输入数字或者数字是0，则关闭当前活动，并跳出这个方法
                    getActivity().finish();
                    return;
                }

                float money = Float.parseFloat(moneyStr);
                ac.setMoney(money);

                // 获取记录的信息，保存在数据库中，
                saveAccountToDB();

                // 返回上级页面
                getActivity().finish();
            }
        });
    }

    public abstract void saveAccountToDB(); // 抽象方法的作用是：让子类一定要重写这个方法

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.frag_record_tv_beizhu) {
            showBeiZhuDialog();
        } else if (v.getId() == R.id.frag_record_tv_time) {
            showCalendarDialog();
        }
    }

    // 弹出日历
    private void showCalendarDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();  // 日历显示出来

        // 设置日期 确定按钮被点击的监听
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                ac.setTime(time);
                ac.setYear(year);
                ac.setMonth(month);
                ac.setDay(day);
            }
        });
    }

    // 弹出备注对话框
    public void showBeiZhuDialog() {
        BeiZhuDialog dialog = new BeiZhuDialog(getContext());
        dialog.show();  // 对话框显示出来

        dialog.setOnEnsureListener(new BeiZhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String message = dialog.getEditText();
                if(!TextUtils.isEmpty(message)) {
                    beizhuTv.setText(message);
                    ac.setBeizhu(message);
                }
            }
        });
    }
}