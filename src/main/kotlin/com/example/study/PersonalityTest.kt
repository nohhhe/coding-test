package com.example.study

class PersonalityTest {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/118666
     * 성격 유형 검사하기
     */

    companion object {
        // 선택에 따른 점수
        private val SCORES = listOf(3, 2, 1, 0, 1, 2, 3)
        /* 성격 유형
         * 라이언형(R), 튜브형(T)
         * 콘형(C), 프로도형(F)
         * 제이지형(J), 무지형(M)
         * 어피치형(A), 네오형(N)
         */
        private val PERSONALITY = listOf('R' to 'T', 'C' to 'F', 'J' to 'M', 'A' to 'N')
    }

    fun solution(surveys: Array<String>, choices: IntArray): String {
        var answer: String = ""
        val personalityScores: MutableMap<Char, Int> = mutableMapOf()

        surveys.forEachIndexed { index, survey ->
            // 선택지에 따른 성격 유형과 점수를 계산
            val score = choices[index].choiceToScore(survey)

            // 성격 유형별 점수 계산(유형이 같은 경우 누적, 없는 경우 추가)
            personalityScores[score.first] = personalityScores.getOrDefault(score.first, 0) + score.second
        }

        // 성격 유형별 점수를 비교하여 성격 유형을 반환
        PERSONALITY.forEach { (first, second) ->
            // 점수가 같은 경우 사전 순으로 빠른 성격 유형을 반환(PERSONALITY에 정의된 순서)
            answer += if (personalityScores.getOrDefault(first, 0) >= personalityScores.getOrDefault(second, 0)) first else second
        }

        return answer
    }

    // 선택지에 따른 성격 유형과 점수를 반환
    private fun Int.choiceToScore(survey: String): Pair<Char, Int> {
        // 1 ~ 4점인 경우 첫 번째 성격 유형(4점은 0점이기 때문에 영향도 없음), 5 ~ 7점인 경우 두 번째 성격 유형
        val char = if (this < 4) survey[0] else survey[1]

        return char to SCORES[this - 1]
    }
}

fun main() {
    val personalityTest = PersonalityTest()

    // 1
    val surveys1 = arrayOf("AN", "CF", "MJ", "RT", "NA")
    val choices1 = intArrayOf(5, 3, 2, 7, 5) // result = "TCMA"

    val result1 = personalityTest.solution(surveys1, choices1)
    println(result1)

    // 2
    val surveys2 = arrayOf("TR", "RT", "TR")
    val choices2 = intArrayOf(7, 1, 3) // result = "RCJA"

    val result2 = personalityTest.solution(surveys2, choices2)
    println(result2)
}