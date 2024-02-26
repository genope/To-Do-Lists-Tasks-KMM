package todolist.kmm.data_cache.sqldelight

import app.cash.sqldelight.ColumnAdapter
import todolist.kmm.datacache.sqldelight.TaskFavorite
import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status

class SharedDatabase(
    private val driverProvider: DatabaseDriverFactory,
) {
    private var database: AppDatabase? = null

    private val statusAdapter = object : ColumnAdapter<Status, String> {
        override fun decode(databaseValue: String): Status = when (databaseValue) {
            "0" -> Status.PENDING
            "1" -> Status.DONE
            else -> Status.UNKNOWN
        }

        override fun encode(value: Status): String = when (value) {
            Status.PENDING -> "0"
            Status.DONE -> "1"
            Status.UNKNOWN -> "0"
        }

    }

    private val genderAdapter = object : ColumnAdapter<Complexity, String> {
        override fun decode(databaseValue: String): Complexity = when (databaseValue) {
            "1" -> Complexity.EASY
            "2" -> Complexity.MEDIUM
            "3" -> Complexity.HARD
            else -> Complexity.UNKNOWN
        }

        override fun encode(value: Complexity): String = when (value) {
            Complexity.EASY -> "1"
            Complexity.MEDIUM -> "2"
            Complexity.HARD -> "3"
            Complexity.UNKNOWN -> "4"
        }
    }

    private suspend fun initDatabase() {
        if (database == null) {
            database = AppDatabase.invoke(
                driverProvider.createDriver(),
                TaskFavorite.Adapter(statusAdapter, genderAdapter)
            )
        }
    }

    suspend operator fun <R> invoke(block: suspend (AppDatabase) -> R): R {
        initDatabase()
        return block(database!!)
    }
}