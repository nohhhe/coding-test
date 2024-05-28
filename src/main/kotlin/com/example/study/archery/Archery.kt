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
     * info 배열의 i번째 값은 10 - i점을 맞힌 개수
     * return : 라이언이 우승할 수 있는 방법이 없는 경우 -1, 라이언이 우승할 수 있는 경우 라이언이 맞힌 점수 개수의 배열
     * 라이언이 우승할 수 있는 방법이 여러 가지일 경우
     * 1) 가장 큰 점수 차이를 return
     * 2) 동일한 점수 차이인 경우 가장 낮은 점수를 더 많이 맞힌 경우를 return
     *
     * 제한사항
     * 1 ≤ n ≤ 10
     * info 배열의 length = 11
     * 0 ≤ info 배열 내부 값 ≤ n
     */

    private companion object {
        private const val MAX_SCORE = 10
    }

    private var answer:IntArray = intArrayOf()
    private var maxDiff:Int = 0

    fun solution(n: Int, info: IntArray): IntArray {
        calculateScores(n, info, IntArray(MAX_SCORE + 1), 0)

        return if (answer.isEmpty()) intArrayOf(-1) else answer
    }

    private fun calculateScores(remainCount: Int, apeachInfo: IntArray, ryanInfo: IntArray, index: Int) {
        // 마지막 index까지 온 경우 결과 계산
        if (index == MAX_SCORE + 1) {
            var apeachScore:Int = 0
            var ryanScore:Int = 0

            // 남은 화살이 있는 경우
            if (remainCount > 0) ryanInfo[MAX_SCORE] = remainCount

            // 점수 계산
            apeachInfo.forEachIndexed { index, arrowCount ->
                when {
                    // 어피치와 라이언 모두 해당 점수에 화살을 맞히지 않은 경우
                    arrowCount == 0 && ryanInfo[index] == 0 -> return@forEachIndexed
                    // 어피치가 라이언보다 많이 맞히거나 똑같이 맞힌 경우
                    arrowCount >= ryanInfo[index] -> apeachScore += MAX_SCORE - index
                    // 라이언이 어피치보다 많이 맞힌 경우
                    arrowCount < ryanInfo[index] -> ryanScore += MAX_SCORE - index
                }
            }

            val diff:Int = ryanScore - apeachScore

            /* 라이언이 우승할 수 있는 경우
             * 1.어피치보다 점수가 높은 경우
             * 2.점수가 높은 경우가 여러개일 경우 가장 큰 점수 차이를 return
             * 3.점수가 같은 경우 어피치보다 낮은 점수를 더 많이 맞힌 경우
             */
            if (diff > 0 && (diff > maxDiff || (diff == maxDiff && isHigherLastScore(ryanInfo)))) {
                answer = ryanInfo.clone()
                maxDiff = diff
            }

            return
        }

        // 1.라이언이 해당 점수에 화살을 맞히는 경우
        if (apeachInfo[index] < remainCount) {
            ryanInfo[index] = apeachInfo[index] + 1
            calculateScores(remainCount - ryanInfo[index], apeachInfo, ryanInfo, index + 1)
        }

        // 2.라이언이 해당 점수에 화살을 맞히지 않는 경우
        ryanInfo.fill(0, index, MAX_SCORE + 1)
        calculateScores(remainCount, apeachInfo, ryanInfo, index + 1)
    }

    private fun isHigherLastScore(ryanInfo: IntArray): Boolean {
        for (index in MAX_SCORE downTo 0) {
            if (ryanInfo[index] != answer[index]) {
                return ryanInfo[index] > answer[index]
            }
        }

        return false
    }
}

fun main() {
    val archery = Archery()

    /* 1
    val n = 5
    val info = intArrayOf(2,1,1,1,0,0,0,0,0,0,0) // result [0,2,2,0,1,0,0,0,0,0,0]
    */

    /* 2
    val n = 1
    val info = intArrayOf(1,0,0,0,0,0,0,0,0,0,0) // result [-1]
    */

    /* 3
    val n = 9
    val info = intArrayOf(0,0,1,2,0,1,1,1,1,1,1) // result [1,1,2,0,1,2,2,0,0,0,0]
    */

    /* 4 */
    val n = 10
    val info = intArrayOf(0,0,0,0,0,0,0,0,3,4,3) // result [1,1,1,1,1,1,1,1,0,0,2]


    val result = archery.solution(n, info)
    println(result.joinToString(" "))
}