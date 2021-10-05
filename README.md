# Android Task - Jobs app

[![Build CI](https://github.com/Kotlin-Android-Open-Source/MVI-Coroutines-Flow/actions/workflows/build.yml/badge.svg)](https://github.com/Kotlin-Android-Open-Source/MVI-Coroutines-Flow/actions/workflows/build.yml)

## This is a prototype for a jobs app that’s main focus is to let users browse the jobs.
*   The user is welcomed on the first screen with the message “Welcome to Mega Trust Jobs”. And start button.
*   When clicking on the start button he should navigate to the jobs screen Which display a list of jobs, the list item should display the following info (company name, company logo, job title , Star icon if the user add the job to favorite ).
*   When the user clicks on one of the jobs, he should navigate to the job details screen which displays the full details of the job (company name, company logo, job title, job type, job URL, company URL, job description).
*   When the user refreshes the jobs screen, you should keep the favorite items saved locally. 
*   You should save the remote data to the local using room db.
*   You should return a listener from the local database to get immediate updates ( using flow ).
*   The application should be implemented in Kotlin.
*   You should use the following technologies.
navigation component, MVI, livedata, coroutines, flow, koin for DI, room db, retrofit, Moshi for JSON serialization

*   **[Download latest debug APK here] (https://github.com/alfayedoficial/MVI_Architecture_Design/blob/master/debug/app-debug.apk)**.


| Get Started | Loading View | Recycler View |
| --------------- | ---------------- | ---------------- |
| <img src="Screenshot_1.png" height="480"> | <img src="Screenshot_2.png" height="480"> |  <img src="Screenshot_3.png" height="480"> |

|  Details View  | WishList is Empty | Info Task  |
| ------------ | ------------ | ---------------- |
| <img src="Screenshot_4.png" height="480"> |  <img src="Screenshot_5.png" height="480"> | <img src="Screenshot_6.png" height="480"> |



<!-- Pixel 3 XL API 30 -->
