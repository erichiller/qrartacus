package pro.hiller.qratacus

import android.app.ListActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.SearchManager
import android.content.Intent
import android.provider.ContactsContract;
import android.util.Log
import android.widget.ListAdapter
import android.widget.SimpleCursorAdapter

// see https://developer.android.com/reference/android/widget/SearchView.html#setQueryRefinementEnabled(boolean)
// setQueryRefinementEnabled
// see SearchView https://developer.android.com/reference/android/widget/SearchView

class SearchableActivity : ListActivity() {
    val TAG = "SearchableActivity"
    // An adapter that binds the result Cursor to the ListView
    private var mCursorAdapter: SimpleCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "onCreateOptionsMenu()")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)    // https://developer.android.com/guide/topics/search/search-dialog#InvokingTheSearchDialog

        // Get the intent, verify the action and get the query
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            query(query)
        }
    }

    fun query(query: String){
        Log.v(TAG, "query()")



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


