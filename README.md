# Android-Compose-Calendar
##This is library for android jetpack composeable or compose calendar
>Screen shot
![alt text](https://github.com/Mithun1990/Android-Compose-Calendar/blob/master/Screenshot_20220208-233329_AndroidComposeCalendar.jpg)

>Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories.
```gradle
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
>Step 2. Add the dependency to your app level gradle file
```gradle
	dependencies {
	        implementation 'com.github.Mithun1990:Android-Compose-Calendar:1.0.0'
		}
```
>Step 3. Initialize the different calendar configuration and view model with the config class
```kotlin
private val calendarConfig: CalendarConfig = CalendarConfig().apply {
        date = Date()
       	currentDateTextColor = Color(0xFF03DAC5)
        disableDateTextColor = Color(0xFFCCCCCC)
        monthListBgColor = Color(0xFF87584E)
        monthListItemTextColor = Color(0xFFFFFFee)
    }
private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            calendarConfig
        )
    }
 ```
>Step 4. Call the android compose calendar using config and view model and date change listener 
```kotlin
 AndroidComposableCalendar(calendarViewModel, calendarConfig,
                onCalendarEvent = {
                    when (it) {
                        is CalendarEvent.dateSelectionSelection -> {
                            println("Selected date ${it.date}")
                        }
                        is CalendarEvent.nextMonthSelection -> {
                            println("Selected date ${it.month}")
                        }
                        is CalendarEvent.onMonthSelection -> {
                            println("Selected Month ${it.month}")
                        }
                        is CalendarEvent.nextYearSelection -> {
                            println("Selected Year ${it.year}")
                        }
                    }
                })
```
>Available calendar configaration
```kotlin
    date
    minDate
    maxDate
    selectedDateTextColor 
    currentDateTextColor
    selectedDateBgColor 
    currentDateBgColor 
    normalDateTextColor 
    disableDateTextColor 
    holidayTextColor
    monthTitleTextColor
    monthTitleTextStyle 
    isMonthChangeEnabled 
    isYearChangedEnabled
 ```
