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

package com.android.dialer2.binary.google;

import com.android.dialer2.binary.basecomponent.BaseDialerRootComponent;
import com.android.dialer2.calllog.CallLogModule;
import com.android.dialer2.commandline.CommandLineModule;
import com.android.dialer2.common.concurrent.DialerExecutorModule;
import com.android.dialer2.configprovider.SharedPrefConfigProviderModule;
import com.android.dialer2.duo.stub.StubDuoModule;
import com.android.dialer2.enrichedcall.stub.StubEnrichedCallModule;
import com.android.dialer2.feedback.stub.StubFeedbackModule;
import com.android.dialer2.glidephotomanager.GlidePhotoManagerModule;
import com.android.dialer2.inject.ContextModule;
import com.android.dialer2.metrics.StubMetricsModule;
import com.android.dialer2.phonelookup.PhoneLookupModule;
import com.android.dialer2.phonenumbergeoutil.impl.PhoneNumberGeoUtilModule;
import com.android.dialer2.precall.impl.PreCallModule;
import com.android.dialer2.preferredsim.suggestion.stub.StubSimSuggestionModule;
import com.android.dialer2.simulator.impl.SimulatorModule;
import com.android.dialer2.simulator.stub.StubSimulatorEnrichedCallModule;
import com.android.dialer2.spam.StubSpamModule;
import com.android.dialer2.storage.StorageModule;
import com.android.dialer2.strictmode.impl.SystemStrictModeModule;
import com.android.incallui.calllocation.impl.CallLocationModule;
import com.android.incallui.maps.impl.MapsModule;
import com.android.incallui.speakeasy.StubSpeakEasyModule;
import com.android.newbubble.stub.StubNewBubbleModule;
import com.android.voicemail.impl.VoicemailModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Root component for the Google Stub Dialer application. Unlike the AOSP variant, this component
 * can pull in modules that depend on Google Play Services like the maps module.
 */
@Singleton
@Component(
  modules = {
    CallLocationModule.class,
    CallLogModule.class,
    CommandLineModule.class,
    ContextModule.class,
    DialerExecutorModule.class,
    GlidePhotoManagerModule.class,
    MapsModule.class,
    PhoneLookupModule.class, // TODO(zachh): Module which uses APDL?
    PhoneNumberGeoUtilModule.class,
    PreCallModule.class,
    SharedPrefConfigProviderModule.class,
    SimulatorModule.class,
    StorageModule.class,
    StubSimulatorEnrichedCallModule.class,
    StubDuoModule.class,
    StubEnrichedCallModule.class,
    StubFeedbackModule.class,
    StubMetricsModule.class,
    StubNewBubbleModule.class,
    StubSimSuggestionModule.class,
    StubSpamModule.class,
    StubSpeakEasyModule.class,
    SystemStrictModeModule.class,
    VoicemailModule.class,
  }
)
public interface GoogleStubDialerRootComponent extends BaseDialerRootComponent {}
