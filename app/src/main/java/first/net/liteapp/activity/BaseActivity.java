package first.net.liteapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFeature();
        setContentView(getContentView());
        initView();
        setListener();
        setData();
    }

    protected void initFeature(){}

    public abstract int getContentView() ;

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void setData();
}
