package com.thell.focus.helper.navigation

import java.io.Serializable

interface IFragmentCallback :Serializable{
    fun changeHeader(header:String)
}