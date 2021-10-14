<div>
<img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=Android&logoColor=white" />
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=Kotlin&logoColor=white" />
<img src="https://img.shields.io/badge/writer-kym1924-yellow?&style=flat&logo=Android"/>
</div>

# Room
Daily diary using *Room*.
<br><br>
<img width=360 height=760 src="https://user-images.githubusercontent.com/63637706/137353741-33d6f019-b98a-4944-8abd-5ae574d18aad.gif"/>

#### 1. Initialize
```groovy
// build.gradle in Module
implementation "androidx.room:room-ktx:2.3.0"
implementation "androidx.room:room-runtime:2.3.0"
kapt "androidx.room:room-compiler:2.3.0"
```
<br>

#### 2. Create Entity
Create data class that is annotated with *@Entity*.
```kotlin
@Entity(tableName = "diary_database")
data class Diary(
    @PrimaryKey(autoGenerate = true) val idx: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String = getToday()
)
```

`@Entity(tableName = "diary_database")`
- Room uses the *class name* as the *database table name.*
- Table name can be specified using *@Entity(tableName = "TableName")* annotation.

`@ColumnInfo(name = "title") val title: String`
- Entity includes *fields for each column* in the corresponding table in the database.
- Each column is named with a variable name.
- Column name can be specified using *@ColumnInfo(name = "ColumnName")* annotation.

`@PrimaryKey(autoGenerate = true) val idx: Int = 0`
- Entity includes one or more *@PrimaryKey*.
- To automatically assign an ID, set the *autoGenerate* to true.
<br>

#### 3. Create DAO (Data Access Object)
Create interface that is annotated with *@DAO* to read and write data.
```kotlin
@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_database ORDER BY idx DESC")
    fun getAllDiaries(): Flow<List<Diary>>

    @Query("SELECT * FROM diary_database WHERE idx = :idx")
    suspend fun getDetailDiary(idx: Int): Diary
    
    @Insert
    suspend fun writeDiary(diary: Diary)
    
    @Update
    suspend fun updateDiary(diary: Diary)
    
    @Delete
    suspend fun deleteDiary(diary: Diary)
}
```

`@Insert @Update @Delete` 
- These annotations allow access to the database without SQL query.
- Each parameter for an *@Insert* must be RoomDatabase *Entity*.
- With *@Update and @Delete*, RoomDatabase uses the primary key to match entity and parameters.
- With *@Update and @Delete*, can get the number of rows that were updated.

`@Query`
- This annotation allow access to the database, using SQL query.

`java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long periods of time.`
- If you try to access the database from *the main thread*, an exception will be thrown.
<br>

#### 4. Create DataBase
Create abstract class that is annotated with *@Database*.
```kotlin
@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDataBase : RoomDatabase() {
    abstract fun getDiaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDataBase? = null

        fun getDatabase(context: Context): DiaryDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DiaryDataBase::class.java, "diary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

`abstract class DiaryDataBase : RoomDatabase()`
- The class must be *an abstract class* that extends *RoomDatabase.*

`entities = [Diary::class]`
- The class to designate *as an entity* in the database.

`version = 1`
- The version number is incremented each time you make a schema change.

`exportSchame = false`
- *exportSchema* has been set to false here, in order to avoid a build warning.

`abstract fun getDiaryDao(): DiaryDao`
- The database class must define *function returns an instance of the DAO*.

`fun getDatabase(context: Context) { ... }`
- The database class should follow *the singleton design pattern*.
- Each RoomDatabase instance is fairly expensive.
- Avoid opening multiple database instances at the same time.
<br>

##### Reference

- https://developer.android.com/topic/libraries/architecture/room
- https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow#0
- https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0
- https://github.com/android/sunflower
