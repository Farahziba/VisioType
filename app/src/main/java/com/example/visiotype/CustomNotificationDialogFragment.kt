package com.example.visiotype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class CustomNotificationDialogFragment(private val warningType: Int, private val uid: String ) : DialogFragment() {

    private lateinit var rejectButton: Button
    private lateinit var allowButton: Button
    private lateinit var cancelButton: Button
    private lateinit var okButton: Button

    private var notificationInteractionListener: NotificationInteractionListener? = null
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var notificationStartTime: Long = 0
    private var secondnotificationStartTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (warningType) {
            1 -> inflater.inflate(R.layout.activity_warning_1, container, false)
            2 -> inflater.inflate(R.layout.activity_warning_2, container, false)
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent) // ← This sets transparent background
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        when (warningType) {
            1 -> {
                rejectButton = view.findViewById(R.id.btnReject)
                allowButton = view.findViewById(R.id.btnAllow)

                rejectButton.setOnClickListener {
                    val responseTime = System.currentTimeMillis() - notificationStartTime
                    logUserInteraction("reject", responseTime)
                    notificationInteractionListener?.onNotificationDismiss()
                    showSecondWarning()
                }

                allowButton.setOnClickListener {
                    val responseTime = System.currentTimeMillis() - notificationStartTime
                    logUserInteraction("allow", responseTime)
                    notificationInteractionListener?.onNotificationClick()
                    dismiss()
                }

                notificationStartTime = System.currentTimeMillis() // 🟢 For Warning 1
            }

            2 -> {
                cancelButton = view.findViewById(R.id.btnCancel)
                okButton = view.findViewById(R.id.btnOk)

                cancelButton.setOnClickListener {
                    val responseTime = System.currentTimeMillis() - secondnotificationStartTime
                    logUserInteraction("cancel", responseTime)
                    notificationInteractionListener?.onNotificationDismiss()
                    dismiss()
                }

                okButton.setOnClickListener {
                    val responseTime = System.currentTimeMillis() - secondnotificationStartTime
                    logUserInteraction("ok", responseTime)
                    notificationInteractionListener?.onNotificationClick()
                    dismiss()
                }

                secondnotificationStartTime = System.currentTimeMillis() // 🟢 Moved here
            }
        }
    }

    private fun showSecondWarning() {
        // Prevent showing Warning 2 again if already shown
        val secondWarningDialog = CustomNotificationDialogFragment(2, uid)
        secondWarningDialog.notificationInteractionListener = notificationInteractionListener
        secondWarningDialog.show(parentFragmentManager, "SecondWarningDialog")
        dismiss() // Close the current dialog (Warning 1)
    }

    private fun logUserInteraction(action: String, responseTime: Long? = null) {
        val userId = auth.currentUser?.uid ?: uid  // Use 'guest' if no user is logged in
        val warningTypeStr = if (warningType == 1) "warning_1" else "warning_2"

        // Prepare the data to log
        val actionData = hashMapOf<String, Any>(
            "action" to action,
            "timestamp" to System.currentTimeMillis(),
        )

        responseTime?.let {
            actionData["response_time"] = it
        }

        // Store the interaction in Firestore
        firestore.collection("user_interactions")
            .document(userId)  // Store under the user document
            .collection("warnings")
            .document(warningTypeStr)  // Store interactions under the respective warning
            .set(actionData)
            .addOnSuccessListener {
                // Optionally log success or handle further
            }
            .addOnFailureListener {
                // Handle errors if any
            }
    }

    fun setOnNotificationInteractionListener(listener: NotificationInteractionListener) {
        notificationInteractionListener = listener
    }

    interface NotificationInteractionListener {
        fun onNotificationClick()
        fun onNotificationDismiss()
    }
}

