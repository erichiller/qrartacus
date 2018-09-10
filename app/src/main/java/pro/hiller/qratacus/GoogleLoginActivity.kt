package pro.hiller.qratacus

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.w3c.dom.Text


private val RC_SIGN_IN = 9001


class GoogleLoginActivity : AppCompatActivity() {

    private val TAG = "GoogleLoginActivity"

    private var mStatusTextView: TextView? = null

//    private val mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
        Log.d(TAG, "GoogleLoginActivity.onCreate()")

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        mStatusTextView = findViewById<TextView>(R.id.mStatusTextView)

        signInButton.setOnClickListener {
            when (it.id) {
                R.id.sign_in_button -> signIn(mGoogleSignInClient)
            }// ...
        }
    }
    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        Log.d(TAG, "in signIn()")
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.d(TAG, "account=" + account)
        if( account != null ){
            updateUI(account)
        }

        // the account was already logging into
        // don't run anything else
    }

//    fun displayAccount(account: GoogleSignInAccount){
//        val message = intent.getStringExtra(account.displayName)
//        Log.d(TAG, "displayAccount, sending " + message)
//        val textView = findViewById<TextView>(R.id.mStatusTextView).apply {
//            text = "Welcome:" + message
//        }
//    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG,"GoogleLoginResultActivity.onActivityResult() 1")
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG,"GoogleLoginResultActivity.onActivityResult() 2")

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG,"GoogleLoginResultActivity.onActivityResult() requestCode == GOOGLE_LOGIN_REQUEST")

            // The Task returned from this call is always completed, no need to attach a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.d(TAG,"GoogleLoginResultActivity.handleSignInResult()")
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val textView = findViewById<TextView>(R.id.mStatusTextView).apply {
                text = account.displayName
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

            val textView = findViewById<TextView>(R.id.mStatusTextView).apply {
                text = "Error with code" + e.statusCode
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        Log.d(TAG, "in update account with account=" + account)
        if (account != null) {
            Log.d(TAG, "account is non-null")
            mStatusTextView?.setText(getString(R.string.msg_fmt_welcome, account.displayName))

            findViewById<SignInButton>(R.id.sign_in_button).setVisibility(SignInButton.GONE)
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE)
        } else {
            Log.d(TAG, "account is null")
            mStatusTextView?.setText(R.string.msg_please_sign_in)
            findViewById<SignInButton>(R.id.sign_in_button).setVisibility(SignInButton.VISIBLE)
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE)
        }
    }
}
