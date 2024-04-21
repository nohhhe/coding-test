package miroEscape

class MiroEscape {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/159993
     * 1 x 1 크기의 칸들로 이루어진 직사각형 격자 형태의 미로에서 탈출
     * 각 칸은 통로 또는 벽으로 구성되어 있으며, 벽으로 된 칸은 지나갈 수 없고 통로로 된 칸으로만 이동할 수 있다.
     * 통로는 시작, 출구, 레버 칸으로 구성되어 있고 출구는 레버를 당겨야만 탈출할 수 있다. 단, 레버를 당기지 않았어도 출구 칸은 지나갈 수 있다.
     * 최대한 빠르게 미로를 탈출하는 방법을 찾아야 한다. 탈출할 수 없다면 -1을 반환한다.
     *
     * maps: 문자열 배열(미로를 표시)
     * return: 최소 이동 칸의 개수
     *
     * 제한사항
     * 5 <= maps.length <= 100
     * 5 <= maps[i].length <= 100
     * maps[i]는 S: 시작, E: 출구, L: 레버, O: 통로, X: 벽으로 이루어져 있다.
     */
    private companion object {
        private const val START = "S"
        private const val EXIT = "E"
        private const val LEVER = "L"
    }

    data class Position(val x: Int, val y: Int, val route: String)

    fun solution(maps: Array<String>): Int {
        var answer: Int = 0

        // 현재 좌표에 시작이 있는 좌표 세팅 (X, Y, route)
        val start = maps.asSequence().mapIndexedNotNull { index, value ->
            value.indexOf(START).takeIf { it >= 0 }?.let { Position(index, it, "") }
        }.firstOrNull()

        var routes: MutableList<Position> = mutableListOf(start!!)

        while (answer == 0) {
            val newRoutes = mutableListOf<Position>()

            routes.forEach {
                val leverIndex= it.route.indexOf(LEVER)
                val exitIndex = it.route.indexOf(EXIT)

                // 레버를 올리고 출구에 도착한 경우
                if (leverIndex in 1 until exitIndex) answer = it.route.length

                // 위로 갈 수 있는 루트 추가
                if (it.x - 1 >= 0 && maps[it.x - 1][it.y] != 'X') {
                    newRoutes.add(Position(it.x - 1, it.y, it.route + maps[it.x - 1][it.y]))
                }

                // 아래로 갈 수 있는 루트 추가
                if (it.x + 1 < maps.size && maps[it.x + 1][it.y] != 'X') {
                    newRoutes.add(Position(it.x + 1, it.y, it.route + maps[it.x + 1][it.y]))
                }

                // 왼쪽으로 갈 수 있는 루트 추가
                if (it.y - 1 >= 0 && maps[it.x][it.y - 1] != 'X') {
                    newRoutes.add(Position(it.x, it.y - 1, it.route + maps[it.x][it.y - 1]))
                }

                // 오른쪽으로 갈 수 있는 루트 추가
                if (it.y + 1 < maps[it.x].length && maps[it.x][it.y + 1] != 'X') {
                    newRoutes.add(Position(it.x, it.y + 1, it.route + maps[it.x][it.y + 1]))
                }
            }

            if (newRoutes.isEmpty()) answer = -1 else routes = newRoutes
        }

        return answer
    }
}

fun main() {
    val miroEscape = MiroEscape()

    val result = miroEscape.solution(arrayOf("SOOOOXOO","XOOOOOOO","OXOOOOOO","EOOOOOOO","OOOOOOOL"))

    println("result: $result")
}