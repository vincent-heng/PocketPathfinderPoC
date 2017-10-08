package fr.project24.fox.pocketpathfinder.retrofit

/**
 * Repository method to access search functionality of the api service
 */
class PPSApi(val apiService: PPSApiService) {
    fun findStoryByRoom(roomName: String): io.reactivex.Observable<Result> {
        return apiService.findStoryByRoom(query = "roomName:$roomName")
    }
}