package pro.hiller.qratacus

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.SearchView
import android.util.Log


const val EXTRA_MESSAGE = "pro.hiller.qrarticus.MESSAGE"

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate()")
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }

        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }
                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }
                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }
                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "onOptionsItemSelected()")
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
//            R.id.action_search -> {
//                mDrawerLayout.openDrawer(GravityCompat.START)
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun menuSelectSettings(item: MenuItem) {
        Log.v(TAG, "menuSelectSettings()")
        val intent = Intent(this, GoogleLoginActivity::class.java)
        startActivity(intent)
    }

    fun menuSelectAccount(item: MenuItem) {
        Log.v(TAG, "menuSelectAccount()")

        val intent = Intent(this, GoogleLoginActivity::class.java)
        startActivity(intent)
    }

    //temp
    fun menuSelectSearcher(item: MenuItem) {
        Log.v(TAG, "menuSelectSearcher()")

        val intent = Intent(this, SearchableActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.v(TAG, "onCreateOptionsMenu()")

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbar, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        // Configure the search info and add any event listeners...
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false) // Do not iconify the widget; expand it by default

        return super.onCreateOptionsMenu(menu)
    }

    /** Called when the user taps the Send button **/
    fun sendMessage(view: View) {
        Log.v(TAG, "sendMessage()")
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}
