package fr.project24.fox.pocketpathfinder.retrofit

import com.google.gson.annotations.SerializedName

data class Result (
        val total_count: Int,
        val incomplete_results: Boolean,
        val items: List<Story>
)

data class Story(
        @SerializedName("story_content") val storyContent: String
)