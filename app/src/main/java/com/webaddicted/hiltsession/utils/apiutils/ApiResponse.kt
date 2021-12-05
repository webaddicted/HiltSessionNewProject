package com.webaddicted.hiltsession.utils.apiutils

import java.net.SocketTimeoutException

/**
* Author : Deepak Sharma(Webaddicted)
* Email : techtamper@gmail.com
* Profile : https://github.com/webaddicted
*/
class ApiResponse<T>(val status: Status, val data: T?, error: Throwable?) {

      var errorMessage: String? = null
      private var errorType: Error? = null

      init {
            if (error != null && (status == Status.ERROR|| status== Status.NO_INTERNET_CONNECTION)) {
                  errorType = Error.API_ERROR
                  errorMessage = error.message
                } else if (error is SocketTimeoutException) {
                  errorType = Error.TIMEOUT_ERROR
                  errorMessage = "network_error"
                } else {
                  errorType = Error.SERVER_ERROR
                  errorMessage = "internal_server_error"
                }
          }

      companion object {
            fun <T> internetError(): ApiResponse<T> {
                  return ApiResponse(
                    Status.NO_INTERNET_CONNECTION,
                    null,
                    Exception("Seems You do not have active internet connection. Please connect to Internet and try again")
                  )
                }

            fun <T> loading(): ApiResponse<T> {
                  return ApiResponse(
                    Status.LOADING,
                    null,
                    Exception("")
                  )
                }

            fun <T> success(data: T): ApiResponse<T> {
                  return ApiResponse(
                    Status.SUCCESS,
                    data,
                    Exception("")
                  )
                }

            fun <T> error(error: Throwable?): ApiResponse<T> {
                  return ApiResponse(
                    Status.ERROR,
                    null,
                    error
                  )
                }
          }

      enum class Status {
            NO_INTERNET_CONNECTION,
            LOADING,
            SUCCESS,
            ERROR,
          }

      enum class Error {
            SERVER_ERROR,
            TIMEOUT_ERROR,
            API_ERROR
          }
}

