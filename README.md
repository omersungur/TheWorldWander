# <p align="center"> The World Wander </p>  

## 📸 Screenshots
<p align="center">
  
</p>
![1](https://github.com/omersungur/TheWorldWander/assets/70448538/8bf9d6bc-43d7-41fd-93f8-d95eb7ca52d6)
![2](https://github.com/omersungur/TheWorldWander/assets/70448538/a7b52010-75cb-4db1-861c-19be499b91ec)
![5](https://github.com/omersungur/TheWorldWander/assets/70448538/96bf23e9-580f-4e1c-83fc-49f5d9a113d2)
![3](https://github.com/omersungur/TheWorldWander/assets/70448538/814d5476-3dab-495f-8f71-8cad2bbf3618)
![4](https://github.com/omersungur/TheWorldWander/assets/70448538/d83ba193-510f-4603-a41e-ae27dc565a2e)
![6](https://github.com/omersungur/TheWorldWander/assets/70448538/55ead85d-43fa-4351-8742-a26a9f5dbc88)
![7](https://github.com/omersungur/TheWorldWander/assets/70448538/7dcda734-6fb3-4bb6-b795-8dcd22d4b6d2)

<br>

## :point_down: Structures Used
- MVVM + Clean Architecture
- Mongo DB Realm
- Firebase (Auth + Storage)
- Room
- Compose Navigation
- Retrofit
- Gson
- Dagger Hilt
- Coil
- Message Bar Compose
- One-Tap Compose
- Swipe Refresh
- Data Store
- Material
- Google Maps
- Coroutines

## :pencil2: Dependency

app build.gradle

```
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("io.realm.kotlin")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("com.google.gms.google-services")
}
```

```
    dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.5")

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson
    implementation ("com.google.code.gson:gson:2.10")

    // Room components
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Runtime Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Mongo DB Realm
    implementation("io.realm.kotlin:library-sync:1.12.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Message Bar Compose
    implementation("com.github.stevdza-san:MessageBarCompose:1.0.5")

    // One-Tap Compose
    implementation("com.github.stevdza-san:OneTapCompose:1.0.7")

    // Swipe Refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.27.0")

    // Data Store
    implementation ("androidx.datastore:datastore-preferences:1.1.0-alpha07")

    // Material
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material:material-icons-extended-android:1.5.4")

    // Google Maps
    implementation ("com.google.maps.android:maps-compose:4.3.0")
}
```

project build.gradle

```
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("io.realm.kotlin") version "1.12.0" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id ("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}
```
