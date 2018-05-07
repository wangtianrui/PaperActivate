package wuxiang.miku.scorpio.paperactivate.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.bean.Note;

public class NoteBookAdapter extends BaseQuickAdapter<Note,BaseViewHolder> {
    public NoteBookAdapter(@Nullable List<Note> data) {
        super(R.layout.item_note_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note item) {
        helper.setText(R.id.note_tv, item.getNote());
    }
}
