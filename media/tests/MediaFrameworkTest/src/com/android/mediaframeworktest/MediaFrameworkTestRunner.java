/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.mediaframeworktest;

import com.android.mediaframeworktest.functional.CameraTest;
import com.android.mediaframeworktest.functional.MediaMetadataTest;
import com.android.mediaframeworktest.functional.MediaSamplesTest;
import com.android.mediaframeworktest.functional.MediaMimeTest;
import com.android.mediaframeworktest.functional.MediaPlayerInvokeTest;
import com.android.mediaframeworktest.functional.mediaplayback.MediaPlayerApiTest;
import com.android.mediaframeworktest.functional.mediarecorder.MediaRecorderTest;
import com.android.mediaframeworktest.functional.audio.SimTonesTest;
import com.android.mediaframeworktest.functional.audio.MediaAudioTrackTest;
import com.android.mediaframeworktest.functional.audio.MediaAudioManagerTest;
import com.android.mediaframeworktest.functional.audio.MediaAudioEffectTest;
import com.android.mediaframeworktest.functional.audio.MediaBassBoostTest;
import com.android.mediaframeworktest.functional.audio.MediaEnvReverbTest;
import com.android.mediaframeworktest.functional.audio.MediaEqualizerTest;
import com.android.mediaframeworktest.functional.audio.MediaPresetReverbTest;
import com.android.mediaframeworktest.functional.audio.MediaVirtualizerTest;
import com.android.mediaframeworktest.functional.audio.MediaVisualizerTest;
import com.android.mediaframeworktest.functional.videoeditor.MediaItemThumbnailTest;
import com.android.mediaframeworktest.functional.videoeditor.MediaPropertiesTest;
import com.android.mediaframeworktest.functional.videoeditor.VideoEditorAPITest;
import com.android.mediaframeworktest.functional.videoeditor.VideoEditorExportTest;
import com.android.mediaframeworktest.functional.videoeditor.VideoEditorPreviewTest;
import java.io.File;
import java.util.Arrays;
import android.os.Bundle;

import android.os.Bundle;
import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;
import android.util.Log;

/**
 * Instrumentation Test Runner for all MediaPlayer tests.
 *
 * Running all tests:
 *
 * adb shell am instrument \
 *   -w com.android.smstests.MediaPlayerInstrumentationTestRunner
 */

public class MediaFrameworkTestRunner extends InstrumentationTestRunner {

    public static int mMinCameraFps = 0;
    public static String mTargetDir = "/sdcard/";
    private static String TAG = "MediaFrameworkTestRunner";
    @Override
    public void onCreate (Bundle arguments){
        Log.v(TAG, "step into onCreate");
        super.onCreate(arguments);

        String minCameraFps = (String) arguments.get("min_camera_fps");
        System.out.print("min_camera_" + minCameraFps);

        if (minCameraFps != null ) {
            mMinCameraFps = Integer.parseInt(minCameraFps);
        }
        String targetDir = (String)arguments.get("targetDir");
        if(targetDir != null){
             Log.v(TAG, "targetDir="+targetDir);
             mTargetDir = targetDir;
        }
        Log.v(TAG, "step out onCreate");
    }

    @Override
    public TestSuite getAllTests() {
        TestSuite suite = new InstrumentationTestSuite(this);
/*
        suite.addTestSuite(MediaPlayerApiTest.class);
        suite.addTestSuite(SimTonesTest.class);
        suite.addTestSuite(MediaMetadataTest.class);
        suite.addTestSuite(CameraTest.class);
        suite.addTestSuite(MediaRecorderTest.class);
        suite.addTestSuite(MediaAudioTrackTest.class);
        suite.addTestSuite(MediaMimeTest.class);
        suite.addTestSuite(MediaPlayerInvokeTest.class);
        suite.addTestSuite(MediaAudioManagerTest.class);
        suite.addTestSuite(MediaAudioEffectTest.class);
        suite.addTestSuite(MediaBassBoostTest.class);
        suite.addTestSuite(MediaEnvReverbTest.class);
        suite.addTestSuite(MediaEqualizerTest.class);
        suite.addTestSuite(MediaPresetReverbTest.class);
        suite.addTestSuite(MediaVirtualizerTest.class);
        suite.addTestSuite(MediaVisualizerTest.class);
*/
        /*Test for Video Editor*/
/*        suite.addTestSuite(MediaItemThumbnailTest.class);
        suite.addTestSuite(MediaPropertiesTest.class);
        suite.addTestSuite(VideoEditorAPITest.class);
        suite.addTestSuite(VideoEditorExportTest.class);
        suite.addTestSuite(VideoEditorPreviewTest.class);
*/
        Log.v(TAG, "step into getAllTests");

        if((mTargetDir != null) && (mTargetDir != "")){
            Log.v(TAG, "before addMMTestCase");
            addMMTestCase(suite, mTargetDir);
        }
        Log.v(TAG, "step out getAllTests");
        return suite;
    }

    public void addMMTestCase(TestSuite suite, String testFilesDir){
        File dir = new File(testFilesDir);
        String[] children;
        if (dir.isFile()){
            children = new String[]{testFilesDir};
        }else{
            children = dir.list();
        }
        if ((children == null) ||(children.length == 0)) {
            Log.v(TAG, "This dir is empty:" + testFilesDir);
            return;
        } else {
            Arrays.sort(children);
            int length = children.length;
            for (int i = 0; i < length; i++) {
                String filename = children[i];
                final File subFile = new File(dir + "/" + filename);
                if (subFile.isDirectory()){
                    Log.v(TAG, "loop directory:" + subFile.getPath());
                    addMMTestCase(suite, subFile.getPath());
                }else{
                    Log.v(TAG, "add test case:" + subFile.getPath());
                    MediaSamplesTest tempTestCase = new MediaSamplesTest(){
                          protected void runTest() throws Exception {
                               testSubPlay(subFile.getPath());
                          }
                    };
                    tempTestCase.setName(filename);
                    suite.addTest(tempTestCase);
                }
            }
            return;
       }
    }

    @Override
    public ClassLoader getLoader() {
        return MediaFrameworkTestRunner.class.getClassLoader();
    }
}
