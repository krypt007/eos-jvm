package com.memtrip.eos.http.rpc.wallet

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.utils.Config
import com.memtrip.eos.http.rpc.utils.generateUniqueWalletName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class WalletLockAllTest : Spek({

    given("an Api.Wallet") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val walletApi by memoized { Api(Config.WALLET_API_BASE_URL, okHttpClient).wallet }

        on("v1/wallet/lock_all") {

            walletApi.create(generateUniqueWalletName()).blockingGet()

            val isSuccessful = walletApi.lockAll().blockingGet().isSuccessful

            val wallets = walletApi.listWallets().blockingGet()

            it("should lock the wallet") {
                assertTrue(isSuccessful)

                for (wallet in wallets.body()!!) {
                    assertFalse(wallet.contains("*"))
                }
            }
        }
    }
})