package com.thell.focus.viewmodel

import androidx.lifecycle.ViewModel
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.repository.repo.SettingsRepository

class SettingsViewModel:ViewModel()
{
    private var settingsRepository = SettingsRepository.getInstance()

    fun insert(settings: SettingsEntity) = settingsRepository.insert(settings)

    fun update(settings: SettingsEntity) = settingsRepository.update(settings)

    fun updateByKey(settings: SettingsEntity) = settingsRepository.updateByKey(settings)

    fun getAllNote() = settingsRepository.getAll()
}