package ru.alapplications.matdesign.kursproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_enter.*
import ru.alapplications.matdesign.kursproject.mainScreen.MainActivity


class EnterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        enterButton.setOnClickListener { enter() }
        closeButton.setOnClickListener{finish()}
    }

    private fun enter() = if (nameTextInputEditText.text!!.isEmpty()) showErrorSnackBar() else callMainActivity()

    private fun showErrorSnackBar() =
        Snackbar.make(enterActivityConstraintLayout,getString(R.string.EmptyTextError),Snackbar.LENGTH_LONG).show()

    private fun callMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}