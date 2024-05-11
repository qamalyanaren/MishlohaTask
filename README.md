# MishlohaTask Android Project

Welcome to the MishlohaTask project! This Android application is designed to showcase trending repositories on GitHub with a focus on clean architecture principles. Here’s a breakdown of the project's key details:

## Architecture

### MVVM and Clean Architecture

We adopted the MVVM (Model-View-ViewModel) pattern while adhering to Clean Architecture principles to create a well-structured and maintainable project.

1. **Domain Layer**: Contains business logic and use cases.
2. **Data Layer**: Manages data from various sources (network, database).
3. **CoreUI Layer**: Manages all UI components, including resources and drawables.

### UI Components
- **Bottom Navigation**: To switch between the Home and Favorite screens.
- **Home Screen**: Displays trending repositories in a sortable list.
- **Favorite Screen**: Shows the repositories marked as favorite. It’s backed by a Room database and functions even in offline mode.
- **Detail Screen**: Written in Java, provides a detailed view of a selected repository.

### Asynchronous Programming
- **Kotlin Code**: Uses Kotlin coroutines for asynchronous programming.
- **Java Code**: Implements asynchronous programming using RxJava3 and RxAndroid3.

### Networking and Dependency Injection
- **Retrofit & OkHttp**: Manage API requests and responses.
- **Dagger Hilt**: Ensures proper dependency injection throughout the app.

## Features

### Core Features

- **Timeframe Selection**: Users can select trending repositories from the last day, week, or month.
- **Repository Details**: Each repository displays:
  - Owner’s username and avatar.
  - Repository name and description.
  - Stargazers count.

- **Infinite Scrolling**: The list supports infinite scrolling to load more repositories as you reach the bottom.
- **Detail Screen**: Repository details include language, number of forks, creation date, and a link to the GitHub page.
- **Favorites**: Users can add repositories to a local favorite list that’s available offline.

## Areas for Improvement

### Unimplemented Features
- **UI Testing**: Needs comprehensive UI testing for all main features.
- **Search for Each Page**: Implement separate search capabilities for each page.

## APK and Local Setup

- **APK**: The APK file is available in the GitHub artifact section.
- **Local Development**: To run the project locally, you'll need a GitHub authentication token. Add a `local.properties` file to your project root and include the following:

    ```properties
    github.token=<your-account-token>
    ```


