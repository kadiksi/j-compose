package kz.post.jcourier.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import kz.post.jcourier.data.model.auth.TokensModel


interface SharedPreferencesProvider {
    var login: String?
    var password: String?

    /**
     * Return the OAuth2 access token.
     */
    var accessToken: String?

    /**
     * Return the OAuth2 refresh token.
     */
    var refreshToken: String?
    var fcmToken: String?
    var name: String?
    var city: String?
    var company: String?
    var tokenType: String?
    var issued: String?
    var accessExpired: String?
    var refreshExpired: String?
    var defaultMapType: String?
    var isFirstEntry: Boolean

    fun cleanup()
    fun setUserData(user: TokensModel)
    fun saveCredentials(
        login: String? = null,
        password: String? = null
    )

    fun hasSavedCredentials(): Boolean
}

class SharedPreferencesProviderImpl constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : SharedPreferencesProvider {

    override fun cleanup() {
        accessToken = null
        refreshToken = null
        accessExpired = null
        refreshExpired = null
        name = null
        city = null
        issued = null
        tokenType = null
    }

    override fun setUserData(user: TokensModel) {
        accessToken = user.tokens?.auth?.token
        refreshToken = user.tokens?.refresh?.token
        accessExpired = user.tokens?.auth?.expired
        refreshExpired = user.tokens?.refresh?.expired
//        name = user.name
//        city = user.city
//        company = user.company
//        issued = user.issued
//        tokenType = user.tokenType
    }

    override fun saveCredentials(
        login: String?,
        password: String?
    ) {
        if (!login.isNullOrBlank() && !password.isNullOrBlank()) {
            this.login = login
            this.password = password
        }
    }

    override fun hasSavedCredentials(): Boolean =
        !login.isNullOrBlank() && !password.isNullOrBlank() && !refreshToken.isNullOrBlank()

    override var login: String?
        get() = sharedPreferences.getString(LOGIN, null)
        set(value) = sharedPreferences.edit { putString(LOGIN, value) }

    override var password: String?
        get() = sharedPreferences.getString(PASSWORD, null)
        set(value) = sharedPreferences.edit { putString(PASSWORD, value) }

    /**
     * Return the OAuth2 access token.
     */
    override var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, null)
        set(token) = sharedPreferences.edit { putString(ACCESS_TOKEN, token) }

    /**
     * Return the OAuth2 refresh token.
     */
    @get:Synchronized
    @set:Synchronized
    override var refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN, null)
        set(token) = sharedPreferences.edit { putString(REFRESH_TOKEN, token) }

    override var fcmToken: String?
        get() = sharedPreferences.getString(FCM_TOKEN, null)
        set(token) = sharedPreferences.edit { putString(FCM_TOKEN, token) }

    override var name: String?
        get() = sharedPreferences.getString(NAME, null)
        set(name) = sharedPreferences.edit { putString(NAME, name) }

    override var city: String?
        get() = sharedPreferences.getString(CITY, null)
        set(city) = sharedPreferences.edit { putString(CITY, city) }

    override var company: String?
        get() = sharedPreferences.getString(COMPANY, null)
        set(company) = sharedPreferences.edit { putString(COMPANY, company) }


    override var tokenType: String?
        get() = sharedPreferences.getString(TOKEN_TYPE, null)
        set(tokenType) = sharedPreferences.edit { putString(TOKEN_TYPE, tokenType) }

    override var issued: String?
        get() = sharedPreferences.getString(ISSUED, null)
        set(issued) = sharedPreferences.edit { putString(ISSUED, issued) }

    override var accessExpired: String?
        get() = sharedPreferences.getString(ACCESS_EXPIRED, null)
        set(accessExpired) = sharedPreferences.edit { putString(ACCESS_EXPIRED, accessExpired) }

    override var refreshExpired: String?
        get() = sharedPreferences.getString(REFRESH_EXPIRED, null)
        set(refreshExpired) = sharedPreferences.edit { putString(REFRESH_EXPIRED, refreshExpired) }

    override var defaultMapType: String?
        get() = sharedPreferences.getString(DEFAULT_MAP_TYPE, null)
        set(mapType) = sharedPreferences.edit { putString(DEFAULT_MAP_TYPE, mapType) }

    override var isFirstEntry: Boolean
        get() = sharedPreferences.getBoolean(IS_FIRST_ENTRY, true)
        set(isFirstEntry) = sharedPreferences.edit { putBoolean(IS_FIRST_ENTRY, isFirstEntry) }

    companion object {
        private const val LOGIN = "JCOURIER_LOGIN"
        private const val PASSWORD = "JCOURIER_PASSWORD"
        private const val ACCESS_TOKEN = "JCOURIER_ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "JCOURIER_REFRESH_TOKEN"
        private const val NAME = "JCOURIER_NAME"
        private const val CITY = "JCOURIER_CITY"
        private const val COMPANY = "JCOURIER_COMPANY"
        private const val ISSUED = "JCOURIER_ISSUED"
        private const val REFRESH_EXPIRED = "JCOURIER_REFRESH_EXPIRED"
        private const val ACCESS_EXPIRED = "JCOURIER_ACCESS_EXPIRED"
        private const val TOKEN_TYPE = "JCOURIER_TOKEN_TYPE"
        private const val DEFAULT_MAP_TYPE = "JCOURIER_DEFAULT_MAP_TYPE"
        private const val IS_FIRST_ENTRY = "JCOURIER_IS_FIRST_ENTRY"
        private const val FCM_TOKEN = "JCOURIER_FCM_TOKEN"
        private const val ROLES = "JCOURIER_ROLES"
    }
}