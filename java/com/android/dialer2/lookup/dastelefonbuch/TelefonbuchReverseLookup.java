/*
 * Copyright (C) 2014 Danny Baumann <dannybaumann@web.de>
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

package com.android.dialer2.lookup.dastelefonbuch;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;

import com.android.dialer2.phonenumbercache.ContactInfo;
import com.android.dialer2.lookup.ContactBuilder;
import com.android.dialer2.lookup.ReverseLookup;

import java.io.IOException;

public class TelefonbuchReverseLookup extends ReverseLookup {
    private static final String TAG = TelefonbuchReverseLookup.class.getSimpleName();

    public TelefonbuchReverseLookup(Context context) {
    }

    /**
     * Perform phone number lookup.
     *
     * @param context The application context
     * @param normalizedNumber The normalized phone number
     * @param formattedNumber The formatted phone number
     * @return The phone number info object
     */
    public ContactInfo lookupNumber(Context context,
            String normalizedNumber, String formattedNumber) throws IOException {
        if (normalizedNumber.startsWith("+") && !normalizedNumber.startsWith("+49")) {
            // Das Telefonbuch only supports German numbers
            return null;
        }

        TelefonbuchApi.ContactInfo info = TelefonbuchApi.reverseLookup(context, normalizedNumber);
        if (info == null) {
            return null;
        }

        ContactBuilder builder = new ContactBuilder(
                ContactBuilder.REVERSE_LOOKUP,
                normalizedNumber, formattedNumber);
        builder.setName(ContactBuilder.Name.createDisplayName(info.name));
        builder.addPhoneNumber(ContactBuilder.PhoneNumber.createMainNumber(info.formattedNumber));
        builder.addWebsite(ContactBuilder.WebsiteUrl.createProfile(info.website));
        if (info.address != null) {
            builder.addAddress(ContactBuilder.Address.createFormattedHome(info.address));
        }

        return builder.build();
    }
}
