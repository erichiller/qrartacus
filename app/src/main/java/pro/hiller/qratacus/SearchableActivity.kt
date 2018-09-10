package pro.hiller.qratacus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.SearchManager
import android.content.Intent
import android.util.Log

// see https://developer.android.com/reference/android/widget/SearchView.html#setQueryRefinementEnabled(boolean)
// setQueryRefinementEnabled
// see SearchView https://developer.android.com/reference/android/widget/SearchView

class SearchableActivity : AppCompatActivity() {
    val TAG = "SearchableActivity"

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
        // nothing right now
    }
}


