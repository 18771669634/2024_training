package adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class RecordFragPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] title = {"支出", "收入"};
    public RecordFragPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public RecordFragPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    // 重写 getPageTitle
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
