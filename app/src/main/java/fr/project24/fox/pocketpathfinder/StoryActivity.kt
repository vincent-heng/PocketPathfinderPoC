package fr.project24.fox.pocketpathfinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import fr.project24.fox.pocketpathfinder.retrofit.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.android.synthetic.main.content_story.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick

class StoryActivity : AppCompatActivity(), AnkoLogger {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        fab.onClick {
            retrieveStory()
        }
    }

    private fun retrieveStory() {
        val repository = SearchRepositoryProvider.provideSearchRepository()
        compositeDisposable.add(
                repository.findStoryByRoom("azerty")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            info("Found " + result.items.size + "stories")
                            story_text.text = result.items[0].storyContent
                        }, { error ->
                            error.printStackTrace()
                        })
        )
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

}
