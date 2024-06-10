package com.example.study

class MenuRenewal {
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

    data class MenuInfo(val menu: String, val orderCount: Int, val menuCount: Int)

    fun solution(orders: Array<String>, course: IntArray): Array<String> {
        val newCourseMenus: MutableList<MenuInfo> = mutableListOf()

        orders.forEachIndexed { index, order ->
            // 다음 주문 내역들과 비교
            orders.sliceArray(index + 1 until orders.size).forEach { nextOrder ->
                // 주문 내역 중 공통 메뉴 찾기
                var commonMenu:String = order.filter { nextOrder.contains(it) }
                // 메뉴 개수
                val commonMenuCount = commonMenu.length

                /* 코스 요리 메뉴 조건
                 * 1. 구성 코스 요리(course) 개수에 포함 (2개 이상)
                 * 3. 이미 추가된 코스 요리 메뉴는 제외
                 */
                if (course.contains(commonMenuCount)) {
                    // 공통 메뉴가 포함된 주문 내역 수
                    val commonMenuOrderCount = orders.count { order ->
                        commonMenu.all { char -> order.contains(char) }
                    }

                    // 문자열 정렬 후 배열에 추가
                    commonMenu = commonMenu.toSortedSet().joinToString("")

                    // 리스트 중복 체크 후 추가
                    if (newCourseMenus.none { it.menu == commonMenu }) {
                        val newCourseMenu = MenuInfo(
                            commonMenu,
                            commonMenuOrderCount,
                            commonMenuCount
                        )

                        // 이미 추가된 코스 요리 메뉴가 있을 경우 제외
                        newCourseMenus.firstOrNull { it.menuCount == commonMenuCount }?.also { courseMenu ->
                            // 주문 건 수가 더 많은 경우 기존 코스 메뉴 삭제 후 추가 (주문 건 수가 같은 경우 삭제하지 않고 추가)
                            if (courseMenu.orderCount <= commonMenuOrderCount) {
                                newCourseMenus.removeIf { it.menuCount < commonMenuCount }
                                newCourseMenus.add(newCourseMenu)
                            }
                        } ?: newCourseMenus.add(newCourseMenu)
                    }
                }
            }
        }

        // 사전 순으로 오름차순 정렬
        return newCourseMenus.map { it.menu }.sorted().toTypedArray()
    }
}

fun main() {
    val menuRenewal = MenuRenewal()

    /* 1 */
    val orders = arrayOf("ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH")
    val course = intArrayOf(2, 3, 4) // ["AC", "ACDE", "BCFG", "CDE"]


    /* 2
    val orders = arrayOf("ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD")
    val course = intArrayOf(2, 3, 5) // ["ACD", "AD", "ADE", "CD", "XYZ"]
    */

    /* 3
    val orders = arrayOf("XYZ", "XWY", "WXA")
    val course = intArrayOf(2, 3, 4) // ["WX", "XY"]


    val orders = arrayOf("ABCD", "ABCD", "ABCD")
    val course = intArrayOf(2, 3, 4) // ['AB', 'ABC', 'ABCD', 'ABD', 'AC', 'ACD', 'AD', 'BC', 'BCD', 'BD', 'CD']
    */

    val result = menuRenewal.solution(orders, course)
    println(result.joinToString(", "))
}