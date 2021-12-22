package com.example.moviecompose.ui.person.personDetail

import androidx.lifecycle.ViewModel
import com.example.moviecompose.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {



}