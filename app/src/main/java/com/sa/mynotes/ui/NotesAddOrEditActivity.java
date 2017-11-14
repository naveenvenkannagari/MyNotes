package com.sa.mynotes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.mynotes.R;
import com.sa.mynotes.Utils;
import com.sa.mynotes.models.Note;
import com.sa.mynotes.repo.NotesRepoWrapper;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by temp on 14/11/17.
 */

public class NotesAddOrEditActivity extends AppCompatActivity {

    public static final int LAUNCH_MODE_ADD = 1000;
    public static final int LAUNCH_MODE_EDIT = 1001;

    public static final String NOTES_DATA = "NOTES_DATA";
    public static final String LAUNCH_MODE = "LAUNCH_MODE";
    private static final String DISPLAY_DATE_FORMAT = "yyyy/MM/dd";

    @BindView(R.id.notes_edit_text)
    EditText mNotesEditText;

    @BindView(R.id.notes_label)
    TextView mNotesLabel;


    @OnClick(R.id.submit_button_view)
    public void onSubmitButtonClick() {
        if (TextUtils.isEmpty(mNotesEditText.getText().toString())) {
            Toast.makeText(this, getString(R.string.notes_validation_message), Toast.LENGTH_SHORT).show();
            return;
        }
        saveDataToRepo();
        finish();
    }

    @OnClick(R.id.back_button_view)
    public void onBackButtonClicked() {
        finish();
    }

    private int mLaunchMode;
    private Note mNoteData;
    private Unbinder mUnBinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_or_edit_view);
        mUnBinder = ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        mLaunchMode = getIntent().getIntExtra(LAUNCH_MODE, 0);
        if (mLaunchMode == LAUNCH_MODE_EDIT) {
            mNoteData = Parcels.unwrap(getIntent().getParcelableExtra(NOTES_DATA));
            mNotesEditText.setText(mNoteData.getDescription());
            mNotesEditText.setSelection(mNotesEditText.getText().length());
            mNotesLabel.setText(getString(R.string.edit_note_label));
        } else {
            mNotesLabel.setText(getString(R.string.new_note_label));
        }
    }

    private void saveDataToRepo() {
        Note note;
        if (mLaunchMode == LAUNCH_MODE_EDIT) {
            mNoteData.setDescription(mNotesEditText.getText().toString());
            note = mNoteData;
        } else {
            note = new Note();
            note.setDescription(mNotesEditText.getText().toString());
            note.setDateAdded(Utils.getTodaysDateFormatinString(DISPLAY_DATE_FORMAT));
        }
        NotesRepoWrapper.getInstance().addNotes(note);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }
}
