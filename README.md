# MotionGuard
 Student Grade Calculator

An Android application built in Kotlin that reads student marks from an Excel file, calculates averages, assigns grades, displays a grade distribution analysis, and exports the results back to a new Excel file.

---



Features

-  Upload Excel Sheet — Import a `.xlsx` file containing student IDs, names, and subject marks
-  Auto Calculation — Automatically sums all subject marks and computes the average per student
-  Grade Assignment — Assigns a grade to each student based on their average score
-  Grade Distribution — Shows how many students achieved each grade for analysis
-  Empty Sheet Detection — Displays `"This sheet has no entries"` if the uploaded file has no data
-  Export Results — Downloads a new Excel file containing student grades and a distribution summary sheet


  Grade Scale

| Grade | Score Range |
|-------|-------------|
| A     | 80 – 100    |
| B+    | 70 – 79     |
| B     | 60 – 69     |
| C+    | 55 – 59     |
| C     | 50 – 54     |
| D+    | 45 – 49     |
| D     | 40 – 44     |
| F     | 0 – 39      |

---

  Excel Input Format

The input Excel file must follow this structure:

| Student ID | Student Name | Subject 1 | Subject 2 | Subject 3 | Subject 4 |
|------------|-------------|-----------|-----------|-----------|-----------|
| STU001     | Alice Nguema | 85        | 72        | 90        | 68        |
| STU002     | Bob Mbeki    | 55        | 48        | 62        | 71        |

- The first row must contain the headers exactly as shown
- You can add as many subject columns as needed
- Download the provided template to get started quickly

---

 Tech Stack

- Language: Kotlin
- Platform: Android (min SDK 26 / Android 8.0+)
- Excel Processing: Apache POI 5.2.5
- UI Components: Material Design, RecyclerView, CardView
- Build System: Gradle (Kotlin DSL)

---

  Getting Started

# Prerequisites

- Android Studio (latest version recommended)
- Android device or emulator running Android 8.0 (API 26) or higher
- Java 17+

# Installation

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/GradeCalculator.git
   ```

2. Open in Android Studio
   - Launch Android Studio
   - Click `File → Open`
   - Select the cloned `GradeCalculator` folder

3. Sync Gradle
   - Wait for Android Studio to sync dependencies automatically
   - This may take a few minutes on first run

4. Run the app
   - Connect your Android device via USB or launch an emulator
   - Click the ▶ Run button

---

  How to Use

1. Prepare your Excel file using the provided template format
2. Open the app on your Android device
3. Tap "Upload Excel File" and select your `.xlsx` file
4. The app will instantly display:
   - A list of all students with their total marks, average and grade
   - A grade distribution summary
5. Tap "Download Results Excel" to export and save the graded results

---

  Project Structure

```
GradeCalculatorr/
├── app/
│   └── src/main/
│       ├── java/com/example/gradecalculatorr/
│       │   ├── MainActivity.kt          # Main screen logic
│       │   ├── StudentResult.kt         # Data model
│       │   └── StudentResultAdapter.kt  # RecyclerView adapter
│       ├── res/
│       │   ├── layout/
│       │   │   ├── activity_main.xml    # Main screen layout
│       │   │   └── item_student_result.xml  # Student card layout
│       │   ├── values/
│       │   │   ├── colors.xml
│       │   │   ├── strings.xml
│       │   │   └── themes.xml
│       │   ├── drawable/
│       │   │   └── grade_badge_bg.xml
│       │   └── xml/
│       │       └── file_paths.xml       # FileProvider config
│       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

 Build Configuration

```kotlin
minSdk = 26        // Android 8.0+
targetSdk = 36
compileSdk = 36
kotlin jvmTarget = "17"
```

 Key Dependencies

```kotlin
// Apache POI for Excel reading/writing
implementation("org.apache.poi:poi:5.2.5")
implementation("org.apache.poi:poi-ooxml:5.2.5")

// Material Design UI
implementation("com.google.android.material:material:1.12.0")

// RecyclerView
implementation("androidx.recyclerview:recyclerview:1.3.2")
```

---

