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

package com.android.dialer2.precall;

import android.content.Context;
import com.android.dialer2.inject.HasRootComponent;
import dagger.Subcomponent;

/** Daggaer component for {@link PreCall} */
@Subcomponent
public abstract class PreCallComponent {
  public abstract PreCall getPreCall();

  public static PreCallComponent get(Context context) {
    return ((HasComponent) ((HasRootComponent) context.getApplicationContext()).component())
        .preCallActionsComponent();
  }

  /** Used to refer to the root application component. */
  public interface HasComponent {
    PreCallComponent preCallActionsComponent();
  }
}
