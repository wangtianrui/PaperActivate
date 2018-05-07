package wuxiang.miku.scorpio.paperactivate.modules;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showNotebookDialog(mNotes.get(position).getNote(),mNotes.get(position).getObjectId());
            }
        });
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
            }

            @Override
            public void onError(int i, String s) {
                mRefershLayout.setRefreshing(false);
            }
        });
    }

    private void showNotebookDialog(String notebookStr, final String updateObjectId) {
        final EditText notebookText = new EditText(this);
        notebookText.setText(notebookStr);
        notebookText.setTextSize(18);
        notebookText.setTextColor(Color.GRAY);
        notebookText.setGravity(Gravity.CENTER);

        AlertDialog notebookDialog = new AlertDialog.Builder(this)
                .setTitle("笔记")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String edittedNotebook = notebookText.getText().toString();
                        String username = "123456";
                        if (BmobUser.getCurrentUser(NoteBookActivity.this) != null) {
                            username = BmobUser.getCurrentUser(NoteBookActivity.this).getUsername();
                        }

                        new Note(edittedNotebook, username).update(NoteBookActivity.this,updateObjectId, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(NoteBookActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                                fetchNoteBooks();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Logger.e(s);
                            }
                        });

                    }
                })
                .create();


        notebookDialog.setView(notebookText,20,10,20,10);
        notebookDialog.show();
    }
}
