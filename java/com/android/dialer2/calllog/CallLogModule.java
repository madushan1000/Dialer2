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

import com.android.dialer2.calllog.datasources.CallLogDataSource;
import com.android.dialer2.calllog.datasources.DataSources;
import com.android.dialer2.calllog.datasources.phonelookup.PhoneLookupDataSource;
import com.android.dialer2.calllog.datasources.systemcalllog.SystemCallLogDataSource;
import com.google.common.collect.ImmutableList;
import dagger.Module;
import dagger.Provides;

/** Dagger module which satisfies call log dependencies. */
@Module
public abstract class CallLogModule {

  @Provides
  static DataSources provideCallLogDataSources(
      SystemCallLogDataSource systemCallLogDataSource,
      PhoneLookupDataSource phoneLookupDataSource) {
    // System call log must be first, see getDataSourcesExcludingSystemCallLog below.
    ImmutableList<CallLogDataSource> allDataSources =
        ImmutableList.of(systemCallLogDataSource, phoneLookupDataSource);
    return new DataSources() {
      @Override
      public SystemCallLogDataSource getSystemCallLogDataSource() {
        return systemCallLogDataSource;
      }

      @Override
      public ImmutableList<CallLogDataSource> getDataSourcesIncludingSystemCallLog() {
        return allDataSources;
      }

      @Override
      public ImmutableList<CallLogDataSource> getDataSourcesExcludingSystemCallLog() {
        return allDataSources.subList(1, allDataSources.size());
      }
    };
  }
}
