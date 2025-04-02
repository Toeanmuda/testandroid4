package com.example.androidtest2.di

import android.app.Application
import com.example.androidtest2.data.api.RetrofitInstance
import com.example.androidtest2.data.dao.CartDao
import com.example.androidtest2.data.database.ProductDatabase
import com.example.androidtest2.data.repository.CartRepository
import com.example.androidtest2.data.repository.LoginRepository
import com.example.androidtest2.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {

        @Provides
        @Singleton
        fun provideProductDatabase(app: Application): ProductDatabase {
            return ProductDatabase.getDatabase(app)
        }

        @Provides
        @Singleton
        fun provideProductDao(productDatabase: ProductDatabase): CartDao {
            return productDatabase.cartDao()
        }
    }

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepository()
    }

    @Provides
    fun provideProductRepository(app: Application): ProductRepository {
        return ProductRepository(RetrofitInstance.apiService, DatabaseModule.provideProductDao(DatabaseModule.provideProductDatabase(app)))
    }

}
