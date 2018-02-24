package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import first.net.liteapp.R;

/**
 * Created by 10960 on 2018/2/3.
 */

public class AuthorFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_honor;
    private TextView tv_like;
    private TextView tv_intro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author, container, false);
        initView(view);
        setListener();
        initData();
        return view;
    }

    private void initView(View view) {
        iv_head = view.findViewById(R.id.iv_head);
        tv_name = view.findViewById(R.id.tv_name);
        tv_honor = view.findViewById(R.id.tv_honor);
        tv_like = view.findViewById(R.id.tv_like);
        tv_intro = view.findViewById(R.id.tv_intro);
    }

    private void setListener() {
        tv_like.setOnClickListener(this);
    }

    private void initData() {
        iv_head.setBackgroundResource(R.mipmap.ic_launcher);
        tv_name.setText("李叫兽");
        tv_honor.setText("百度副总裁");
        tv_like.setText(getString(R.string.live_author_like));
        tv_intro.setText("2016年12月，百度宣布全资收购北京受教信息科技有限公司，公司创始人、知名营销类公众号“李叫兽”作者李靖携团队加盟百度，任副总裁。李靖本人仍独立负责受教信息科技公司原有的科学化营销创意工具业务.");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_like:
                break;
        }
    }
}
