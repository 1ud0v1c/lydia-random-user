package com.ludovic.vimont.lydiarandomuser.helper

class StateData<T>(val status: DataStatus,
                   val data: T?,
                   val errorMessage: String?) {
    companion object {
        fun <T> success(data: T?): StateData<T> {
            return StateData(DataStatus.SUCCESS, data, null)
        }

        fun <T> error(errorMessage: String, data: T?): StateData<T> {
            return StateData(DataStatus.ERROR, data, errorMessage)
        }
    }
}