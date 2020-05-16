package com.thell.focus.viewmodel

import androidx.lifecycle.ViewModel
import com.thell.focus.repository.repo.NotificationRepository
import com.thell.focus.repository.repo.SettingsRepository

class NotificationHistoryViewModel:ViewModel()
{
    private var notificationRepository = NotificationRepository.getInstance()

    fun getAllNotificationHistory() = notificationRepository.getAll()

    fun deleteAllHistoryNotification() = notificationRepository.deleteAll()
}