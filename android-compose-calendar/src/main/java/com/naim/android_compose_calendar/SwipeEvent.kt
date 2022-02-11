package com.naim.android_compose_calendar

sealed class SwipeEvent {
    object onRightSwipe : SwipeEvent()
    object onLeftSwipe : SwipeEvent()
    object onTopSwipe : SwipeEvent()
    object onDownSwipe : SwipeEvent()
}