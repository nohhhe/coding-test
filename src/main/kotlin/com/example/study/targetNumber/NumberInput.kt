package com.example.study.targetNumber

import Solution

class NumberInput {
    fun input() {
        println("숫자를 입력하세요.(2개 이상 20개 이하, 각 숫자는 1 이상 50 이하인 자연수, 입력 값은 콤마로 구분)")

        // 숫자 값 입력 받기
        val numbers = numbersValidationCheck(readln().split(","))

        println("타겟 숫자를 입력하세요.(숫자는 1 이상 1000 이하인 자연수)")
        // 타겟 값 입력 받기
        val targetNumber = targetNumberValidationCheck(readln())

        // 입력 값이 유효할 경우 계산 함수 호출 후 결과 출력
        if (numbers.isNotEmpty()) {
            val result = Solution().solution(numbers, targetNumber)
            println("결과 값: $result")
        }
    }

    // 숫자 값 입력 받는 조건 검사
    private fun numbersValidationCheck(inputs: List<String>): IntArray {
        val numbers = mutableListOf<Int>()

        // 숫자 값 입력 받는 조건
        try {
            // 1.숫자의 개수는 2개 이상 20개 이하
            if (inputs.size < 2 || inputs.size > 20) {
                throw Exception("숫자의 개수는 2개 이상 20개 이하만 입력 가능합니다.")
            }

            // 2.각 숫자는 1 이상 50 이하인 자연수
            inputs.map {
                val number = it.toInt()

                if (number < 1 || number > 50) {
                    throw Exception("숫자는 1 이상 50 이하인 자연수만 입력 가능합니다.")
                }

                // 3.정상 입력 값인 경우 리스트에 추가
                numbers.add(number)
            }
        } catch(e: NumberFormatException) {
            throw Exception("숫자만 입력 가능합니다.")
        } catch (e: Exception) {
            throw Exception(e.message)
        }

        return numbers.toIntArray()
    }

    // 타겟 값 입력 받는 조건 검사
    private fun targetNumberValidationCheck(targetNumber: String): Int {
        // 타겟 값 입력 받는 조건
        // 1.타겟 숫자는 1 이상 1000 이하인 자연수
        try {
            val number = targetNumber.toInt()

            if (number < 1 || number > 1000) {
                throw Exception("숫자는 1 이상 1000 이하인 자연수만 입력 가능합니다.")
            }

            return number
        } catch(e: NumberFormatException) {
            throw Exception("숫자만 입력 가능합니다.")
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}