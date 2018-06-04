package mobile.frba.utn.tpmobile.models

import android.arch.lifecycle.LiveData
import mobile.frba.utn.tpmobile.daos.WordRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel


class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: WordRepository

    private val allWords: LiveData<List<Word>>

    init {
        mRepository = WordRepository(application)
        allWords = mRepository.getAllWords()
    }

    fun getAllWords(): LiveData<List<Word>> {
        return allWords
    }
    fun insert(word: Word) {
        mRepository.insert(word)
    }
}
