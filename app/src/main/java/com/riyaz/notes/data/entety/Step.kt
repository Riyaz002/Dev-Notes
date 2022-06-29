package com.riyaz.notes.data.entety

import android.accounts.AuthenticatorDescription
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import java.io.Serializable


@Entity
data class Step(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name="step_title") val title: String?,
    @ColumnInfo(name="sub_steps") val explanation: String?
)