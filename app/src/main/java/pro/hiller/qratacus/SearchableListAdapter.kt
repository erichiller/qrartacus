package pro.hiller.qratacus

import android.app.ListActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.widget.ListAdapter
import android.widget.SimpleCursorAdapter


public class SearchableListAdapter : ListActivity() {
    val TAG = "SearchableListAdapter"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchableListAdapter.onCreate")
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.adapter_searchable_list);

        // Query for all people contacts using the Contacts.People convenience class.
        // Put a managed wrapper around the retrieved cursor so we don't have to worry about
        // requerying or closing it as the activity changes state.
        var mCursor = this.getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        startManagingCursor(mCursor);

        var contacts: Array<String> = arrayOf(Contacts.People.NAME, Contacts.People.NOTES)
        var templates: IntArray = intArrayOf(R.id.text1, R.id.text2)

        // Now create a new list adapter bound to the cursor.
        // SimpleListAdapter is designed for binding to a Cursor.
        var adapter: ListAdapter = SimpleCursorAdapter(
                this, // Context.
                android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
                mCursor,                                              // Pass in the cursor to bind to.
                contacts,           // Array of cursor columns to bind to.
                templates,
                0);  // Parallel array of which template objects to bind to those columns.

        // Bind to our new adapter.
        setListAdapter(adapter);
    }
}