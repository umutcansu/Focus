package com.thell.focus.viewmodel

import androidx.lifecycle.ViewModel
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.repository.repo.SettingsRepository

class SettingsViewModel:ViewModel()
{
    private var settingsRepository = SettingsRepository()

    fun insert(settings: SettingsEntity) = settingsRepository.insert(settings)

    fun update(settings: SettingsEntity) = settingsRepository.update(settings)

    fun getAllNote() = settingsRepository.getAll()
}