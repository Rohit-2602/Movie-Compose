<h1 align="center">Movie Compose</h1>
  
<p align="center">  
Movie Compose is an Application based on Modern Android tech-stacks especially focus on JetPack Compose UI using "The Movie DB API". Fetcheing data from network and integrating persisted data in the database via repository pattern.
<br>
</p>
<br>

<p align="center">
<img src="/previews/preview-image.png" width=900>
</p>

<img src="/previews/preview-gif.gif" align="right" width="30%"/>

## Techs Used 💻
- 100% [Kotlin](https://kotlinlang.org/) based
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
- [Dagger-Hilt](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that is lifecycle aware (didn't destroyed on UI changes).
  - [Room Persistance](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database.
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.

## Application Install
You can Install and test the app from below 👇

[![Movie Compose App](https://img.shields.io/badge/Movie_Compose-APK-silver.svg?style=for-the-badge&logo=android)](https://github.com/Rohit-2602/Movie-Compose/releases/tag/release-1.0.0)

## Find this repository useful? ❤️
Support it by joining [stargazers](https://github.com/Rohit-2602/Movie-Compose/stargazers) for this repository. ⭐
And [follow](https://github.com/Rohit-2602) me for my next creations! 🤩

## SetUp Project
Add your [The Movie DB](https://www.themoviedb.org/)'s API key in gradle.properties file.
```XML
api_key = "Your API Key"
```

