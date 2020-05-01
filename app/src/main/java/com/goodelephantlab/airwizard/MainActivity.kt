package com.goodelephantlab.airwizard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.ui.main.MainFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        val viewModel: MainViewModel = getViewModel()
        viewModel.errorLD.observe(this@MainActivity, Observer {
            Snackbar.make(container, it ?: getText(R.string.unknown_error), Snackbar.LENGTH_SHORT).show()
        })
    }
}
