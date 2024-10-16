package edu.ucne.dealerapp.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.dealerapp.data.local.database.DealerDb
import edu.ucne.dealerapp.data.remote.DealerApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val BASE_URL = "https://dealerwebapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providesDealerApi(moshi: Moshi): DealerApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DealerApi::class.java)
    }
    @Provides
    @Singleton
    fun providesDealerDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            DealerDb::class.java,
            "DealerDb"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesVehiculoDao(db: DealerDb) = db.vehiculoDao
    @Provides
    fun providesAccesorioDao(db: DealerDb) = db.accesoriosDao

}