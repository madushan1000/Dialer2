/*
 * Copyright (C) 2017 The Android Open Source Project
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
 * limitations under the License
 */

package com.android.dialer2.constants;

import com.android.dialer2.duo.Duo;

/**
 * Class containing {@link android.app.Activity#onActivityResult(int, int, android.content.Intent)}
 * request codes.
 */
public final class ActivityRequestCodes {

  private ActivityRequestCodes() {}

  /** Request code for {@link android.speech.RecognizerIntent#ACTION_RECOGNIZE_SPEECH} intent. */
  public static final int DIALTACTS_VOICE_SEARCH = 1;

  /** Request code for {@link com.android.dialer2.callcomposer.CallComposerActivity} intent. */
  public static final int DIALTACTS_CALL_COMPOSER = 2;

  /** Request code for {@link Duo#getIntent(android.content.Context, String)}. */
  public static final int DIALTACTS_DUO = 3;

  /** Request code for {@link com.android.dialer2.calldetails.CallDetailsActivity} intent. */
  public static final int DIALTACTS_CALL_DETAILS = 4;
}
