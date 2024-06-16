package com.example.study

class Troublemaker {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/64064
     * 불량 사용자
     * 불량 사용자라는 이름으로 목록을 만들어 당첨 처리 시 제외
     * 불량 사용자 아이디 중 일부 문자를 1개 이상의 '*' 문자로 가림
     * 불량 사용자 아이디와 매칭된 응모자 아이디를 제재 아이디라고 부를 때
     * 불량 사용자에 매핑되어서 당첨 제외가 되어야할 제재 아이디 목록의 경우의 수를 구하시오
     *
     * user_id: 응모자 아이디 목록
     * banned_id: 불량 사용자 아이디 목록
     * return: 당첨에서 제외되어야 할 제재 아이디 목록의 경우의 수
     *
     * 제한사항
     * 1.공통
     *  1)id는 1 ≤ id의 길이 ≤ 8 문자열
     *  2)id는 알파벳 소문자와 숫자로만 이루어짐
     * 2.user_id
     *  1)응모자 아이디는 중복되지 않음
     * 3.banned_id
     *  1) 1 ≤ banned_id의 크기 ≤ user_id의 크기
     *  2) 불량 아이디 하나는 응모자 아이디 하나에 매핑, 중복해서 매핑되는 경우는 없음
     */

    fun solution(user_ids: Array<String>, banned_ids: Array<String>): Int {
        // 불량 사용자 아이디 패턴 생성
        val bannedIdPatterns = banned_ids.map { it.replace("*", ".").toRegex() }

        return findBannedIdCombinations(0, user_ids.toList(), bannedIdPatterns, emptyList()).size
    }

    fun findBannedIdCombinations(
        index: Int,
        userIds: List<String>,
        bannedIdPatterns: List<Regex>,
        previousCombination: List<String>
    ): MutableSet<List<String>> {
        val bannedCombinations: MutableSet<List<String>> = mutableSetOf()

        // 불량 사용자 아이디 패턴을 마지막 인덱스까지 찾은 경우 조합을 정렬하여 반환(중복 제거를 위해 정렬)
        if (index == bannedIdPatterns.size) {
            bannedCombinations.add(previousCombination.sorted())
            return bannedCombinations
        }

        // 불량 사용자 아이디 패턴에 매칭되는 응모자 아이디 찾은 리스트로 조합 찾기
        userIds.filter { bannedIdPatterns[index].matches(it) }.forEach { matchedUserId ->
            // 매칭된 응모자 아이디 제외
            val nextUserIds = userIds.filter { it != matchedUserId }
            // 현재까지의 조합을 생성(이전 조합 + 매칭된 응모자 아이디)
            val currentCombination = previousCombination + matchedUserId

            // 재귀를 통해 찾은 불량 사용자 아이디 조합 추가
            bannedCombinations.addAll(
                findBannedIdCombinations(index + 1, nextUserIds, bannedIdPatterns, previousCombination)
            )
        }

        return bannedCombinations
    }
}

fun main() {
    val troublemaker: Troublemaker = Troublemaker()

    // 1
    val user_ids1 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val banned_ids1 = arrayOf("fr*d*", "abc1**") // result = 2

    val result1 = troublemaker.solution(user_ids1, banned_ids1)
    println(result1)

    // 2
    val user_ids2 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val banned_ids2 = arrayOf("*rodo", "*rodo", "******") // result = 2

    val result2 = troublemaker.solution(user_ids2, banned_ids2)
    println(result2)

    // 3
    val user_ids3 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val banned_ids3 = arrayOf("fr*d*", "*rodo", "******", "******") // result = 3

    val result3 = troublemaker.solution(user_ids3, banned_ids3)
    println(result3)

    // 4
    val user_ids4 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val banned_ids4 = arrayOf("*rodo", "*rodo", "******", "******") // result = 1

    val result4 = troublemaker.solution(user_ids4, banned_ids4)
    println(result4)

    // 5
    val user_ids5 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val banned_ids5 = arrayOf("*rodo", "*rodo", "******", "******", "******") // result = 0

    val result5 = troublemaker.solution(user_ids5, banned_ids5)
    println(result5)

    // 6
    val user_ids6 = arrayOf("aa", "ab", "ac", "ad", "ae", "be")
    val banned_ids6 = arrayOf("a*", "a*", "*e", "**") // result = 14

    val result6 = troublemaker.solution(user_ids6, banned_ids6)
    println(result6)

    // 7
    val user_ids7 = arrayOf("aa", "ab", "ac", "ad", "ae", "be")
    val banned_ids7 = arrayOf("*b", "*c", "*d", "*e", "a*", "**") // result = 1

    val result7 = troublemaker.solution(user_ids7, banned_ids7)
    println(result7)
}