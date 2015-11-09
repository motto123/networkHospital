package com.huiyuan.networkhospital.common.util;

import java.util.List;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.huiyuan.networkhospital.R;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.CountDownFormatter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
/**
 * ListVIew滑动删除,实现此类需要重写deleteItem(),onDismiss()
 * @author Administrator
 *
 */
public class RollDelete implements OnDismissCallback,
DeleteItemCallback{
	
	private Context context;
	
	private List list;
	
	private BaseAdapter adapter;
	
	private ListView listView;

	
	public RollDelete(Context context, List list, BaseAdapter adapter,
			ListView listView) {
		super();
		this.context = context;
		this.list = list;
		this.adapter = adapter;
		this.listView = listView;
		
		setContextualUndoWithTimedDeleteAdapter();
	}
	
	/**
	 *  可撤销的滑动删除
	 */
	private void setContextualUndoAdapter() {
        ContextualUndoAdapter adapter2 = new ContextualUndoAdapter(adapter, R.layout.item_undo_row, R.id.undo_row_undobutton, this);
        adapter2.setAbsListView(listView);
        listView.setAdapter(adapter2);
    }
	/**
	 *  滑动删除
	 */
	private void setSwipeDismissAdapter() {
		SwipeDismissAdapter adapter1 = new SwipeDismissAdapter(adapter, this);
		adapter1.setAbsListView(listView);
		listView.setAdapter(adapter1);
	}

	/**
	 *  自动消失的可撤销的滑动删除
	 */
	private void setContextualUndoWithTimedDeleteAdapter() {
        ContextualUndoAdapter adapter3 = new ContextualUndoAdapter(adapter, R.layout.item_undo_row, R.id.undo_row_undobutton, 3000, this);
        adapter3.setAbsListView(listView);
        listView.setAdapter(adapter3);
    }

	/**
	 *  带倒计时的滑动删除
	 */
    private void setContextualUndoWithTimedDeleteAndCountDownAdapter() {
        ContextualUndoAdapter adapter4 = new ContextualUndoAdapter(adapter, R.layout.item_undo_row, R.id.undo_row_undobutton, 3000, R.id.undo_row_texttv, this, new MyFormatCountDownCallback());
        adapter4.setAbsListView(listView);
        listView.setAdapter(adapter4);
    }

	//子类重写
	@Override
	public void deleteItem(int position) {
		// TODO Auto-generated method stub

	}
	
	//子类重写
	@Override
	public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
		// TODO Auto-generated method stub

	}
	/**
	 * 倒计时
	 * @author Administrator
	 *
	 */
	private class MyFormatCountDownCallback implements CountDownFormatter {

        @Override
        public String getCountDownString(final long millisUntilFinished) {
            int seconds = (int) Math.ceil(millisUntilFinished / 1000.0);

            if (seconds > 0) {
                return seconds+"s";
            }
            return "...";
        }
    }
	
}
