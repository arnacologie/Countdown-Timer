package com.petite_semence.countdowntimer.di

import android.app.Application
import android.content.Context
import com.petite_semence.countdowntimer.feature_event.data.repository.EventRepositoryImpl
import com.petite_semence.countdowntimer.feature_event.domain.model.MyObjectBox
import com.petite_semence.countdowntimer.feature_event.domain.repository.EventRepository
import com.petite_semence.countdowntimer.feature_event.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ) = context


    @Provides
    @Singleton
    fun provideMainDispatcher() = Dispatchers.Main as CoroutineDispatcher

    @Provides
    @Singleton
    fun provideEventDatabase(app: Application): BoxStore {
        return MyObjectBox.builder()
            .androidContext(app)
            .build()
    }

    @Provides
    @Singleton
    fun provideEventRepository(boxStore: BoxStore) : EventRepository{
        return EventRepositoryImpl(boxStore)
    }

    @Provides
    @Singleton
    fun provideEventUseCases(repository: EventRepository): EventUseCases{
        return EventUseCases(
            getEvents = GetEvents(repository),
            getEvent = GetEvent(repository),
            addEvent = AddEvent(repository),
            deleteEvent = DeleteEvent(repository)
        )
    }


}