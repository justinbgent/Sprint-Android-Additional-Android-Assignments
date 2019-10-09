package com.example.idea_opensourceideas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setSupportActionBar(toolbar)
        //toolbar.title = "Idea"

        val toggle = ActionBarDrawerToggle(this, drawer_layout,
            toolbar, R.string.open_drawer, R.string.close_drawer)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId){
                R.id.ideas_tech -> {
                    Toast.makeText(this, "Show Tech Ideas", Toast.LENGTH_SHORT).show()
                }
                R.id.ideas_game -> {
                    Toast.makeText(this, "Show Game Ideas", Toast.LENGTH_SHORT).show()
                }
            }

            drawer_layout.closeDrawers()

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sign_in -> {
                Toast.makeText(this, "Sign In", Toast.LENGTH_SHORT).show()
            }
            R.id.sign_out -> {
                Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
