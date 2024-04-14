package com.example.foxchat.di

import com.example.foxchat.data.reposiotry.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository():AuthRepository{
        return AuthRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideUserRepository():UserRepository{
        return UserRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideChatRepository():ChatRepository{
        return ChatRepositoryImpl()
    }
    @Provides
    @Singleton
    fun providePersonRepository():PersonRepository{
        return PersonRepositoryImpl()
    }
}