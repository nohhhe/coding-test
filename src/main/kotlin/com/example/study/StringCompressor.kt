package com.example.study

class StringCompressor {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/60057?language=kotlin
     * 문자열 압축
     */

    fun solution(s: String): Int {
        // 문자 길이별로 압축한 문자열의 길이를 구한 후, 가장 짧은 길이를 반환
        return (1..s.length).minOf { compress(s, it).length }
    }

    private fun compress(targetString: String, chunkSize: Int): String {
        var compressedString = ""
        var prevString = ""
        var count = 1

        // 동일한 문자열 횟수를 포함하여 압축한 문자열로 변환
        fun appendCompressedString() {
            compressedString += if (count > 1) "$count$prevString" else prevString
        }

        // 문자열을 chunkSize 단위로 잘라 반복 처리
        targetString.chunked(chunkSize).forEach { currentString ->
            when {
                prevString.isEmpty() -> prevString = currentString
                // 이전 문자열과 동일한 경우 count 증가
                prevString == currentString -> count++
                // 이전 문자열과 다른 경우 압축 문자열로 변환
                else -> {
                    appendCompressedString()
                    prevString = currentString
                    count = 1
                }
            }
        }

        // 마지막 문자열 처리
        appendCompressedString()

        return compressedString
    }
}

fun main() {
    val stringCompressor = StringCompressor()

    // 1
    val string1 = "aabbaccc"
    val result1: Int = stringCompressor.solution(string1) // 7
    println("result1: $result1")

    // 2
    val string2 = "ababcdcdababcdcd"
    val result2: Int = stringCompressor.solution(string2) // 9
    println("result2: $result2")

    // 3
    val string3 = "abcabcdede"
    val result3: Int = stringCompressor.solution(string3) // 8
    println("result3: $result3")

    // 4
    val string4 = "abcabcabcabcdededededede"
    val result4: Int = stringCompressor.solution(string4) // 14
    println("result4: $result4")

    // 5
    val string5 = "xababcdcdababcdcd"
    val result5: Int = stringCompressor.solution(string5) // 17
    println("result5: $result5")
}