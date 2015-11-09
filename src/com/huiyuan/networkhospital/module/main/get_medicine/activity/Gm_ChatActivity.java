package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Chat;
import com.huiyuan.networkhospital.module.main.adapter.ChatAdapter;

@EActivity(R.layout.activity__chat)
public class Gm_ChatActivity extends Activity {

	@ViewById
	TextView tvTitle1;
	
	@ViewById
	TextView tvTime;
	
	@ViewById
	ImageView ivBack;
	
	@ViewById
	ImageView ivRecord;
	
	@ViewById
	ImageView ivMore1;
	
	@ViewById
	ListView lvChat;
	
	@ViewById
	EditText etInput;
	
	@ViewById
	Button btSend;
	
	@ViewById
	RelativeLayout rlMore;
	
	@ViewById
	RelativeLayout rlInputBox;
	
	@ViewById
	ImageView ivImage;
	
	@ViewById
	ImageView ivVideo;
	
	private List<Chat> list;

	private ChatAdapter adapter;
	private InputMethodManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity__chat);
	}

	@AfterViews
	protected void init(){
		//去掉Item的分割线
		lvChat.setDividerHeight(0);
		lvChat.setDivider(null);
		//隐藏聊天功能栏
		rlMore.setVisibility(View.GONE);
	
		//测试
		list=new ArrayList<Chat>();
		Chat c=new Chat();
		c.setType(0);
		c.setText("注意多锻炼");
		list.add(c);
		
		Chat c1=new Chat();
		c1.setType(1);
		c1.setText("不明白请在指点");
		list.add(c1);
		
		Chat c2=new Chat();
		c2.setType(0);
		c2.setPic("");
		list.add(c2);
		
		Chat c3=new Chat();
		c3.setType(1);
		c3.setPic("");
		list.add(c3);
		adapter=new ChatAdapter(list, this);
		lvChat.setAdapter(adapter);

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Click({R.id.ivBack,R.id.ivRecord,R.id.ivMore1,
		R.id.btSend,R.id.ivImage,R.id.ivVideo,R.id.etInput})
	protected void onClick(View v){
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.ivRecord:
			
			break;
			//隐藏或显示聊天功能栏
		case R.id.ivMore1:
			if(rlMore.getVisibility()==View.GONE){
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				rlMore.setVisibility(View.VISIBLE);
			}else {
				rlMore.setVisibility(View.GONE);
				rlInputBox.setBottom(RelativeLayout.ALIGN_BOTTOM);
			}
			break;
		case R.id.btSend:
			
			break;
		case R.id.ivImage:
			
			break;
		case R.id.etInput:
			rlMore.setVisibility(View.GONE);
			break;
		case R.id.ivVideo:
			
			break;
		}
	}
	/**
	 * <RelativeLayout
        android:id="@+id/rlMore"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:visibility=""
        android:layout_alignParentLeft="true" >

        <ImageView
            android:id="@+id/ivVideo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="23dp"
            android:layout_marginBottom="10dp"
            android:layout_marginTop="10dp"
            android:layout_toRightOf="@+id/ivImage"
            android:src="@drawable/ic_chat_video" />

        <ImageView
            android:id="@+id/ivImage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:paddingBottom="10dp"
            android:layout_marginTop="10dp"
            android:src="@drawable/ic_chat_img" />

    </RelativeLayout>
    
    
    
    
    
    
    
   
	 */
	
}
