package com.example.study.nQueen

class Solution {
    /* Question. n-Queen (https://programmers.co.kr/learn/courses/30/lessons/12952)
     * n개의 퀸이 서로 공격할 수 없는 배치의 경우의 수를 반환
     * n: 정사각형의 가로, 세로와 퀸의 개수
     * return: 서로 공격할 수 없는 배치의 경우의 수
     *
     * 제한사항
     * 퀸(Queen)은 가로, 세로 대각선으로 이동할 수 있습니다.
     * n은 12이하의 자연수 입니다.
     */

    fun solution(n: Int): Int {
        return recursion(0,  0, 0, 0, n, (1 shl n) - 1)
    }

    private fun recursion(queen: Int, row: Int, left:Int, right: Int, count: Int, maxBit: Int): Int {
        var answer = 0

        if (queen == count) return 1

        /* 다음 퀸이 놓을 수 있는 자리 체크(1이면 퀸을 놓을 수 있는 자리)
         * row: 퀸이 놓일 수 없는 행 자리
         * left: 퀸이 놓일 수 없는 왼쪽 대각선 자리
         * right: 퀸이 놓일 수 없는 오른쪽 대각선 자리
         * left 비트가 초과되는 경우를 대비해 maxBit로 and 연산하여 비트를 제한
         */
        var checkBit = (row or left or right).inv() and maxBit

        // checkBit가 0이면 퀸을 놓을 수 있는 자리가 없음
        while (checkBit != 0) {
            // 2의 보수로 다음 퀸 비트를 세팅
            val queenBit = checkBit and -checkBit
            answer += recursion(queen + 1, (row or queenBit), ((left or queenBit) shl 1), ((right or queenBit) shr 1), count, maxBit)
            // 사용한 퀸 비트 자리 제거
            checkBit -= queenBit
        }

        return answer
    }
}

private fun validationCheck(inputNumber: String): Int {
    try {
        val number = inputNumber.toInt()

        if (number < 1 || number > 12) throw NumberFormatException()

        return number
    } catch (e: NumberFormatException) {
        throw Exception("숫자는 12개 이하 자연수만 입력 가능합니다.")
    }
}

fun main() {
    val solution = Solution()

    println("퀸의 개수를 입력하세요.(12개 이하 자연수)")
    val result = solution.solution(validationCheck(readln()))
    println("result: $result")
}