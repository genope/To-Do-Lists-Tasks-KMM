package todolist.kmm.data_cache.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import android.content.Context

actual class DatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "todoapp.db")    }
}