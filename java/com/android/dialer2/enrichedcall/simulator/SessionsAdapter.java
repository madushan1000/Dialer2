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
 * limitations under the License.
 */

package com.android.dialer2.enrichedcall.simulator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.dialer2.common.Assert;
import java.util.List;

/** Adapter for the RecyclerView in {@link EnrichedCallSimulatorActivity}. */
class SessionsAdapter extends RecyclerView.Adapter<SessionViewHolder> {

  /** List of the string representation of all in-memory sessions */
  private List<String> sessionStrings;

  void setSessionStrings(@NonNull List<String> sessionStrings) {
    this.sessionStrings = Assert.isNotNull(sessionStrings);
  }

  @Override
  public SessionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    return new SessionViewHolder(inflater.inflate(R.layout.session_view_holder, viewGroup, false));
  }

  @Override
  public void onBindViewHolder(SessionViewHolder viewHolder, int i) {
    viewHolder.updateSession(sessionStrings.get(i));
  }

  @Override
  public int getItemCount() {
    return sessionStrings.size();
  }
}
