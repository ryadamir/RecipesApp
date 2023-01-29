### Hi there <img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="25px">

#### This is a practice project that I developped from scratch, it's called RecipesApp.

##### Please keep in mind that some of these libraries were implemented only to showcase my skills, and show my mastery of what's trending right now, that's why I used Kotlin even that I'm better at Java 😅, it is a very simple project so it could have been made with simple implementations.

## Features
- Login view
- Sign up view
- Home view
- Detail of recipe view
- Settings view
- Logout frature

## Library References

- Retrofit2 [https://square.github.io/retrofit/]
    - Used in this project as a REST Client to retrieve JSON file.
    
- Koin [https://insert-koin.io/]
    - For the dependancy injection in this project, it is simple, easy to use and lightweight than Dagger or Spring, it's supported in AndroidX and gives a clean maintainable and scalable code as asked.
    
- RXJava2 [https://github.com/ReactiveX/RxJava]
    - Used with Retrofit2 in the under Model layer for the request and reception of data from the server or from the local database in order to handle asynchronous data streams and events.
    
- Live Data [https://developer.android.com/topic/libraries/architecture/livedata]
    - It allows data to be observable within the app's lifecycle, ensuring that the data is always up-to-date and the UI can be updated accordingly (not very important in this simple project).
    
- Kotlin coroutines [https://developer.android.com/kotlin/coroutines]
    - It allows threads that to be suspended and resumed, for the execution of long-running tasks without blocking the main thread (not very important in this simple project).
    
- View Binding [https://developer.android.com/topic/libraries/view-binding]

- MVVM Design Pattern 
    - An Architecture Pattern in Android, used it for this project beacause it's a "trending" and most common architecture for now.
    
- SQLite [https://www.sqlite.org/]
    - For storing data in local database.

- Glide [https://github.com/bumptech/glide]
    - For image loading, it has a good cash memory for offline use, that's why I like using it. 
    
- ImageSlider [https://github.com/smarteist/Android-Image-Slider]
    - For image slides in Home page. 

## Testing
- Generated some UI tests using the Espresso Test for the Login and Sign up features as examples.

## Data used in this project
<a href="https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json">
  <span>recipes.json<span/>
</a>
