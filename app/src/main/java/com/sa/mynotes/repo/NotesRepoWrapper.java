package com.sa.mynotes.repo;

import com.sa.mynotes.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by temp on 14/11/17.
 */

public class NotesRepoWrapper {

    private static NotesRepoWrapper instance;
    private final Realm mRealm;

    private NotesRepoWrapper() {
        mRealm = Realm.getDefaultInstance();
    }

    public static NotesRepoWrapper getInstance() {
        if (instance == null) {
            instance = new NotesRepoWrapper();
        }
        return instance;
    }


    /*
        Fetch all saved notes
     */
    public List<Note> getNotesList() {
        List<Note> list = new ArrayList<>();
        try {
            RealmResults<Note> results = mRealm
                    .where(Note.class)
                    .findAll();
            list.addAll(mRealm.copyFromRealm(results));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /*
        Add a new note
     */
    public void addNotes(Note note) {
        if (note.getId() == 0) {
            int randomNo = new Random().nextInt(50) + 1;
            note.setId(randomNo);
        }
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(note);
        mRealm.commitTransaction();
        mRealm.close();
    }

}
