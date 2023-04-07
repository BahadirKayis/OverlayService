package com.bahadir.services.ui.activity

import com.bahadir.services.ui.base.Event

sealed class ActivityUIEvent : Event {
  data class   ServiceStatusChanged (val status: Boolean): ActivityUIEvent()
}