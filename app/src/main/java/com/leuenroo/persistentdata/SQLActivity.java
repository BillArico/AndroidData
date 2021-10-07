package com.leuenroo.persistentdata;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;


import com.leuenroo.persistentdata.DB.Note;
import com.leuenroo.persistentdata.DB.OrganizerStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SQLActivity extends ListActivity {
    private OrganizerStorage datasource;
    @BindView(R.id.get_toWrite)
    EditText mEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_l);
        ButterKnife.bind(this);

        datasource = new OrganizerStorage(this);
        datasource.open();

        List<Note> values = datasource.getAllNotes();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Note> adapter = (ArrayAdapter<Note>) getListAdapter();
        Note nt = null;
        switch (view.getId()) {
            case R.id.add:


                // save the new comment to the database
                nt = datasource.createComment(mEditText.getText().toString());
                adapter.add(nt);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    nt = (Note) getListAdapter().getItem(0);
                    datasource.deleteNote(nt);
                    adapter.remove(nt);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}