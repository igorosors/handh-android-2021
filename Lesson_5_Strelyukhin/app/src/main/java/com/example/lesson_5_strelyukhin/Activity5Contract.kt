package com.example.lesson_5_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Activity5Contract : ActivityResultContract<Unit, String>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Activity5.createStartIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return intent?.getStringExtra(Activity5.EXTRA_RESULT_INPUT).orEmpty()
    }
}