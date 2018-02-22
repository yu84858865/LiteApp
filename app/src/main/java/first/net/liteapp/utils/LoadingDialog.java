package first.net.liteapp.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import first.net.liteapp.R;


public class LoadingDialog extends Dialog {
   private TextView mTv;
	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setCancelable(false);
		setContentView(R.layout.page_loading_view);
		mTv = (TextView)findViewById(R.id.tv_loading_error);
		setCanceledOnTouchOutside(false);
	}

	public  void  setText(String text){
		mTv.setText(text);
	}
	
}
