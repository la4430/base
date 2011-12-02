/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.android.mediaframeworktest.functional;

import com.android.mediaframeworktest.MediaFrameworkTest;
import com.android.mediaframeworktest.MediaNames;
import com.android.mediaframeworktest.MediaProfileReader;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase;
import android.util.Log;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.Suppress;

import java.io.File;
import junit.framework.*;

/**
 * Junit / Instrumentation test case for the media player api
 */
public class MediaSamplesTest extends ActivityInstrumentationTestCase<MediaFrameworkTest> {
   private String TAG = "MediaSamplesTest";

   public MediaSamplesTest() {
     super("com.android.mediaframeworktest", MediaFrameworkTest.class);
   }

   protected void setUp() throws Exception {
      super.setUp();
   }

   public void testSubPlay(String path) throws Exception{
       boolean onCompleteSuccess = false;
       Log.v(TAG,
             "testSubPlay: file to be played: "
              + path);
       onCompleteSuccess = CodecTest.playMediaSamples(path);
       assertTrue("testSubPlay:" + path, onCompleteSuccess);
   }
}
