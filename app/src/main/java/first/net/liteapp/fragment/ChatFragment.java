package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.ChatAdapter;

/**
 * Created by 10960 on 2018/2/3.
 */

public class ChatFragment extends BaseFragment{
    private ListView lv_chat;
    private List<String> mChatList;
    private ChatAdapter mChatAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lv_chat =new ListView(mContext);
        lv_chat.setDivider(null);
        mChatList = new ArrayList<>();
        lv_chat.setAdapter(mChatAdapter = new ChatAdapter(mContext));
        mChatList.add("欢迎 177756745来到本直播间");
        mChatList.add("Guess高: 学习了，这样就能预防疾病");
        mChatList.add("Jack离: 教授讲的太好了");
        mChatList.add("追梦: 666666");
        mChatList.add("放飞自我: 受教了");
        mChatList.add("Guess高: lallalalla~~");
        mChatAdapter.setData(mChatList);
        return lv_chat;
    }
}
