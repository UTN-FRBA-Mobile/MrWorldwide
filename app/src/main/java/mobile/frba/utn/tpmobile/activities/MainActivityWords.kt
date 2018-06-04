package mobile.frba.utn.tpmobile.activities;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import mobile.frba.utn.tpmobile.R;
import mobile.frba.utn.tpmobile.adapters.WordListAdapter;
import mobile.frba.utn.tpmobile.models.Word;
import mobile.frba.utn.tpmobile.models.WordViewModel;
import mobile.frba.utn.tpmobile.services.RestClient


class MainActivityWords : AppCompatActivity() {

    private var mWordViewModel: WordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_words)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val recyclerView = findViewById(R.id.recyclerview) as RecyclerView
        val adapter = WordListAdapter(this)
        recyclerView.setAdapter(adapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // Get a new or existing ViewModel from the ViewModelProvider.
        try {
            mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        } catch (e: Exception) {
            val aaa = e
        }


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel!!.getAllWords().observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            adapter.setWords(words!!)
        })

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivityWords, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RestClient.isOnline(this))
            Toast.makeText(
                    applicationContext,
                    "Online!",
                    Toast.LENGTH_LONG).show()
        else
            Toast.makeText(
                    applicationContext,
                    "Offline D: ",
                    Toast.LENGTH_LONG).show()
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val word = Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY))
            mWordViewModel!!.insert(word)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }

    }

    companion object {

        val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }
}

