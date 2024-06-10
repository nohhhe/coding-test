package com.example.study

class MenuRenewal2 {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/72411
     * 메뉴 리뉴얼
     * 이전 손님들이 가장 많이 함께 주문한 단품 메뉴들로 코스 요리 메뉴를 구성
     * 코스 요리 메뉴는 최소 2가지 이상의 단품 메뉴로 구성
     * 최소 2명 이상의 손님으로부터 주문된 단품 메뉴 조합만 코스 요리 메뉴 후보
     * 각 손님은 단품 메뉴를 2가지 이상 주문
     * 각 단품 메뉴는 A ~ Z 알파벳 대문자로 표기
     *
     * 각 손님들이 주문한 단품 메뉴들이 문자열 형식으로 담긴 배열 orders
     * 코스 요리를 구성하는 단품 메뉴 개수가 담긴 배열 course
     * 새로 추가하게 될 코스 요리의 메뉴 구성을 문자열 형태로 배열에 담아 return
     *
     * 제한사항
     * 2 ≤ orders의 길이 ≤ 20
     * 2 ≤ orders의 각 원소의 길이 ≤ 10
     * * orders의 각 원소는 A ~ Z 알파벳 대문자로만 이루어져 있음
     * * 같은 단품 메뉴(알파벳)가 중복되어 들어있지 않음
     * 1 ≤ course의 길이 ≤ 10
     * * course의 각 원소는 2 이상 10 이하인 자연수가 오름차순으로 정렬
     * * course에 중복되는 값은 없음
     * 새로 추가하게 될 코스 요리의 메뉴 구성을 문자열 형태로 배열에 담아 사전 순으로 오름차순으로 정렬하여 return
     * * 코스 요리 메뉴의 구성은 알파벳 대문자로 오름차순 정렬
     * * 가장 많이 함께 주문된 메뉴가 여러개라면, 모두 배열에 담아 return
     */

    data class MenuInfo(val menu: String, val menuCount: Int, var orderCount: Int = 1)

    fun solution(orders: Array<String>, course: IntArray): Array<String> {
        var answer: Array<String> = arrayOf()
        val newCourseMenus: MutableList<MenuInfo> = mutableListOf()

        orders.forEach { order ->
            course.forEach { courseSize ->
                // 코스 요리 알파벳 순으로 정렬 후 코스 요리 메뉴 조합을 구함
                val courseMenus: MutableList<String> = getCourseMenu(order.toCharArray().sorted(), courseSize)

                // 코스 요리 메뉴 조합을 코스 요리 메뉴 리스트에 추가
                courseMenus.forEach { courseMenu ->
                    // 이미 추가된 코스 요리 메뉴인 경우 주문 수 증가, 아닌 경우 새 메뉴 추가
                    newCourseMenus.firstOrNull { it.menu == courseMenu }?.also { courseMenuInfo ->
                        courseMenuInfo.orderCount++
                    } ?: newCourseMenus.add(
                        MenuInfo(courseMenu, courseSize)
                    )
                }
            }
        }

        /* 코스 요리 메뉴 조건
         * 1. 주문 수가 2번 이상인 경우
         * 2. 각 코스 개수별 주문 수가 가장 많은 경우(동일한 주문 수인 경우 모두 추가)
         */
        newCourseMenus
            .filter { it.orderCount >= 2 }
            .groupBy { it.menuCount }
            .forEach {(_, courseMenus) ->
                val maxOrderCount: Int = courseMenus.maxOf { it.orderCount }

                courseMenus.filter { it.orderCount == maxOrderCount }.forEach { courseMenuInfo ->
                    answer += courseMenuInfo.menu
                }
            }

        // 사전 순으로 오름차순 정렬
        return answer.sorted().toTypedArray()
    }

    // 코스 요리 메뉴 조합을 구하는 함수
    private fun getCourseMenu(order: List<Char>, courseSize: Int): MutableList<String> {
        // 코스 요리 메뉴 조합이 1개인 경우 현재 메뉴를 반환
        if (courseSize == 1) return order.mapTo(mutableListOf()) { it.toString() }

        val courseMenus = mutableListOf<String>()
        // 현재 메뉴와 다음 메뉴들을 조합하여 코스 요리 메뉴 조합을 구함
        order.forEachIndexed { index, menu ->
            val subMenus = order.slice(index + 1 until order.size)
            getCourseMenu(subMenus, courseSize - 1).forEach { subMenu ->
                courseMenus.add(menu + subMenu)
            }
        }

        return courseMenus
    }
}

fun main() {
    val menuRenewal = MenuRenewal2()

    /* 1
    val orders = arrayOf("ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH")
    val course = intArrayOf(2, 3, 4) // ["AC", "ACDE", "BCFG", "CDE"]
*/

    /* 2
    val orders = arrayOf("ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD")
    val course = intArrayOf(2, 3, 5) // ["ACD", "AD", "ADE", "CD", "XYZ"]
*/

    /* 3
    val orders = arrayOf("XYZ", "XWY", "WXA")
    val course = intArrayOf(2, 3, 4) // ["WX", "XY"]
*/

    val orders = arrayOf("ABCD", "ABCD", "ABCD")
    val course = intArrayOf(2, 3, 4) // ['AB', 'ABC', 'ABCD', 'ABD', 'AC', 'ACD', 'AD', 'BC', 'BCD', 'BD', 'CD']


    val result = menuRenewal.solution(orders, course)
    println(result.joinToString(", "))
}