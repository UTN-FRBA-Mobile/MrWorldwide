package mobile.frba.utn.tpmobile.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.NavigatorFragment
import mobile.frba.utn.tpmobile.singletons.Navigator
import net.danlew.android.joda.JodaTimeAndroid

class MainActivity : AppCompatActivity() {

    private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.main_activity)
        navigator.setNavigationView(findViewById(R.id.bottom_navigation))
    }

    fun navigateTo(fragment : NavigatorFragment){
        navigator.navigateTo(fragment)
    }
}
