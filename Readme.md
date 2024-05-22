# Swipe Android Assignment App

This Android app is designed for listing products and adding new products, developed as part of the Swipe Android Assignment. It enables users to view a product list and add new products. The app utilizes modern Android technologies and best practices, such as MVVM architecture, Retrofit for API communication, KOIN for dependency injection, and Lifecycle for Kotlin coroutines.


## Getting Started

To set up and run the app on your local machine, follow these steps:

1. Clone the repository using Git:

```bash
git clone https://github.com/saras1122/AndroidAssignment
```

2. Open Android Studio and choose "Open an existing Android Studio project."

3. Navigate to the cloned project directory and open the swipe-android-assignment folder.

4. Allow Gradle to sync and build the project.

5. Connect an Android device or start an emulator with API level 27 or higher.

6. Use the Run button in Android Studio to launch the app on the connected device or emulator.



## How to Use the App

1. When the app launches, it displays the product listing screen. Ensure your device has an active internet connection.

2. Utilize the search bar at the top to filter products by name, type, price, or tax.

3. Scroll through the list to view all available products.

4. To add a new product, click on the "Add Product" button in the bottom navigation.

5. On the Add Product screen, input the product name, select the product type from the dropdown menu, and enter the selling price and tax rate.

6. Optionally, click the "Attachment Icon" button to upload an image for the new product.

7. Click the "Add Product" button to add the product. A success message will appear if the product is added successfully.



## Fetching Product Data API Endpoint

The app communicates with the following API endpoint to fetch the list of products:

- Endpoint: https://app.getswipe.in/api/public/get
- Method: GET

The app uses Retrofit to make the GET request and Gson to parse the JSON response into data objects.


## Adding Product Data API Endpoint

The app communicates with the following API endpoint for adding new products:

- Endpoint: https://app.getswipe.in/api/public/add
- Method: POST

## Libraries and Technologies Used

- Retrofit: For making API calls.
- KOIN: For dependency injection.
- Android Lifecycle: For managing coroutines and asynchronous tasks.
- Glide: For image loading and caching.
- ViewModel and LiveData: For implementing MVVM architecture.


