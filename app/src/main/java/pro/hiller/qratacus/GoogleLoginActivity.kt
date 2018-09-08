package pro.hiller.qratacus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton


const val GOOGLE_LOGIN_REQUEST = 42


class GoogleLoginActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient ?= null
    private val TAG = this.localClassName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener {
            when (it.id) {
                R.id.sign_in_button -> signIn(mGoogleSignInClient)
            }// ...

        }


    }
    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivity(signInIntent)
//        val intent = Intent(this, GoogleLoginResultActivity::class.java ).apply {
////            putExtra(GOOGLE_LOGIN_REQUEST_STR, GOOGLE_LOGIN_REQUEST)
//        }
//        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if( account != null ){
            displayAccount(account)
            // the account was already logging into
            // don't run anything else
        }
        // do nothing
    }

    fun displayAccount(account: GoogleSignInAccount){
        val message = intent.getStringExtra(account.displayName)
        val textView = findViewById<TextView>(R.id.textView2).apply {
            text = message
        }
    }




}
