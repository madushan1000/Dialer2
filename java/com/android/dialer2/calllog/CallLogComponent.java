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
package com.android.dialer2.calllog;

import android.content.Context;
import com.android.dialer2.calllog.notifier.RefreshAnnotatedCallLogNotifier;
import com.android.dialer2.inject.HasRootComponent;
import dagger.Subcomponent;

/** Dagger component for the call log package. */
@Subcomponent
public abstract class CallLogComponent {

  public abstract CallLogFramework callLogFramework();

  public abstract RefreshAnnotatedCallLogNotifier getRefreshAnnotatedCallLogNotifier();

  public abstract RefreshAnnotatedCallLogWorker getRefreshAnnotatedCallLogWorker();

  public abstract AnnotatedCallLogMigrator getAnnotatedCallLogMigrator();

  public abstract ClearMissedCalls getClearMissedCalls();

  public static CallLogComponent get(Context context) {
    return ((HasComponent) ((HasRootComponent) context.getApplicationContext()).component())
        .callLogComponent();
  }

  /** Used to refer to the root application component. */
  public interface HasComponent {
    CallLogComponent callLogComponent();
  }
}
