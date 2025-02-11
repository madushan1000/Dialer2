/*
 * Copyright (C) 2013 The Android Open Source Project
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
package com.android.dialer2.app.list;

import static android.Manifest.permission.READ_CONTACTS;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.contacts.common.list.ContactEntryListAdapter;
import com.android.contacts.common.list.PinnedHeaderListView;
import com.android.dialer2.app.R;
import com.android.dialer2.callintent.CallInitiationType;
import com.android.dialer2.common.LogUtil;
import com.android.dialer2.common.concurrent.DialerExecutor;
import com.android.dialer2.common.concurrent.DialerExecutor.Worker;
import com.android.dialer2.common.concurrent.DialerExecutorComponent;
import com.android.dialer2.phonenumbercache.CachedNumberLookupService;
import com.android.dialer2.phonenumbercache.CachedNumberLookupService.CachedContactInfo;
import com.android.dialer2.phonenumbercache.PhoneNumberCache;
import com.android.dialer2.util.PermissionsUtil;
import com.android.dialer2.widget.EmptyContentView;
import com.android.dialer2.widget.EmptyContentView.OnEmptyViewActionButtonClickedListener;
import java.util.Arrays;

public class RegularSearchFragment extends SearchFragment
    implements OnEmptyViewActionButtonClickedListener,
        FragmentCompat.OnRequestPermissionsResultCallback {

  public static final int PERMISSION_REQUEST_CODE = 1;

  private static final int SEARCH_DIRECTORY_RESULT_LIMIT = 5;
  protected String permissionToRequest;

  private DialerExecutor<CachedContactInfo> addContactTask;

  public RegularSearchFragment() {
    configureDirectorySearch();
  }

  public void configureDirectorySearch() {
    setDirectorySearchEnabled(true);
    setDirectoryResultLimit(SEARCH_DIRECTORY_RESULT_LIMIT);
  }

  @Override
  public void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    addContactTask =
        DialerExecutorComponent.get(getContext())
            .dialerExecutorFactory()
            .createUiTaskBuilder(
                getFragmentManager(),
                "RegularSearchFragment.addContact",
                new AddContactWorker(getContext().getApplicationContext()))
            .build();
  }

  @Override
  protected void onCreateView(LayoutInflater inflater, ViewGroup container) {
    super.onCreateView(inflater, container);
    ((PinnedHeaderListView) getListView()).setScrollToSectionOnHeaderTouch(true);
  }

  @Override
  protected ContactEntryListAdapter createListAdapter() {
    RegularSearchListAdapter adapter = new RegularSearchListAdapter(getActivity());
    adapter.setDisplayPhotos(true);
    adapter.setUseCallableUri(usesCallableUri());
    adapter.setListener(this);
    return adapter;
  }

  @Override
  protected void cacheContactInfo(int position) {
    CachedNumberLookupService cachedNumberLookupService =
        PhoneNumberCache.get(getContext()).getCachedNumberLookupService();
    if (cachedNumberLookupService != null) {
      final RegularSearchListAdapter adapter = (RegularSearchListAdapter) getAdapter();
      CachedContactInfo cachedContactInfo =
          adapter.getContactInfo(cachedNumberLookupService, position);
      addContactTask.executeSerial(cachedContactInfo);
    }
  }

  @Override
  protected void setupEmptyView() {
    if (emptyView != null && getActivity() != null) {
      final int imageResource;
      final int actionLabelResource;
      final int descriptionResource;
      final OnEmptyViewActionButtonClickedListener listener;
      if (!PermissionsUtil.hasPermission(getActivity(), READ_CONTACTS)) {
        imageResource = R.drawable.empty_contacts;
        actionLabelResource = R.string.permission_single_turn_on;
        descriptionResource = R.string.permission_no_search;
        listener = this;
        permissionToRequest = READ_CONTACTS;
      } else {
        imageResource = EmptyContentView.NO_IMAGE;
        actionLabelResource = EmptyContentView.NO_LABEL;
        descriptionResource = EmptyContentView.NO_LABEL;
        listener = null;
        permissionToRequest = null;
      }

      emptyView.setImage(imageResource);
      emptyView.setActionLabel(actionLabelResource);
      emptyView.setDescription(descriptionResource);
      if (listener != null) {
        emptyView.setActionClickedListener(listener);
      }
    }
  }

  @Override
  public void onEmptyViewActionButtonClicked() {
    final Activity activity = getActivity();
    if (activity == null) {
      return;
    }

    if (READ_CONTACTS.equals(permissionToRequest)) {
      String[] deniedPermissions =
          PermissionsUtil.getPermissionsCurrentlyDenied(
              getContext(), PermissionsUtil.allContactsGroupPermissionsUsedInDialer);
      if (deniedPermissions.length > 0) {
        LogUtil.i(
            "RegularSearchFragment.onEmptyViewActionButtonClicked",
            "Requesting permissions: " + Arrays.toString(deniedPermissions));
        FragmentCompat.requestPermissions(this, deniedPermissions, PERMISSION_REQUEST_CODE);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      setupEmptyView();
      if (grantResults != null
          && grantResults.length == 1
          && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
        PermissionsUtil.notifyPermissionGranted(getActivity(), permissions[0]);
      }
    }
  }

  @Override
  protected CallInitiationType.Type getCallInitiationType(boolean isRemoteDirectory) {
    return isRemoteDirectory
        ? CallInitiationType.Type.REMOTE_DIRECTORY
        : CallInitiationType.Type.REGULAR_SEARCH;
  }

  public interface CapabilityChecker {

    boolean isNearbyPlacesSearchEnabled();
  }

  private static class AddContactWorker implements Worker<CachedContactInfo, Void> {

    private final Context appContext;

    private AddContactWorker(Context appContext) {
      this.appContext = appContext;
    }

    @Nullable
    @Override
    public Void doInBackground(@Nullable CachedContactInfo contactInfo) throws Throwable {
      CachedNumberLookupService cachedNumberLookupService =
          PhoneNumberCache.get(appContext).getCachedNumberLookupService();
      if (cachedNumberLookupService != null) {
        cachedNumberLookupService.addContact(appContext, contactInfo);
      }
      return null;
    }
  }
}
