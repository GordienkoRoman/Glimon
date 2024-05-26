package stud.gilmon.data.remote.network

object Network {
   /* private var okhttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val githubApi: GithubApi
        get() = retrofit?.create() ?: error("retrofit is not initialized")

    fun init(context: Context) {
        okhttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Timber.tag("NetworkInit").d(it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(AuthorizationInterceptor())
            .addNetworkInterceptor(AuthorizationFailedInterceptor(AuthorizationService(context), TokenStorage))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient!!)
            .build()
    }*/
}