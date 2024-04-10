class Solution {
    // 오버헤드 발생 이슈
    fun solution(numbers: IntArray, target: Int): Int {
        // 합계 리스트
        var sums = mutableListOf<Int>()

        numbers.forEachIndexed { index, number ->
            // 첫 번째 인덱스인 경우 합계 리스트에 추가
            if (index == 0) {
                sums = mutableListOf(number, -number)
                return@forEachIndexed
            }

            // 합계 리스트에 더하기, 빼기 연산 결과 추가
            sums = sums.flatMapTo(mutableListOf()) {
                listOf(it + number, it - number)
            }
        }

        // 합계 리스트에서 타겟 값과 같은 값의 개수 반환
        return sums.count { it == target }
    }

    // 스택 오버플로우 발생 이슈
    fun solution2(numbers: IntArray, target: Int): Int {
        return recursion(numbers, target, 0, 0)
    }

    private fun recursion(numbers: IntArray, target:Int, index:Int, sum: Int): Int {
        // 재귀 호출 종료 조건
        if (index == numbers.size) {
            // 타겟 값과 합계 값이 같으면 1, 아니면 0 반환
            return if (sum == target) 1 else 0
        }

        // 재귀 호출(더하기)
        val plusAnswer = recursion(numbers, target, index + 1, sum - numbers[index])
        // 재귀 호출(빼기)
        val minusAnswer = recursion(numbers, target, index + 1, sum + numbers[index])

        // 더하기, 빼기 결과 합산
        return plusAnswer + minusAnswer
    }
}