package com.example.study.findLargeNumber

class FindLargeNumber {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/154539?language=kotlin
     * 정수로 이루어진 배열 numbers가 존재
     * 배열의 각 원소는 자신보다 뒤에 있는 숫자 중에 가까운 큰 수를 찾아 새로운 배열에 담아 반환
     * 단, 자신보다 큰 수가 없는 경우 -1을 배열에 담음
     *
     * numbers : 정수 배열
     * return : 가까운 큰 수를 담은 배열
     *
     * 제한사항
     * 4 ≤ numbers의 길이 ≤ 1,000,000
     * 1 ≤ numbers[i] ≤ 1,000,000
     */

    fun solution(numbers: IntArray): IntArray {
        val answer: IntArray
        val resultList = mutableListOf<Int>()
        val indexList = mutableListOf<Int>()

        // numbers 만큼 반복
        numbers.forEachIndexed { index, number ->
            // number가 가까운 큰 수를 발견하지 못한 값보다 큰 경우
            while (indexList.isNotEmpty() && number > numbers[indexList.last()]) {
                // resultList에 -1로 설정된 값을 가까운 큰 수인 number로 변경
                resultList[indexList.removeLast()] = number
            }

            // 가까운 큰 수를 발견하지 못한 경우 -1로 설정하고 index를 indexList에 추가(추후 발견 시 값을 변경하기 위함)
            resultList.add(-1)
            indexList.add(index)
        }

        answer = resultList.toIntArray()
        return answer
    }

    // 시간 초과 11 부터
    fun solution4(numbers: IntArray): IntArray {
        var answer: IntArray = intArrayOf()

        numbers.forEachIndexed { index, number ->
            val largeNumber = numbers.sliceArray(index + 1 until numbers.size).asSequence().filter { it > number }.firstOrNull() ?: -1
            answer += largeNumber
        }

        return answer
    }

    // 시간 초과 14부터
    fun solution3(numbers: IntArray): IntArray {
        var answer: IntArray
        val numberList = numbers.toMutableList()
        val resultList = mutableListOf<Int>()

        while (numberList.isNotEmpty()) {
            val number = numberList.removeFirst()
            val largeNumber = numberList.asSequence().filter { it > number }.firstOrNull() ?: -1

            resultList.add(largeNumber)
        }

        answer = resultList.toIntArray()
        return answer
    }

    // 시간 초과 6부터
    fun solution2(numbers: IntArray): IntArray {
        var answer: IntArray

        val resultList = numbers.mapIndexed { index, number ->
            numbers.firstOrNull {
                numbers.indexOf(it) > index && it > number
            } ?: -1
        }

        answer = resultList.toIntArray()
        return answer
    }
}

fun main() {
    val findLargeNumber = FindLargeNumber()

    val result = findLargeNumber.solution(intArrayOf(9, 1, 5, 3, 6, 2))

    println("result: ${result.toList()}")
}