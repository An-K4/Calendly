import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.calendly"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.calendly"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // avoid conflict with ical4j
    packaging {
        resources {
            excludes += "META-INF/groovy/**"
            excludes += "META-INF/groovy-release-info.properties"
            excludes += "META-INF/INDEX.LIST"
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // ViewPager2 (switch view mode day/month/year)
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // MaterialCalendarView (month calendar)
    implementation("com.prolificinteractive:material-calendarview:1.4.3")

    // iCal4j (parse .ics)
    implementation("org.mnode.ical4j:ical4j:3.2.14")
}