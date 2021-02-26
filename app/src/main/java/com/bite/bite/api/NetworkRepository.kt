package com.bite.bite.api

import com.bite.bite.application.base.BaseRepository
import com.bite.bite.models.Restaurant
import com.bite.bite.models.Version
import com.bite.bite.utils.Logger
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class NetworkRepository: BaseRepository(){

    companion object{
        private const val TAG = "ApiService"
    }

    private val apiService by lazy { ApiService.create() }

    suspend fun getRestaurants(onError: () -> (Unit)): MutableList<Restaurant>?{
//        val response = safeApiCall(
//            call = {apiService.getRestaurants().await()},
//            onError = onError
//        )
//        return response?.toMutableList()
        return mutableListOf(
            Restaurant(
                1,
                "Endorphine",
                "https://lh3.googleusercontent.com/proxy/3uBy_TMZ2BFS2dRax_hQG65X-n3AkPV0_xQJerrjhzKUZHKTjLgxjgBh7J6wW7U5faoWkJKoZQ0nlw2l1IpwbEd-s0gzsCuTpg",
                "Наразі найкращий кальян, який я курив у Києві. Пробували на печерці,  подолі і інших дорогих закладах.На фоні цього бару,  який ще й недалеко від дому,  усі ті пафосні заклади просто \"мєркнуть\".",
                5,
                "50.469353",
                "30.468611",
                "Юрія Іллєнка 32"
            ),
            Restaurant(
                2,
                "Narikela NEO",
                "https://lh3.googleusercontent.com/proxy/C3U8lCh_R9wrMjZPvxcQT0ex19qZVksnsjgMs7oRD0HVdS8kcXT0gkJxE4F8QBOWLi3rxbf0g3IQkNI9ENnhSBA5pD4EPylU9z1BMUe_qNuGL5d7li_pV_3azugQZGUjfRI",
                "Чудова місцина, прекрасні дівчата, дуже привітні і ввічливі кальянщики які в змозі задовольнити потреби і смаки найпримхливішого любителя кальянокуріння,одним словом TOP чик!!!",
                5,
                "50.450123",
                "30.496317",
                "вулиця Гоголівська, 11/39"
            ),
            Restaurant(
                3,
                "ArtHouse",
                "https://lh3.googleusercontent.com/proxy/Zh4RN9Ov7HRmBL6vYz_awKimW6MOMpwrtyJWgC5ebYXEt_YNMlHwESmYlr_RCT3pjUYTD552aTaCvbhDWr0q3flLFuFS-n_InMEydGb0ynE9bcCScrcBIw0mtUTZDEay5PfpaQzP",
                "Приємна атмосфера, смачна кухня и привітний персонал. Рекомендую спробувати фондан, дуже смачний \uD83D\uDC4D",
                5,
                "50.442765",
                "30.519304",
                "бульвар Тараса Шевченка, 2/54"
            ),
            Restaurant(
                4,
                "DUDKA BAR",
                "https://mir-s3-cdn-cf.behance.net/projects/404/70f7ad99162443.Y3JvcCwxMDg3LDg1MCwyMTcsNDA.jpg",
                "Чудове місце, приємні бармен та кальянщик, спокійна атмосфера \uD83D\uDC4D❤️",
                4,
                "50.589340",
                "30.490884",
                "вулиця Шолуденка, 6г"
            )
        )
    }
}