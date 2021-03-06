package com.soboleva.vkmusicloader.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.soboleva.vkmusicloader.R;
import com.soboleva.vkmusicloader.presenters.AudioActivityPresenter;
import com.soboleva.vkmusicloader.ui.fragments.AboutDialog;
import com.soboleva.vkmusicloader.ui.fragments.SearchAudioListFragment;
import com.soboleva.vkmusicloader.ui.fragments.ViewPagerFragment;
import com.soboleva.vkmusicloader.utils.FontUtil;
import timber.log.Timber;

public class AudioListActivity extends BaseActivity {

    private Toolbar mToolbar;
    private SearchView mSearchView;

    private AudioActivityPresenter mAudioActivityPresenter;

    private final String mainFragmentName = "my audio";

    private boolean isSearchFragmentOpen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.soboleva.vkmusicloader.R.layout.activity_audio_list);
        mAudioActivityPresenter = new AudioActivityPresenter(this);

        setupUI();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        //MenuItem searchItem = menu.findItem(R.id.action_search);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Do whatever you want
                Timber.d("WTF, onMenuItemActionExpand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Do whatever you want
                Timber.d("WTF, onMenuItemActionCollapse");
                if (isSearchFragmentOpen) {
                    getSupportFragmentManager().popBackStack(mainFragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return true;
            }
        });

        mSearchView = (SearchView) searchItem.getActionView();

        //mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Timber.d("WTF,onQueryTextSubmit,  %s", s);
                Fragment searchAudioListFragment = SearchAudioListFragment.instanceOf(AudioListActivity.this, s);
                isSearchFragmentOpen = true;
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(mainFragmentName)
                        .replace(R.id.fragment_container, searchAudioListFragment)
                        .commit();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d("WTF,onQueryTextChange,  %s", s);
                if (s.equals("")){
                    Timber.d("WTF, empty string", s);
                }
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchFragmentOpen = false;
                getSupportFragmentManager().popBackStack(mainFragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mAudioActivityPresenter.logOut();
                startActivity(new Intent(AudioListActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.action_about:
                new AboutDialog().show(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar_audio_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.inflateMenu(R.menu.main_menu);

        TextView toolbarTitle = (TextView)findViewById(R.id.audio_activity_toolbar_title);
        FontUtil.apply(toolbarTitle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new ViewPagerFragment())
                .commit();


    }


}





