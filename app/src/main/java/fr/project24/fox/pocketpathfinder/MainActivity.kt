package fr.project24.fox.pocketpathfinder

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import fr.project24.fox.pocketpathfinder.R.id.room_name_display
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        info("MainActivity created")
        info("Network State: " + if (isNetworkConnected()) "Connected" else "No Network")

        prepareActivity()
    }

    override fun onResume() {
        super.onResume()
        prepareActivity()
    }

    private fun prepareActivity() {
        val isInARoom = loadRoomName()
        bindButtons(isInARoom)
    }

    private fun bindButtons(isInARoom: Boolean) {
        /* Public buttons */
        login_button.onClick {
            startActivity<LoginActivity>("id" to 5)
        }

        doc_button.onClick {
            val pages = listOf("Page principale", "Création personnage", "Races", "Classes")
            val pagesURL = listOf(
                    "http://www.pathfinder-fr.org/Wiki/Pathfinder-RPG.MainPage.ashx",
                    "http://www.pathfinder-fr.org/Wiki/Pathfinder-RPG.Cr%c3%a9er%20un%20personnage.ashx",
                    "http://www.pathfinder-fr.org/Wiki/Pathfinder-RPG.races.ashx",
                    "http://www.pathfinder-fr.org/Wiki/Pathfinder-RPG.classes.ashx")
            selector("Quelle page ?", pages, { dialogInterface, i ->
                info("Browsing ${pages[i]}")
                browse(pagesURL[i])
            })
        }

        tools_button.onClick {
            toast("Not implemented yet!")
        }

        if (!isInARoom) {
            return
        }

        /* In-Room buttons */
        story_button.visibility = View.VISIBLE
        story_button.onClick {
            startActivity<StoryActivity>()
        }

        characters_button.visibility = View.VISIBLE
        characters_button.onClick {
            startActivity<CharactersActivity>()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun loadRoomName(): Boolean {
        val roomName = getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("roomName", "")
        if (roomName.isEmpty()) {
            room_name_display.text = getString(R.string.default_room_name)
            return false
        }
        room_name_display.text = "Salle : " + roomName
        return true
    }
}
