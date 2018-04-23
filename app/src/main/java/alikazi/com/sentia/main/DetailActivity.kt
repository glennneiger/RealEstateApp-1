package alikazi.com.sentia.main

import alikazi.com.sentia.R
import alikazi.com.sentia.models.Property
import alikazi.com.sentia.utils.AppConf
import alikazi.com.sentia.utils.DLog
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_property_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = AppConf.LOG_TAG_MAIN
        const val INTENT_EXTRA_BUNDLE = "INTENT_EXTRA_BUNDLE"
        const val INTENT_EXTRA_PROPERTY = "INTENT_EXTRA_PROPERTY"
    }

    private var mProperty: Property? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)
        initToolbar()

        intent?.extras?.let {
            if (it.containsKey(INTENT_EXTRA_BUNDLE)) {
                var bundle = it.getBundle(INTENT_EXTRA_BUNDLE)
                if (bundle.containsKey(INTENT_EXTRA_PROPERTY)) {
                    mProperty = it.getParcelable(INTENT_EXTRA_PROPERTY) as Property
                    DLog.d(LOG_TAG, "mProperty != null")
                }
            }
        }

        initDetailsFragment()
    }

    private fun initDetailsFragment() {
        val fragment = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailsFragment.INTENT_EXTRA_PROPERTY, mProperty)
            }
        }
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.property_detail_activity_fragment_container, fragment)
                .commit()
    }

    private fun initToolbar() {
        DLog.i(LOG_TAG, "initToolbar")
        setSupportActionBar(property_detail_toolbar)
        supportActionBar?.setTitle(R.string.toolbar_title_property_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?)= when(item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }
}
