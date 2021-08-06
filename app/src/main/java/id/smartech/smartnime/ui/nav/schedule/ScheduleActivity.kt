package id.smartech.smartnime.ui.nav.schedule

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityScheduleBinding

class ScheduleActivity : BaseActivity<ActivityScheduleBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_schedule
        super.onCreate(savedInstanceState)

        setSpinner()
    }

    private fun setSpinner() {
        val roleOption = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Other", "Unknown")
        bind.spinnerDay.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, roleOption)
        bind.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@ScheduleActivity, roleOption[position], Toast.LENGTH_SHORT).show()
            }
        }
    }
}