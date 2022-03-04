package com.webaddicted.hiltsession

import android.util.Log

/**
 * Author : Deepak Sharma(webaddicted)
 * Email : techtamper@gmail.com
 * Profile : https://github.com/webaddicted
 */
class Test {
    fun main() {
//        Input: 3, 3, 8, 8, 9, 3, 1, 1
//        Output: 2 3 2 8 1 9 1 3 2 1
        val str = arrayOf(2, 2, 6, 7, 9, 8, 9);
        var currentItem = str[0]
        var count = 0
        for (item in 0..str.size) {
            if(item== str.size){
                Log.d("TAG", "$count")
            }else if (str[item] != item) {
                Log.d("TAG", "$count")
                currentItem = item
                count = 0
            }
            count++

        }
    }

    companion object {
        fun main() {
//        Input: 3, 3, 8, 8, 9, 3, 1, 1
//        Input:  3, 8, 8, 9, 3, 1, 1

//        Output: 2 3 2 8 1 9 1 3 2 1
            val str = arrayOf(3, 8, 8, 9, 3, 1, 1)
            val currentItem = str[0]//6
            var count = 1//1//2->//1
            for (item in 1..str.size) {
                Log.d("Test ", "$currentItem $count $item ")//2
                if(item== str.size-1){
                    Log.d("TAG", "$count")
                }else if (str[item] != str[item+1]) {
                    Log.d("TAG", "$count")
//                    currentItem = item
                    count = 1
                }
                count++
            }
//            Log.d("TAG", "$count")
        }
    }
}