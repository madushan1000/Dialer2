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

package com.android.dialer2.binary.basecomponent;

import com.android.dialer2.calllog.CallLogComponent;
import com.android.dialer2.calllog.database.CallLogDatabaseComponent;
import com.android.dialer2.calllog.ui.CallLogUiComponent;
import com.android.dialer2.commandline.CommandLineComponent;
import com.android.dialer2.common.concurrent.DialerExecutorComponent;
import com.android.dialer2.configprovider.ConfigProviderComponent;
import com.android.dialer2.duo.DuoComponent;
import com.android.dialer2.enrichedcall.EnrichedCallComponent;
import com.android.dialer2.feedback.FeedbackComponent;
import com.android.dialer2.glidephotomanager.GlidePhotoManagerComponent;
import com.android.dialer2.main.MainComponent;
import com.android.dialer2.metrics.MetricsComponent;
import com.android.dialer2.phonelookup.PhoneLookupComponent;
import com.android.dialer2.phonenumbergeoutil.PhoneNumberGeoUtilComponent;
import com.android.dialer2.precall.PreCallComponent;
import com.android.dialer2.preferredsim.suggestion.SimSuggestionComponent;
import com.android.dialer2.simulator.SimulatorComponent;
import com.android.dialer2.spam.SpamComponent;
import com.android.dialer2.storage.StorageComponent;
import com.android.dialer2.strictmode.StrictModeComponent;
import com.android.incallui.calllocation.CallLocationComponent;
import com.android.incallui.maps.MapsComponent;
import com.android.incallui.speakeasy.SpeakEasyComponent;
import com.android.newbubble.NewBubbleComponent;
import com.android.voicemail.VoicemailComponent;

/**
 * Base class for the core application-wide component. All variants of the Dialer app should extend
 * from this component.
 */
public interface BaseDialerRootComponent
    extends CallLocationComponent.HasComponent,
        CallLogComponent.HasComponent,
        CallLogDatabaseComponent.HasComponent,
        CallLogUiComponent.HasComponent,
        ConfigProviderComponent.HasComponent,
        CommandLineComponent.HasComponent,
        DialerExecutorComponent.HasComponent,
        DuoComponent.HasComponent,
        EnrichedCallComponent.HasComponent,
        FeedbackComponent.HasComponent,
        GlidePhotoManagerComponent.HasComponent,
        MainComponent.HasComponent,
        MapsComponent.HasComponent,
        MetricsComponent.HasComponent,
        NewBubbleComponent.HasComponent,
        PhoneLookupComponent.HasComponent,
        PhoneNumberGeoUtilComponent.HasComponent,
        PreCallComponent.HasComponent,
        SimSuggestionComponent.HasComponent,
        SimulatorComponent.HasComponent,
        SpamComponent.HasComponent,
        SpeakEasyComponent.HasComponent,
        StorageComponent.HasComponent,
        StrictModeComponent.HasComponent,
        VoicemailComponent.HasComponent {}
