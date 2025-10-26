# QuickTasks - Professional Demo

A polished mobile application prototype demonstrating modern Android development with Jetpack Compose.

## Features

### Core Functionality
- **Task Management** - Create, edit, and delete tasks with titles and descriptions
- **Deadline Tracking** - Set due dates with visual indicators for overdue tasks
- **Task Categories** - Organize tasks by School, Work, Personal, or Other
- **Completion Status** - Toggle task completion with visual feedback
- **Local Storage** - Persistent data using Room database architecture

### User Interface
- **Welcome Screen** - Clean onboarding with branded logo
- **Task List** - Scrollable list with completion checkboxes and category indicators
- **Add Task** - Comprehensive form with category selection and date picker
- **Task Details** - Detailed view with edit and delete capabilities
- **Navigation** - Seamless screen transitions using Navigation Compose

### Technical Implementation
- **Architecture** - MVVM pattern with Repository and DAO layers
- **UI Framework** - Jetpack Compose with Material3 design system
- **Database** - Room with SQLite for local data persistence
- **State Management** - Compose State for reactive UI updates
- **Navigation** - Navigation Compose for screen management

## Project Structure

```
app/src/main/java/ca/gbc/comp3074/quicktasks/
├── MainActivity.kt                 # Application entry point
├── model/
│   └── Task.kt                    # Data model with Room annotations
├── data/
│   ├── TaskDao.kt                 # Database access operations
│   └── TaskDatabase.kt            # Room database configuration
└── ui/
    ├── screens/
    │   ├── WelcomeScreen.kt        # Onboarding interface
    │   ├── TaskListScreen.kt       # Main task management
    │   ├── AddTaskScreen.kt        # Task creation form
    │   └── TaskDetailScreen.kt     # Task detail view
    └── theme/
        ├── Color.kt               # Application color scheme
        ├── Theme.kt               # Material3 theme configuration
        └── Type.kt                # Typography definitions
```

## Sample Data

The application includes five pre-configured tasks demonstrating various states:

1. **Complete Lab Assignment** (School) - Due tomorrow, incomplete
2. **Buy groceries** (Personal) - No due date, completed
3. **Team meeting** (Work) - Overdue, incomplete
4. **Study for midterm** (School) - Due next week, incomplete
5. **Gym workout** (Personal) - No due date, incomplete

## Technical Specifications

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)
- **Build Tools**: Gradle 8.7.2
- **Kotlin**: 2.1.0
- **Compose BOM**: 2024.12.01
- **Room**: 2.8.1
- **Navigation**: 2.7.7

## Build Instructions

1. Open project in Android Studio
2. Sync Gradle files
3. Connect device or start emulator
4. Run application

## Architecture Highlights

- **Clean Code** - No comments, professional structure
- **Modern UI** - Material3 design with custom theming
- **Reactive State** - Compose State for real-time updates
- **Type Safety** - Kotlin with proper null handling
- **Performance** - Efficient list rendering with LazyColumn
- **Accessibility** - Proper content descriptions and semantic structure

This prototype demonstrates production-ready Android development practices suitable for academic evaluation and professional demonstration.