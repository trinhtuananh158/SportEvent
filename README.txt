1. Approach I’ve taken (Architecture, frameworks or libraries used) and
explain why I’ve selected it for the sample Android application:

+ Architecture:
	- Model-View-ViewModel (MVVM) Architecture:
	* Reasoning: MVVM separates concerns by organizing code into three main components: Model (data), View (UI), and ViewModel (business logic). This separation enhances maintainability, testability, and scalability.
+ Frameworks and Libraries:
	- Android Jetpack:
	* Reasoning: Android Jetpack is a set of libraries, tools, and architectural guidance provided by Google. It includes components such as LiveData, ViewModel and more, which help in building robust and scalable Android applications.
	- Retrofit:
	* Reasoning: Retrofit is a powerful HTTP client library for Android and Java. It simplifies network requests, handles serialization/deserialization, and supports asynchronous programming. It integrates well with Kotlin and is commonly used for making API calls.
	- Dagger Hilt (Dependency Injection):
	* Reasoning: Dagger Hilt is a dependency injection library for Android that simplifies dependency injection and provides compile-time safety. It's built on top of Dagger and is recommended by Google for Android development.
	- Espresso and JUnit:
	* Reasoning: Espresso is the standard UI testing framework for Android. JUnit is commonly used for unit testing. Together, they help ensure the reliability and correctness of the application through automated testing.

2. Functionalities or technical details that I wanted to add if I had
additional time
+ Animations: Integrate smooth animations to enhance the user experience, such as transition animations between activities or fragment transitions.
+ User Authentication: Implement user authentication to enable personalized features, user accounts, and secure data access.
+ Search Functionality: Add a search feature to help users quickly find matches per team within the app.
+ Error Handling: Enhance error handling by providing informative error messages and handling various network and data-related scenarios.
+ Localization: Provide support for multiple languages to make the app accessible to a broader audience.