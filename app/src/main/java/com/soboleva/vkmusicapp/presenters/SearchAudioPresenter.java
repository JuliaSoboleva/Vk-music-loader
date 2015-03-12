package com.soboleva.vkmusicapp.presenters;

import com.soboleva.vkmusicapp.api.vk.callbacks.OnAudioListDownloadedListener;
import com.soboleva.vkmusicapp.api.vk.models.audios.Audio;
import com.soboleva.vkmusicapp.ui.fragments.SearchAudioListFragment;
import timber.log.Timber;

import java.util.List;

public class SearchAudioPresenter extends AudioPresenter {

    private String mSearchRequest;

    public SearchAudioPresenter(SearchAudioListFragment fragment, String searchRequest) {
        super(fragment);
        mSearchRequest = searchRequest;
    }

    @Override
    public void getAudio(final int offset, int count) {
        mIsDownloadingNow = true;
        mVkApi.getSearchedAudio(new OnAudioListDownloadedListener() {
            @Override
            public void onAudioListDownloaded(List<Audio> audios, int totalCount) {
                mAvailableAudioCount += audios.size();
                if (offset == 0) {
                    mAudioFragment.showAudio(audios);
                    mTotalAudioCount = totalCount;

                } else {
                    mAudioFragment.showWithAddedAudio(audios);
                }
                mIsDownloadingNow = false;
            }

            @Override
            public void onError() {
                Timber.d("getSearchedAudio Error");
                mIsDownloadingNow = false;
            }
        }, mSearchRequest, offset, count);
    }

    public void setSearchRequest(String searchRequest) {
        mSearchRequest = searchRequest;
    }
}
