package todolist.kmm.data_cache.sqldelight

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}