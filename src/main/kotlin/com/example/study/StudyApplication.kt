package com.example.study

import Solution
import com.example.study.targetNumber.NumberInput
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudyApplication

fun main(args: Array<String>) {
	runApplication<StudyApplication>(*args)

	NumberInput().input()
	//val result = Solution().solution(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), 3) // OutOfMemoryError
	//val result = Solution().solution2(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), 3) // 0, 스택 사이즈 제한?, 스택 오버플로우(재귀 호출로 인한 메모리 초과) 또는 시간 초과 등의 문제?=

	//val result = Solution().solution(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), 3) // 36s
	//val result = Solution().solution2(intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), 3) // 8s
	//println("결과 값: $result")
}
