package com.example.proj1_tablayout.model

import android.content.Context
import androidx.room.*
import getJsonDataFromAsset
import json2contacts

@Entity
data class Contact(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "group") val group: String?,
    @ColumnInfo(name = "image") val image: String?
)

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE id IN (:contactIds)")
    fun loadAllByIds(contactIds: IntArray): List<Contact>

    @Query("SELECT * FROM contact WHERE name LIKE :name LIMIT 1 ")
    fun findByName(name: String): Contact

    @Insert
    fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<Contact>)

    @Delete
    fun delete(contact: Contact)
}

@Database(entities = arrayOf(Contact::class), version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {

        private var instance: ContactDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    ContactDatabase::class.java, "contact-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                init_database(context)
            }
            return instance
        }

        fun init_database(context: Context){
            val contacts = json2contacts("database.json", context)
            //instance!!.contactDao().insert(contacts[0])
            instance?.contactDao()?.insertAll(contacts)
        }
    }
}