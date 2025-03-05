package com.example.taskmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanagerapp.data.local.TaskDao
import com.example.taskmanagerapp.data.local.TaskDatabase
import com.example.taskmanagerapp.domain.repository.TaskRepository
import com.example.taskmanagerapp.domain.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Application Context
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    // Provide Database Instance
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    // Provide DAO Instance
    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()

    //Provide Repository Instance
    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }
}
