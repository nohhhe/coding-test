package com.example.study

class BracketConverter {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/60058
     * 괄호 변환
     */

    // 괄호 타입 enum class
    enum class BracketType(val bracket: Char, val value: Int) {
        OPEN('(', 1),
        CLOSE(')', -1);

        companion object {
            // 괄호 문자열을 찾아 해당되는 enum type으로 반환
            fun fromChar(bracket: Char) = values().first { it.bracket == bracket } // enum entries 사용 불가 코틀린 버전 문제
        }
    }

    data class BracketInfo(
        val frontString: String,
        val backString: String,
        val isCorrectBracket: Boolean
    )

    fun solution(p: String): String {
        return recursion(p)
    }

    private fun recursion(inputString: String): String {
        // 빈 문자열이면 종료
        if (inputString.isBlank()) return ""
        val bracketInfo: BracketInfo = findBalancedSubstring(inputString)

        // 앞 문자열이 올바른 괄호 문자열이면 뒷 문자열을 재귀
        return if (bracketInfo.isCorrectBracket) {
            bracketInfo.frontString + recursion(bracketInfo.backString)
        } else {
            // 올바른 괄호 문자열이 아니면 규칙에 맞게 변환
            val convertedString = convertBracket(bracketInfo.frontString)
            BracketType.OPEN.bracket + recursion(bracketInfo.backString) + BracketType.CLOSE.bracket + convertedString
        }
    }

    // 균형 잡힌 문자열을 찾아 앞, 뒤 문자열과 올바른 괄호 문자열 여부를 반환
    private fun findBalancedSubstring(inputString: String): BracketInfo {
        var bracketScore = 0
        // 첫 번째 문자가 열린 괄호면 올바른 괄호 문자열로 시작
        val isCorrectBracket = inputString.first() == BracketType.OPEN.bracket

        inputString.chunked(2).forEachIndexed { index, bracket ->
            // 괄호 문자열을 2개씩 묶어서 점수 계산
            bracketScore += bracket.sumOf { char ->
                BracketType.fromChar(char).value
            }

            // 괄호 점수가 0이 되면 균형 잡힌 문자열
            if (bracketScore == 0) {
                return BracketInfo(
                    inputString.substring(0, (index + 1) * 2),
                    inputString.substring((index + 1) * 2),
                    isCorrectBracket
                )
            }
        }

        return BracketInfo(inputString, "", isCorrectBracket)
    }

    private fun convertBracket(convertString: String): String {
        // 첫 번째와 마지막 문자 제거 후 괄호 변환
        val convertedString = convertString
            .substring(1, convertString.length - 1)
            .map {
                if (it == BracketType.OPEN.bracket) BracketType.CLOSE.bracket
                else BracketType.OPEN.bracket
            }.joinToString("")

        return convertedString
    }
}

fun main() {
    val bracketConverter = BracketConverter()

    // 1
    val string1 = "(()())()"

    val result1: String = bracketConverter.solution(string1) // "(()())()"
    println("result1: $result1")

    // 2
    val string2 = ")("

    val result2: String = bracketConverter.solution(string2) // "()"
    println("result2: $result2")

    // 3
    val string3 = "()))((()"

    val result3: String = bracketConverter.solution(string3) // "()(())()"
    println("result3: $result3")

    // 4
    val string4 = "))()(("

    val result4: String = bracketConverter.solution(string4) // "()()()"
    println("result4: $result4")

    // 5
    val string5 = "())()(()"

    val result5: String = bracketConverter.solution(string5) // "()((()))"
    println("result3: $result5")
}