/*
 * Copyright (C) 2012 The Android Open Source Project
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
package com.example.android.basiccontactables



import android.app.Activity
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.widget.AdapterView;import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import android.widget.TextView
import android.view.View

/**
 * Helper class to handle all the callbacks that occur when interacting with loaders.  Most of the
 * interesting code in this sample app will be in this file.
 */
class ContactsFragment: Fragment() , LoaderManager.LoaderCallbacks<Cursor> {
    override fun onCreateLoader(loaderIndex: Int, args: Bundle): Loader<Cursor> {
        // Where the Contactables table excels is matching text queries,
        // not just data dumps from Contacts db.  One search term is used to query
        // display name, email address and phone number.  In this case, the query was extracted
        // from an incoming intent in the handleIntent() method, via the
        // intent.getStringExtra() method.

        // BEGIN_INCLUDE(uri_with_query)
        val query = args.getString(QUERY_KEY)
        val uri = Uri.withAppendedPath(
                CommonDataKinds.Contactables.CONTENT_FILTER_URI, query)
        // END_INCLUDE(uri_with_query)


        // BEGIN_INCLUDE(cursor_loader)
        // Easy way to limit the query to contacts with phone numbers.
        val selection = CommonDataKinds.Contactables.HAS_PHONE_NUMBER + " = " + 1

        // Sort results such that rows for the same contact stay together.
        val sortBy = CommonDataKinds.Contactables.LOOKUP_KEY

        return CursorLoader(
                mContext, // Context
                uri, null, // projection - the list of columns to return.  Null means "all"
                selection, null, // selection args - can be provided separately and subbed into selection.
                sortBy)// URI representing the table/resource to be queried
        // selection - Which rows to return (condition rows must match)
        // string specifying sort order
        // END_INCLUDE(cursor_loader)
    }

    override fun onLoadFinished(arg0: Loader<Cursor>, cursor: Cursor) {
        val tv = (mContext as Activity).findViewById(R.id.sample_output) as TextView
        if (tv == null) {
            Log.e(TAG, "TextView is null?!")
        } else if (mContext == null) {
            Log.e(TAG, "Context is null?")
        } else {
            Log.e(TAG, "Nothing is null?!")
        }

        // Reset text in case of a previous query
        tv.text = mContext!!.getText(R.string.intro_message).toString() + "\n\n"

        if (cursor.count == 0) {
            return
        }

        // Pulling the relevant value from the cursor requires knowing the column index to pull
        // it from.
        // BEGIN_INCLUDE(get_columns)
        val phoneColumnIndex = cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)
        val emailColumnIndex = cursor.getColumnIndex(CommonDataKinds.Email.ADDRESS)
        val nameColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.DISPLAY_NAME)
        val lookupColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.LOOKUP_KEY)
        val typeColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.MIMETYPE)
        // END_INCLUDE(get_columns)

        cursor.moveToFirst()
        // Lookup key is the easiest way to verify a row of data is for the same
        // contact as the previous row.
        var lookupKey = ""
        do {
            // BEGIN_INCLUDE(lookup_key)
            val currentLookupKey = cursor.getString(lookupColumnIndex)
            if (lookupKey != currentLookupKey) {
                val displayName = cursor.getString(nameColumnIndex)
                tv.append(displayName + "\n")
                lookupKey = currentLookupKey
            }
            // END_INCLUDE(lookup_key)

            // BEGIN_INCLUDE(retrieve_data)
            // The data type can be determined using the mime type column.
            val mimeType = cursor.getString(typeColumnIndex)
            if (mimeType == CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {
                tv.append("\tPhone Number: " + cursor.getString(phoneColumnIndex) + "\n")
            } else if (mimeType == CommonDataKinds.Email.CONTENT_ITEM_TYPE) {
                tv.append("\tEmail Address: " + cursor.getString(emailColumnIndex) + "\n")
            }
            // END_INCLUDE(retrieve_data)

            // Look at DDMS to see all the columns returned by a query to Contactables.
            // Behold, the firehose!
            for (column in cursor.columnNames) {
                Log.d(TAG, column + column + ": " +
                        cursor.getString(cursor.getColumnIndex(column)) + "\n")
            }
        } while (cursor.moveToNext())
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {}

    companion object {

        val QUERY_KEY = "query"

        val TAG = "ContactablesLoaderCallbacks"
    }
}