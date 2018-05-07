package wuxiang.miku.scorpio.paperactivate.modules;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.adapter.NoteBookAdapter;
import wuxiang.miku.scorpio.paperactivate.base.BaseActivity;
import wuxiang.miku.scorpio.paperactivate.bean.Note;

public class NoteBookActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refersh_layout)
    SwipeRefreshLayout mRefershLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NoteBookAdapter mAdapter;
    private List<Note> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchNoteBooks();
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new NoteBookAdapter(mNotes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRefershLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNoteBooks();
            }
        });
    }

    @Override
    public void initToolBar() {
        toolbar.setTitle("笔记");
        setSupportActionBar(toolbar);
    }

    private synchronized void fetchNoteBooks() {
        String username = "123456";
        if (BmobUser.getCurrentUser(this) != null) {
            username = BmobUser.getCurrentUser(this).getUsername();
        }

        BmobQuery<Note> query = new BmobQuery<>();
        query.setLimit(50);
        query.addWhereEqualTo("username", username);
        query.findObjects(this, new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> list) {
                mRefershLayout.setRefreshing(false);

                mNotes.clear();
                mNotes.addAll(list);
                mAdapter.notifyDataSetChanged();

                Logger.d(list.size());
            }

            @Override
            public void onError(int i, String s) {
                mRefershLayout.setRefreshing(false);
            }
        });
    }
}
