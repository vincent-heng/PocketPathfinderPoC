package fr.project24.fox.pocketpathfinder.retrofit

object SearchRepositoryProvider {

    fun provideSearchRepository(): PPSApi {
        return PPSApi(PPSApiService.Factory.create())
    }

}