### 1. Project Title:
   - **Title**: Photo Gallery - Efficient Image Loader for Android

### 2. Project Description:
   - **Description**: This Android application efficiently loads and displays images in a scrollable grid using the Unsplash API without relying on any third-party image loading library.

### 3. Getting Started:
   - **Prerequisites**: 
     - Android Studio
     - A device or emulator running Android API level 24 or higher
   - **Setup Instructions**: 
     1. Clone this repository to your local machine.
     2. Open the project in Android Studio.
     3. Build and run the application on a device or emulator.
        Note: you can change the number of images by making changes to the api call in MainActivity @ line 41.

### 4. Usage:
   - **Usage Instructions**: 
     - Upon launching the app, you'll be presented with a scrollable grid of images fetched from the Unsplash API.
     - Scroll vertically to view more images.

### 5. Features:
   - **Main Features**:
     - Asynchronous image loading from Unsplash API URLs.
     - Efficient scrollable grid implementation capable of handling a large number of images.
     - Caching mechanism for storing images in memory.
     - Graceful error handling for network errors and image loading failures.

### 6. Technologies Used:
   - **Technologies**:
     - Implemented in Kotlin.
     - Utilizes Android SDK for development.
     - Used MVVM Architecture.
     - Used Room Datatbase and Cache Memory.
     - Used Hilt for Dependency Injection of Retrofit Api Builder and Room Dao.

### 7. Error Handling:
   - **Error Handling Approach**:
     - Informative error messages are displayed to the user in case of network errors or image loading failures.
     - Different placeholders are used for loading images and failed image loads to maintain the grid's layout integrity.
