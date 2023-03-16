package com.innaval.evertonereplica.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innaval.evertonereplica.data.model.Note
import com.innaval.evertonereplica.data.model.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImplementation : NoteRepository {
    private val compositeDisposable = CompositeDisposable()
    private val remoteDataSource = RemoteDataSource()

    override fun getAllNote(): LiveData<List<Note>?> {
        val data = MutableLiveData<List<Note>?>()

        val disposable = remoteDataSource.listNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Note>>() {
                override fun onNext(notes: List<Note>) {
                    data.postValue(notes)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

                override fun onComplete() {
                    println("complete")
                }
            })

        compositeDisposable.add(disposable)
        return data
    }

    override fun getNote(noteId: Int): LiveData<Note?> {
        val data = MutableLiveData<Note?>()

        val disposable = remoteDataSource.getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Note>() {
                override fun onNext(t: Note) {
                    data.postValue(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

                override fun onComplete() {

                }

            })
        compositeDisposable.add(disposable)
        return data
    }

    override fun createNote(note: Note): LiveData<Note> {
        val data = MutableLiveData<Note>()

        val disposable = remoteDataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Note>() {
                override fun onNext(t: Note) {
                   data.postValue(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

                override fun onComplete() {
                    TODO("Not yet implemented")
                }

            })
        compositeDisposable.add(disposable)
        return data
    }
}