package com.innaval.evertonereplica.add

import com.innaval.evertonereplica.data.model.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable

class AddPresenter(
    private val view:Add.View,
    private val dataSource: RemoteDataSource
): Add.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun createNote(title: String, body: String) {

    }

    override fun getNote(id: Int) {
    }

    override fun stop() {
    }
}