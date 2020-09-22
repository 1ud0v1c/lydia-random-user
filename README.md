# Lydia - RandomUser API

## Subject:

Build an app that fetch data from this service : https://api.randomuser.me (use https://randomuser.me/api/1.0/?seed=lydia&results=10&page=1 to get 10 contacts for each api call, and increase page param to load more results).

- The app must display result in a list of first names and last names, and the email under it.
- The app must handle connectivity issue, and display the last results received if it can't retrieve one at launch.
- Touching an item of the list should make appear a details page listing every attributes.
- The app must be in Kotlin, any third-party libraries are allowed but you'll have to justify why you use them.
- Evaluate the time it should take before starting, and give it with your work, with the time it really took.

## Development estimation

Here is how I propose to develop the application : 

- First, search for inspiration for the look and feel of the application and try to implement it in a POC (2h)
- Then, after a brief API analysis, think about the conception of the model (which field is needed, and so on) (1h)
- Put in place a MVVM architecture and develop HomeActivity and fetching from the API (4h)
  - Install libraries (Retrofit/Gson, Glide, Coroutines, ...)
  - Develop HomeActivity (fetching with Retrofit and scrolling)
  - Unit tests the repository
- Add Room and handle switch between local & remote fetching depending on the connectivity (4h)
  - ConnectivityListener
  - Implementation in the repository
  - Unit tests switch between local & remote fetching
- Add DetailActivity on user click (3h) 
  - Pass id by Intent
  - Load data from DB
  - Unit tests the repository
- UI, UX & Performance (2h)
  - Instrumented tests
  - Design improvement
- Readme summary + "release" (tag & apk) (1h)
