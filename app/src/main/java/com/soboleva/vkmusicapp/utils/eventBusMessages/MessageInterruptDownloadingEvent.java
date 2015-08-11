package com.soboleva.vkmusicapp.utils.eventBusMessages;

public class MessageInterruptDownloadingEvent {
    private final String mAudioID;

    public MessageInterruptDownloadingEvent(String audioID) {
        mAudioID = audioID;
    }

    public String getAudioID() {
        return mAudioID;
    }
}
