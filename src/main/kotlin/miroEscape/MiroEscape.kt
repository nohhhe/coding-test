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
        private const val START = 'S'
        private const val EXIT = 'E'
        private const val LEVER = 'L'
        private const val BLOCK = 'X'
        private val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
    }

    data class Position(val x: Int, val y: Int, val count:Int = 0, val isLever: Boolean = false)

    private fun Boolean.toInt() = if (this) 1 else 0

    fun solution(maps: Array<String>): Int {
        // 현재 좌표에 시작이 있는 좌표 세팅 (X, Y, route)
        val start = maps.asSequence().mapIndexedNotNull { index, value ->
            value.indexOf(START).takeIf { it >= 0 }?.let { Position(index, it) }
        }.firstOrNull() ?: return -1

        val routes: MutableList<Position> = mutableListOf(start)
        // 레버 상태에 따라 방문한 좌표 체크 (레버 방문 후에는 기존 좌표를 다시 방문할 수 있음)
        val visits: List<List<BooleanArray>> = List(maps.size) { List(maps[it].length) { BooleanArray(2) } }

        while (routes.isNotEmpty()) {
            val position = routes.removeFirst()

            // 레버를 올리고 출구에 도착한 경우
            if (position.isLever && maps[position.x][position.y] == EXIT) return position.count

            // 현재 위치에서 네 방향으로 갈 수 있는 루트 추가
            directions.forEach { direction ->
                val x = position.x + direction.first
                val y = position.y + direction.second

                // 범위 내에 있고, 벽이 아니며, 방문하지 않은 경우
                if (x in maps.indices && y in maps[x].indices && maps[x][y] != BLOCK && !visits[x][y][position.isLever.toInt()]) {
                    visits[x][y][position.isLever.toInt()] = true
                    routes.add(Position(x, y, position.count + 1, (position.isLever || maps[x][y] == LEVER)))
                }
            }
        }

        return -1
    }
}

fun main() {
    val miroEscape = MiroEscape()

    val result = miroEscape.solution(arrayOf("SOOOL","XXXXO","OOOOO","OXXXX","OOOOE"))

    println("result: $result")
}