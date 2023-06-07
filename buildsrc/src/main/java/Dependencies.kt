import Versions.coroutines
import Versions.navigation
import org.gradle.api.artifacts.dsl.DependencyHandler
import Versions.constraintLayout as constraint
import Versions.okHttpInterceptor as interceptor

//android ui
private val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
private val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraint"
private val material = "com.google.android.material:material:${Versions.material}"

//compose
private val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
private val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
private val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
private val debugComposeTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"

//navigation
private val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigation"
private val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigation"

//lifecycle
private val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
private val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
private val service = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"

//jetpack
private val annotation = "androidx.annotation:annotation:${Versions.annotation}"
private val biometric = "androidx.biometric:biometric:${Versions.biometric}"
private val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
private val preference = "androidx.preference:preference-ktx:${Versions.preference}"
private val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
private val worker = "androidx.work:work-runtime-ktx:${Versions.worker}"
private val camera = "androidx.camera:camera-camera2:${Versions.camera}"
private val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraLifecycle}"
private val cameraView = "androidx.camera:camera-view:${Versions.cameraView}"
private val multidex = "androidx.multidex:multidex:${Versions.multidex}"

//google
private val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
private val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
private val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
private val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
private val googleMap = "com.google.maps.android:maps-utils-ktx:${Versions.googleMap}"
private val playCore = "com.google.android.play:core:${Versions.playCore}"
private val playCoreKtx = "com.google.android.play:core-ktx:${Versions.playCoreKtx}"
private val servicesAuth = "com.google.android.gms:play-services-auth:${Versions.servicesAuth}"
private val servicesAuthPhone =
    "com.google.android.gms:play-services-auth-api-phone:${Versions.servicesAuthPhone}"

//coroutines
private val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
private val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"

//network
private val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
private val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stethoOkHttp}"
private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
private val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
private val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
private val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:$interceptor"

//hilt
private val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
private val hiltDaggerCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
private val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltJetpack}"
private val hiltFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltJetpack}"
private val hiltJetpackCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetpack}"

//3-rd party libs
private val timber = "com.jakewharton.timber:timber:${Versions.timber}"
private val eventBus = "org.greenrobot:eventbus:${Versions.eventBus}"
private val otpView = "com.github.mukeshsolanki:android-otpview-pinview:${Versions.otpView}"
private val coil = "io.coil-kt:coil:${Versions.coil}"
private val codeScanner = "com.budiyev.android:code-scanner:${Versions.codeScanner}"
private val dexter = "com.karumi:dexter:${Versions.dexter}"
private val shortcutBadger = "me.leolin:ShortcutBadger:${Versions.shortcutBadger}"
private val swipeLayout = "com.daimajia.swipelayout:library:${Versions.swipeLayout}"
private val yandexMap = "com.yandex.android:maps.mobile:${Versions.yandexMap}"

//test libs
private val junit = "junit:junit:${Versions.junit}"
private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
private val mockito = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito}"
private val coroutinesTest =
    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"

object Dependencies {

    val coreLibraries = listOf(
        coreKtx,
    )

    val firebaseApi = listOf(
        firebaseBom,
    )

    val appLibraries = listOf(
        //ui
        coreKtx,
        appcompat,
        constraintLayout,
        material,

        //navigation
        navigationFragment,
        navigationUi,

        //jetpack
        liveData,
        viewModel,
        service,
        biometric,
        dexter,
        worker,
        camera,
        cameraLifecycle,
        cameraView,
        multidex,
        annotation,
        legacy,
        fragment,
        preference,

        //coroutines
        coroutinesCore,
        coroutinesAndroid,

        //network
        stetho,
        stethoOkHttp,
        retrofit,
        gson,
        okHttp,
        okHttpInterceptor,

        //3-rd party
        timber,
        eventBus,
        otpView,
        coil,
        codeScanner,
        swipeLayout,
        shortcutBadger,

        //google
        playCore,
        playCoreKtx,
        servicesAuth,
        servicesAuthPhone,
        googleMap,
        yandexMap,
    )

    val compose = listOf(
        composeUi,
        composeMaterial,
        composeToolingPreview
    )

    val composeDebugTooling = listOf(
        debugComposeTooling
    )

    val hiltLibraries = listOf(
        hilt,
        hiltWork,
        hiltFragment,
    )

    val hiltCompilers = listOf(
        hiltDaggerCompiler,
        hiltJetpackCompiler,
    )

    val firebaseLibraries = listOf(
        firebaseMessaging,
        firebaseAnalytics,
        firebaseCrashlytics,
    )

    val androidTestLibraries = listOf(
        extJUnit,
        espressoCore,
    )

    val testLibraries = listOf(
        junit,
        mockito,
        coroutinesTest,
    )
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

//fun DependencyHandlerScope.platformImplementation(list: List<String>) {
//    list.forEach { dependency ->
//        add("implementation", platform(dependency))
//    }
//}
//
//fun DependencyHandlerScope.implementation(list: List<String>) {
//    list.forEach { dependency ->
//        add("implementation", dependency)
//    }
//}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}