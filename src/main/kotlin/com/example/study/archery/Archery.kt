package com.example.study.archery

class Archery {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/92342?language=kotlin
     * 어피치가 화살 n발을 다 쏜 후에 라이언이 화살 n발을 쏘기 시작
     * 과녁 점수는 10점부터 0점까지 존재
     * k(k는 1~10사이의 자연수)점을 어피치가 a발을 맞혔고 라이언이 b발을 맞혔을 경우 더 많은 화살을 k점에 맞힌 선수가 k점을 획득 (k점을 여러개 맞힌 경우에도 k점만 획득)
     * 단, a=b일 경우는 어피치가 k점을 획득
     * 모든 과녁 점수에 대하여 각 선수의 최종 점수를 계산하고 최종 점수가 같을 경우 어피치가 우승
     *
     * n : 화살 개수
     * info : 어피치가 맞힌 점수 개수의 배열
     * return : 라이언이 우승할 수 있는 방법이 없는 경우 -1, 라이언이 우승할 수 있는 경우 라이언이 맞힌 점수 개수의 배열
     *
     * 제한사항
     * 1 ≤ n ≤ 10
     * info 배열의 length = 11
     * 0 ≤ info 배열 내부 값 ≤ n
     */

    fun solution(n: Int, info: IntArray): IntArray {
        var answer: IntArray = intArrayOf()
        return answer
    }
}

fun main() {
    val archery = Archery()

    /* 1 */
    val n = 5
    val info = intArrayOf(2,1,1,1,0,0,0,0,0,0,0) // result [0,2,2,0,1,0,0,0,0,0,0]

    /* 2
    val n = 1
    val info = intArrayOf(1,0,0,0,0,0,0,0,0,0,0) // result [-1]
    */
    /* 3
    val n = 9
    val info = intArrayOf(0,0,1,2,0,1,1,1,1,1,1) // result [1,1,2,0,1,2,2,0,0,0,0]
    */
    /* 4
    val n = 10
    val info = intArrayOf(0,0,0,0,0,0,0,0,3,4,3) // result [1,1,1,1,1,1,1,1,0,0,2]
    */

    val result = archery.solution(n, info)
    println(result.joinToString(" "))
}