package first.net.liteapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import first.net.liteapp.R;

/**
 * Created by 10960 on 2018/2/25.
 */

public class CommentDialog extends Dialog implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context mContext;
    private InputMethodManager inputMethodManager;
    private EditText et_comment;
    private TextView tv_send;
    private CommentSubmitListener commentSubmitListener;

    public CommentDialog(Activity context) {
        super(context);
        mContext = context;
        setOwnerActivity(context);
        initView();
    }

    public void initView() {
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.activity_comment_edit, null);
        et_comment = mView.findViewById(R.id.et_comment);
        tv_send = mView.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
//        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        setContentView(mView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setOnCancelListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputMethodManager.showSoftInput(et_comment,0);
        et_comment.requestFocus();
        et_comment.setFocusable(true);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_send:
                String comment = et_comment.getText().toString().trim();
                if(commentSubmitListener != null){
                    commentSubmitListener.onSubmit(comment);
                    et_comment.setText("");
                    inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                    dismiss();
                }
                break;
        }
    }

    public void registCommentListener(CommentSubmitListener listener){
        this.commentSubmitListener = listener;
    }

    public interface CommentSubmitListener{
        void onSubmit(String commment);
        void onCancel();
    }


}
