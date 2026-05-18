package com.example.buscaminas.data.data

import com.example.buscaminas.CfgPartida
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {

    companion object {

        val ALIAS = stringPreferencesKey("alias")
        val MEDIDA = intPreferencesKey("medida")
        val PORCENTAJE_MINAS = intPreferencesKey("porcentaje_minas")
        val TIEMPO_ACTIVO = booleanPreferencesKey("tiempo_activo")
    }

    suspend fun guardarPreferencias(alias: String, medida: Int, porcentajeMinas: Int, tiempoActivo: Boolean){

        context.dataStore.edit { prefs ->

            prefs[ALIAS] = alias

            prefs[MEDIDA] = medida

            prefs[PORCENTAJE_MINAS] = porcentajeMinas

            prefs[TIEMPO_ACTIVO] = tiempoActivo
        }
    }

    val preferenciasFlow = context.dataStore.data.map { prefs ->

        CfgPartida(
            alias = prefs[ALIAS] ?: "",
            filas = prefs[MEDIDA] ?: 5,
            columnas = prefs[MEDIDA] ?: 5,
            porcentajeMinas = prefs[PORCENTAJE_MINAS] ?: 25,
            tiempoActivo = prefs[TIEMPO_ACTIVO] ?: false
        )
    }
}