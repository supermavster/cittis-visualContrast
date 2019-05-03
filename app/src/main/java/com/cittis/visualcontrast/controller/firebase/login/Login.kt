package com.cittis.visualcontrast.controller.firebase.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.Navigation
import com.cittis.visualcontrast.R
import com.cittis.visualcontrast.actions.ActionsRequest
import com.cittis.visualcontrast.actions.EndPoints
import com.cittis.visualcontrast.connection.DataBase
import com.cittis.visualcontrast.controller.firebase.tracking.TrackerService
import com.cittis.visualcontrast.model.CittisList
import com.cittis.visualcontrast.model.DataUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class Login : Fragment() {

    // Main Variables
    private lateinit var viewMain: View
    private lateinit var connection: DataBase
    // Make Bundle
    val bundle = Bundle()
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    // Google SignIn
    private lateinit var googleSignInClient: GoogleSignInClient


    companion object {
        private const val TAG = "EmailPassword"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Init View
        viewMain = inflater.inflate(R.layout.fragment_login, container, false)

        // Init Process
        initProcess()

        // Return Data
        return viewMain
    }

    private fun initProcess() {
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(viewMain.context, gso)


        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // Actions
        setCreateAccount()
        setSignInGoogle()
        setSignIn()
        setSignOut()
        setSendEmailVerification()

    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun setCreateAccount() {
        viewMain.findViewById<Button>(R.id.emailCreateAccountButton).setOnClickListener {
            var email = viewMain.findViewById<EditText>(R.id.fieldEmail).text.toString()
            var password = viewMain.findViewById<EditText>(R.id.fieldPassword).text.toString()
            createAccount(email, password)
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            // .addOnCompleteListener(viewMain.context) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    viewMain.context, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                updateUI(null)
            }

            // [START_EXCLUDE]
            hideProgressDialog()
            // [END_EXCLUDE]
        }
        // [END create_user_with_email]
    }

    private fun setSignInGoogle() {
        viewMain.findViewById<com.google.android.gms.common.SignInButton>(R.id.signInButton).setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, ActionsRequest.RC_SIGN_IN)
        }
    }


    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == ActionsRequest.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        showProgressDialog()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(
                        viewMain.findViewById<LinearLayout>(R.id.main_layout),
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                hideProgressDialog()
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]


    private fun setSignIn() {

        viewMain.findViewById<Button>(R.id.emailSignInButton).setOnClickListener {
            var email = viewMain.findViewById<EditText>(R.id.fieldEmail).text.toString()
            var password = viewMain.findViewById<EditText>(R.id.fieldPassword).text.toString()
            signIn(email, password)

        }

    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            // .addOnCompleteListener(viewMain.context) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                updateUI(user)

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    viewMain.context, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                updateUI(null)
            }

            // [START_EXCLUDE]
            if (!task.isSuccessful) {
                viewMain.findViewById<TextView>(R.id.status).setText(R.string.auth_failed)
            }
            hideProgressDialog()
            // [END_EXCLUDE]
        }
        // [END sign_in_with_email]
    }


    private fun setSignOut() {
        viewMain.findViewById<Button>(R.id.signOutButton).setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener {
            updateUI(null)
        }
    }

    private fun setSendEmailVerification() {
        viewMain.findViewById<Button>(R.id.verifyEmailButton).setOnClickListener {
            sendEmailVerification()
        }
    }

    private fun sendEmailVerification() {
        // Disable button
        viewMain.findViewById<Button>(R.id.verifyEmailButton).isEnabled = false

        // Send verification email
        // [START send_email_verification]
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            // [START_EXCLUDE]
            // Re-enable button
            viewMain.findViewById<Button>(R.id.verifyEmailButton).isEnabled = true

            if (task.isSuccessful) {
                Toast.makeText(
                    viewMain.context,
                    "Verification email sent to ${user.email} ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.e(TAG, "sendEmailVerification", task.exception)
                Toast.makeText(
                    viewMain.context,
                    "Failed to send verification email.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // [END_EXCLUDE]
        }
        // [END send_email_verification]
    }

    // [END on_start_check_user]
    private fun updateUI(user: FirebaseUser?) {

        hideProgressDialog()
        if (user != null) {
            viewMain.findViewById<TextView>(R.id.status).text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            viewMain.findViewById<TextView>(R.id.detail).text = getString(R.string.firebase_status_fmt, user.uid)


            viewMain.findViewById<LinearLayout>(R.id.emailPasswordButtons).visibility = View.GONE
            viewMain.findViewById<LinearLayout>(R.id.emailPasswordFields).visibility = View.GONE
            viewMain.findViewById<LinearLayout>(R.id.signedInButtons).visibility = View.VISIBLE

            viewMain.findViewById<Button>(R.id.verifyEmailButton).isEnabled = !user.isEmailVerified


            // Check is Login and Verify
            checkLogin(user)
        } else {
            viewMain.findViewById<TextView>(R.id.status).setText(R.string.signed_out)
            viewMain.findViewById<TextView>(R.id.detail).text = null

            viewMain.findViewById<LinearLayout>(R.id.emailPasswordButtons).visibility = View.VISIBLE
            viewMain.findViewById<LinearLayout>(R.id.emailPasswordFields).visibility = View.VISIBLE
            viewMain.findViewById<LinearLayout>(R.id.signedInButtons).visibility = View.GONE
        }
    }


    private fun checkLogin(user: FirebaseUser?) {
        // Check Login - Email Verification
        var isLogin = user!!.isEmailVerified
        if (isLogin) {

            // Flag Data - Traking
            EndPoints.FireBaseID = user.uid

            // Data - User
            var dataUser = DataUser(user.email.toString(), user.uid, user.isEmailVerified.toInt())
            EndPoints.FireBasePath = dataUser.firebase_path


            // Make Object Main
            var cittisDB = CittisList(1,dataUser,null,null)
            // Show Data
            Log.e("Data-Login", cittisDB.toString())
            // Set and Send Data Main
            bundle.putParcelable("CittisDB", cittisDB)
            // Start Tracking
            startServiceTracking()
            // Init Action
            Navigation.findNavController(viewMain).navigate(R.id.municipalities, bundle)

        } else {
            Toast.makeText(
                viewMain.context, "Please Verify Email.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun startServiceTracking() {
        val intent = Intent(viewMain.context, TrackerService::class.java)
        // start your next activity
        viewMain.context.startService(intent)
    }

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(viewMain.context)
    }

    fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm = viewMain.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = viewMain.findViewById<EditText>(R.id.fieldEmail).text.toString()
        if (TextUtils.isEmpty(email)) {
            viewMain.findViewById<EditText>(R.id.fieldEmail).error = "Required."
            valid = false
        } else {
            viewMain.findViewById<EditText>(R.id.fieldEmail).error = null
        }

        val password = viewMain.findViewById<EditText>(R.id.fieldPassword).text.toString()
        if (TextUtils.isEmpty(password)) {
            viewMain.findViewById<EditText>(R.id.fieldPassword).error = "Required."
            valid = false
        } else {
            viewMain.findViewById<EditText>(R.id.fieldPassword).error = null
        }

        return valid
    }

    fun Boolean.toInt() = if (this) 1 else 0


}
