/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.dialer2.shortcuts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import com.android.contacts.common.list.ContactEntry;
import com.android.dialer2.common.Assert;
import com.android.dialer2.common.LogUtil;
import com.android.dialer2.common.concurrent.DialerExecutor.Worker;
import com.android.dialer2.common.concurrent.DialerExecutorComponent;
import java.util.ArrayList;
import java.util.List;

/** Refreshes launcher shortcuts from UI components using provided list of contacts. */
public final class ShortcutRefresher {

  /** Asynchronously updates launcher shortcuts using the provided list of contacts. */
  @MainThread
  public static void refresh(@NonNull Context context, List<ContactEntry> contacts) {
    Assert.isMainThread();
    Assert.isNotNull(context);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
      return;
    }

    if (!Shortcuts.areDynamicShortcutsEnabled(context)) {
      return;
    }

    DialerExecutorComponent.get(context)
        .dialerExecutorFactory()
        .createNonUiTaskBuilder(new RefreshWorker(context))
        .build()
        .executeSerial(new ArrayList<>(contacts));
  }

  private static final class RefreshWorker implements Worker<List<ContactEntry>, Void> {
    private final Context context;

    RefreshWorker(Context context) {
      this.context = context;
    }

    @Override
    public Void doInBackground(List<ContactEntry> contacts) {
      LogUtil.enterBlock("ShortcutRefresher.Task.doInBackground");

      // Only dynamic shortcuts are maintained from UI components. Pinned shortcuts are maintained
      // by the job scheduler. This is because a pinned contact may not necessarily still be in the
      // favorites tiles, so refreshing it would require an additional database query. We don't want
      // to incur the cost of that extra database query every time the favorites tiles change.
      new DynamicShortcuts(context, new IconFactory(context)).refresh(contacts); // Blocking

      return null;
    }
  }
}
