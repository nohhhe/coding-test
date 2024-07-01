package com.example.study

class PrivacyCollectionPeriod {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/150370
     * 개인정보 수집 유효기간
     */

    data class TermsInfo(val terms: String, val period: Int)
    data class PrivacyInfo(val terms: String, var date: String)
    class CustomDate(dateString: String) {
        private val date: List<String> = dateString.split(".")
        private val year: Int = date[0].toInt()
        private val month: Int = date[1].toInt()
        private val day: Int = date[2].toInt()

        // 개월수를 더한 날짜 반환
        fun addMonth(months: Int): CustomDate {
            // 전체 개월수로 변환하고 전달 받은 개월수를 더해서 다시 날짜로 변환
            val totalMonth = this.month + months - 1
            val newYear = this.year + totalMonth / 12
            val newMonth = totalMonth % 12 + 1

            return CustomDate("$newYear.$newMonth.${this.day}")
        }

        // 날짜 비교하여 이전 or 같은지 확인
        fun isBeforeOrEqual(otherDate: CustomDate): Boolean {
            // 년도가 다르면 년도로 비교, 같으면 월로 비교, 같으면 일로 비교
            return when {
                this.year != otherDate.year -> this.year < otherDate.year
                this.month != otherDate.month -> this.month < otherDate.month
                else -> this.day <= otherDate.day
            }
        }
    }

    fun solution(today: String, termsArray: Array<String>, privacies: Array<String>): IntArray {
        var answer: IntArray = intArrayOf()
        val todayDate = CustomDate(today)

        // 약관 정보를 데이터 클래스로 변환
        val termsList = termsArray.map { terms ->
            terms.split(" ").let { term ->
                TermsInfo(term[0], term[1].toInt())
            }
        }

        privacies.forEachIndexed { index, privacy ->
            // 개인정보 수집 유효기간 정보를 데이터 클래스로 변환
            val privacyInfo = privacy.split(" ").let { privacyData ->
                PrivacyInfo(privacyData[1], privacyData[0])
            }
            // 약관에 해당하는 유효기간을 찾아서 기간을 가져옴(항상 termsArray에 약관이 존재하기 때문에 first 사용)
            val privacyPeriod:Int = termsList.first { it.terms == privacyInfo.terms }.period
            // 만료 일자 계산
            val expirationDate = CustomDate(privacyInfo.date).addMonth(privacyPeriod)

            // 만료일이 지난 경우 파기할 목록에 추가
            if (expirationDate.isBeforeOrEqual(todayDate)) {
                answer += index + 1
            }
        }

        return answer
    }
}

fun main() {
    val privacyCollectionPeriod = PrivacyCollectionPeriod()

    // 1
    val today1 = "2022.05.19"
    val terms1 = arrayOf("A 6", "B 12", "C 3")
    val privacies1 = arrayOf("2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C") // 1, 3

    val result1: IntArray = privacyCollectionPeriod.solution(today1, terms1, privacies1)
    println(result1.joinToString(","))

    // 2
    val today2 = "2020.01.01"
    val terms2 = arrayOf("Z 3", "D 5")
    val privacies2 = arrayOf("2019.01.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z") // 1, 4, 5

    val result2: IntArray = privacyCollectionPeriod.solution(today2, terms2, privacies2)
    println(result2.joinToString(","))
}