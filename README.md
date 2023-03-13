PunkBeerExplorer

Description
This Android application is designed to display data from an API in a RecyclerView in a consistent manner.
The app uses Room, Retrofit, and Repository Pattern to fetch data from the API and store it in a local 
database. The app then displays this data in a RecyclerView and each item tap takes the user to
a more detailed view of itself.
The app is tested using JUnit4 and Mockito to ensure that it functions correctly.

Assumptions
As part of the assumptions made for this app, they are as follows:
-> The app should use a way of storing the data for offline access: RoomDB
-> The app must use an efficient way of displaying a list of items: RecyclerView
-> The app must refresh DB Contents on Launch

Features
The Android application provides the following features:
-> Displays data from an API in a RecyclerView and correspondent detail view
-> Stores data fetched from the API in a local database using Room
-> Uses Repository Pattern 
-> Uses Retrofit to fetch data from the API
-> Use JUnit4 and Mockito to test the app's functionality

Installation
To install the Android application, follow these steps:
-> Open PunkBeerExplorer project in Android Studio
-> Run the app on Android Device or emulator

Usage
To use the Android application, follow these steps:
-> Open the app on an Android device or emulator
-> The app will display a list of items in a RecyclerView
-> Click on an item to view its details
-> Use the system back button or the fragment's back button while on 
   the Detail Fragment in order to return to the list

Dependencies
The Android application uses the following dependencies:
-> Room
-> Retrofit
-> RecyclerView
-> LiveData
-> JUnit4
-> Mockito
-> ViewModel
-> Navigation Components
-> Glide

Possible improvements given more time spent:
-> Writing the app using Jetpack Compose instead of XML
-> Potentially turning the Repository into a Git Repository
-> Addition of UI tests