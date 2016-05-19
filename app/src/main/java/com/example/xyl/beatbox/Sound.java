package com.example.xyl.beatbox;

/**
 * Created by XyL on 2016/5/19.
 */
public class Sound {
    private String mAssetPath;
    private String mName;

    // 加载进SoundPool的每个声音都有一个Integer的ID
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
