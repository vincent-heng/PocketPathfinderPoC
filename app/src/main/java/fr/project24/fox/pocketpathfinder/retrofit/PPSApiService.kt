package fr.project24.fox.pocketpathfinder.retrofit

interface PPSApiService {

    @retrofit2.http.GET("search/users")
    fun search(@retrofit2.http.Query("q") query: String,
               @retrofit2.http.Query("page") page: Int = 1,
               @retrofit2.http.Query("per_page") perPage: Int = 20): io.reactivex.Observable<Result>

    @retrofit2.http.GET("stories")
    fun findStoryByRoom(@retrofit2.http.Query("q") query: String,
                        @retrofit2.http.Query("page") page: Int = 1,
                        @retrofit2.http.Query("per_page") perPage: Int = 3): io.reactivex.Observable<Result>

    /**
     * Companion object for the factory
     */
    companion object Factory {
        fun create(): PPSApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(PPSApiService::class.java)
        }
    }
}