package com.gscarlos.moviescleanarchitecture.data.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulk(t: List<T>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplace(t: T): Long

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)

    @Delete
    fun delete(t: List<T>)

}