package com.weathersnap.data.repository;

import com.weathersnap.data.remote.api.GeocodingApi;
import com.weathersnap.data.remote.api.WeatherApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WeatherRepositoryImpl_Factory implements Factory<WeatherRepositoryImpl> {
  private final Provider<GeocodingApi> geocodingApiProvider;

  private final Provider<WeatherApi> weatherApiProvider;

  public WeatherRepositoryImpl_Factory(Provider<GeocodingApi> geocodingApiProvider,
      Provider<WeatherApi> weatherApiProvider) {
    this.geocodingApiProvider = geocodingApiProvider;
    this.weatherApiProvider = weatherApiProvider;
  }

  @Override
  public WeatherRepositoryImpl get() {
    return newInstance(geocodingApiProvider.get(), weatherApiProvider.get());
  }

  public static WeatherRepositoryImpl_Factory create(Provider<GeocodingApi> geocodingApiProvider,
      Provider<WeatherApi> weatherApiProvider) {
    return new WeatherRepositoryImpl_Factory(geocodingApiProvider, weatherApiProvider);
  }

  public static WeatherRepositoryImpl newInstance(GeocodingApi geocodingApi,
      WeatherApi weatherApi) {
    return new WeatherRepositoryImpl(geocodingApi, weatherApi);
  }
}
