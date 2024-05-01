## MOBILE APPLICATION DEVELOPMENT Assignment.

__Name:__ Farhaan Kaleem

__Video demonstration:__ https://youtu.be/Zaknp9iJAYg

This repository contains an implementation of a Health tracker mobile application. This application can be used by the users to keep a track of their healthy lifestyle.

### Introduction.

In this project, a web application is made, which is used to track the health of the users. It has different ways, in which it encourages user to achieve their goal. In the home page, the user is shown a circular progress bar which shows how much more remaining to achieve the goal. The goals are taken from the user at the time of registration. It has different features like workouts, step tracker, water tracker.

#### Splash Screen

The splash screen is shown once the app is launched. Here I have tried to introduce a bit of motion to encourage the users that they will have to push themselves to achieve the goal. In order to show the animations, the package called “android.view.animation.AnimationUtils” was used.


<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Splash%20Screen.png?raw=true" alt="Splash Screen" width="400" style="max-width:100%;">
</p>


#### Registration Page

In this phase, the user goals are asked like how many glasses of water they want to drink in a day, how many steps the user wants to walk in a day and how many calories they wish to lose in a day. The user data is stored in Firebase Realtime Database with all the data obtained from the user. Once the registration is done, the user is redirected to Login page. In Registration page, material UI is used for the fields so only numbers can be entered for some specific fields like Glasses of Water.

The validation is done for length of the password to be not less than 6 characters.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Registration.png?raw=true" alt="Registration Page" width="400" style="max-width:100%;">
</p>


#### Login Page

The login page uses Firebase to authenticate the users into the application. All the fields are made using the Material UI. In both the login page, and the registration page, a small progress bar is attached to show the user that the registration or signing in process is taken place. Password field also has a small eye icon which comes default with input type “textPassword”.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Login.png?raw=true" alt="Login Page" width="400" style="max-width:100%;">
</p>


#### Home Page

Once the user logs in, he lands into the homepage, which gives all the information to the user about his activities. It shows the health report of the user and shows the remaining calories or steps or glasses the user has to take in order to achieve the goal. It also shows the user their BMI because it is observed that most people keep a close look on their BMI, as that decides the fitness of the user. Changes in any number is immediately reflected here.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Homepage.png?raw=true" alt="Home Page" width="400" style="max-width:100%;">
</p>


#### Toolbar

Toolbar has different icons which are used for achieving different objectives. The first icon, the hamburger icon is used to open and close the app drawer. The toggle button is off by default, which shows the day mode. When enabled, it turns on the night mode. ‘themes.xml’ file is used to achieve day / night mode functionality. The last icon is the log out button, which logs the user out of the app and the user is redirected to the login page. Also the toolbar has the heading which changes as the page changes.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Toolbar.png?raw=true" alt="Toolbar" width="400">
</p>


#### App Drawer

The app drawer is opened, once the user clicks on hamburger icon. It has different functionalities like BMI calculator, Step Tracker, Water Tracker, Workouts. Clicking the option loads that specific layout and corresponding bindings are done. In the day and night mode, the icons of the drawer also changes their colour. App drawer also has the email ID and name of the user to show the logged in user.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/AppDrawerDay.png?raw=true" alt="App Drawer Day Mode" width="400">
</p>



<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/AppDrawerNight.png?raw=true" alt="App Drawer Night Mode" width="400">
</p>


#### BMI Calculator

This page or feature, which takes the inputs from the user and puts the corresponding output in the Database and the text view there.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/BMI.png?raw=true" alt="BMI Calculator" width="400">
</p>


#### Step Tracker

This feature enables the user to measure the steps they have taken in the whole day. This feature uses the sensor to take in the data. The library used for getting the data from the sensor is “android.hardware.Sensor”.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Steps.png?raw=true" alt="Step Tracker" width="400">
</p>



#### Water Tracker

This feature enables the user to just click a button when they drink a glass of water. It works as a counter to increase the number every time the user drinks water. Once the goal is achieved it shows a toast indicating that the goal is achieved.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Water.png?raw=true" alt="Water Tracker" width="400">
</p>


#### Workouts

This feature shows the user a list of workouts they can do. One the user clicks on the workout, it takes the user in the workout explanation. In the explanation page and also on the list page there are gif images which are used to show the workouts. To show the GIF images a library called “pl.droidsonroids.gif.GifImageView” is used.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Workouts.png?raw=true" alt="Workouts" width="400">
</p>


Workout Explanation page also has timer of one minute which can be started, paused or finished. Once the timer is finished the corresponding calories burnt is calculated and saved in the Firebase Realtime Database.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/WorkoutExp.png?raw=true" alt="Workout Explanation" width="400">
</p>


For timer, the library used is “android.os.CountDownTimer”. Also please note that as the day is changed, all the counters will reset and the user will need to achieve all the goals again. Hence this application encourages the user to achieve their goals daily leading them to achieve their goals.

### UML and Class Diagram

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/Relationships.png?raw=true" alt="Relationships" width="600">
</p>


The above diagram shows the data flow between the different classes.

<p align="center">
  <img src="https://github.com/farhaankaleem/HealthFitApplication/blob/develop/ClassDiagram.png?raw=true" alt="Class Diagram" width="800">
</p>

The above diagram shows the class diagram and the data flow amongst the classes. From the diagram, it is seen that the viewModel is used to interact with the Database, and that gives data to the fragment.


### UX / DX Approach Adopted

The app drawer is made for the easy navigation of the user from the menu. One of the feature in the app is day mode and night mode that can be used by the user according to their comfort. A proper day theme and night theme is maintained across the application for smooth view and interaction with the application. The logout button is present in the tool bar, along with the toggle button to enable and disable the night mode. Across the app, the Jetpack compose and the Material UI is used to give the better understanding and look of the application to the user.

Binding is enabled so by default all the UI elements are bonded with the model or logic behind it. Firebase Auth and the Firebase Realtime Database is used, which is responsible for a smooth login and security of the application. Using the Firebase Realtime Database, the data of each user is stored in the backend and retrieved as needed. An attempt is made to separate the logic from the data layer and hence View model, which is responsible for taking the data from the DB and give it to the Fragment is used.
