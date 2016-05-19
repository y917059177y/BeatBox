package com.example.xyl.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XyL on 2016/5/19.
 * 访问读取assets资源
 */
public class BeatBox {

    public static final String TAG = "BeatBox";

    public static final String SOUNDS_FOLDER = "sample_sounds";

    // 同时播放的最大声音数量
    public static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();

    // SoundPool的优点是响应快
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        // 这个老的构造方法已经被弃用了，但是为了兼容性我们依然使用它
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds.");
        } catch (IOException ioe) {
            Log.i(TAG, "Could not list assets!", ioe);
            return;
        }

        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.i(TAG, "Could not load sound " + filename, e);
            }

        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        // 参数分别是SoundID，左声道音量，右声道音量，优先级(忽略)，循环次数，播放速度
        // 循环次数：传0表示只播放一次，传-1表示永远循环
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    // 释放资源
    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }
}
