import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

object JsonBuilder {
  val moshi = Moshi.Builder()
          .add(KotlinJsonAdapterFactory())
          .build()
}

val x = JsonBuilder.moshi