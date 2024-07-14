package com.example.study

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HotelRoomRental {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/155651
     * 호텔 대실
     */

    companion object {
        // 청소 시간(10분) 이후 바로 입장 가능하기 때문에 9분 59초로 설정
        private val CLEANING_TIME: Duration = Duration.ofSeconds(9 * 60 + 59)
    }

    // 대실 예약 시간을 입실 시간과 퇴실 시간으로 정렬하여 List<BookTime>로 반환하는 함수
    private fun createBookTimeList(bookTimes: Array<Array<String>>): List<BookTime> {
        val currentDate = LocalDate.now()

        return bookTimes.map { bookTime ->
            val startTime = LocalTime.parse(bookTime[0])
            val endTime = LocalTime.parse(bookTime[1])

            BookTime(
                LocalDateTime.of(currentDate, startTime),
                LocalDateTime.of(currentDate, endTime)
            )
        }.sortedWith(compareBy({ it.startTime }, { it.endTime }))
    }

    data class BookTime(val startTime: LocalDateTime, val endTime: LocalDateTime)

    fun solution(book_time: Array<Array<String>>): Int {
        val bookTimeList: List<BookTime> = createBookTimeList(book_time)
        val confirmedBookTimeList: MutableList<MutableList<BookTime>> = mutableListOf()

        bookTimeList.forEachIndexed { index, bookTime ->
            if (index == 0) {
                confirmedBookTimeList.add(mutableListOf(bookTime))
            } else {
                var isBooked = false

                run loop@{
                    confirmedBookTimeList.forEach { confirmedBookTime ->
                        // 마지막 예약의 퇴실 시간 + 청소 시간이 다음 예약의 입실 시간보다 이전이면 예약 가능
                        if (confirmedBookTime.last().endTime.plus(CLEANING_TIME).isBefore(bookTime.startTime)) {
                            confirmedBookTime.add(bookTime)
                            isBooked = true
                            return@loop
                        }
                    }
                }

                // 이전 객실 예약에 포함되지 않은 경우 새 객실 예약으로 추가
                if (!isBooked) {
                    confirmedBookTimeList.add(mutableListOf(bookTime))
                }
            }
        }

        return confirmedBookTimeList.size
    }
}

fun main() {
    val hotelRoomRental = HotelRoomRental()

    // 1
    val bookTime1: Array<Array<String>> = arrayOf(
        arrayOf("15:00", "17:00"),
        arrayOf("16:40", "18:20"),
        arrayOf("14:20", "15:20"),
        arrayOf("14:10", "19:20"),
        arrayOf("18:20", "21:20")
    )
    val result1: Int = hotelRoomRental.solution(bookTime1) // 3
    println("result1: $result1")

    // 2
    val bookTime2: Array<Array<String>> = arrayOf(
        arrayOf("09:10", "10:10"),
        arrayOf("10:20", "12:20")
    )
    val result2: Int = hotelRoomRental.solution(bookTime2) // 1
    println("result2: $result2")

    // 3
    val bookTime3: Array<Array<String>> = arrayOf(
        arrayOf("10:20", "12:30"),
        arrayOf("10:20", "12:30"),
        arrayOf("10:20", "12:30")
    )
    val result3: Int = hotelRoomRental.solution(bookTime3) // 3
    println("result3: $result3")

    // 4
    val bookTime4: Array<Array<String>> = arrayOf(
        arrayOf("10:00", "10:20"),
        arrayOf("09:00", "09:20"),
        arrayOf("09:20", "09:40"),
        arrayOf("09:40", "10:00")
    )
    val result4: Int = hotelRoomRental.solution(bookTime4) // 2
    println("result4: $result4")

    // 5
    val bookTime5: Array<Array<String>> = arrayOf(
        arrayOf("08:00", "08:30"),
        arrayOf("08:00", "13:00"),
        arrayOf("12:30", "13:30")
    )
    val result5: Int = hotelRoomRental.solution(bookTime5) // 2
    println("result5: $result5")

    // 6
    val bookTime6: Array<Array<String>> = arrayOf(
        arrayOf("00:01", "00:10"),
        arrayOf("00:19", "00:29")
    )
    val result6: Int = hotelRoomRental.solution(bookTime6) // 2
    println("result6: $result6")

    // 7
    val bookTime7: Array<Array<String>> = arrayOf(
        arrayOf("05:57", "06:02"),
        arrayOf("04:00", "06:59"),
        arrayOf("03:56", "07:57"),
        arrayOf("06:12", "08:55"),
        arrayOf("07:09", "07:11")
    )
    val result7: Int = hotelRoomRental.solution(bookTime7) // 3
    println("result7: $result7")

    // 8
    val bookTime8: Array<Array<String>> = arrayOf(
        arrayOf("11:01", "17:27"),
        arrayOf("04:10", "04:20"),
        arrayOf("07:59", "08:59"),
        arrayOf("09:10", "10:49"),
        arrayOf("11:01", "17:27"),
        arrayOf("04:10", "04:20"),
        arrayOf("07:59", "08:59"),
        arrayOf("09:10", "10:49")
    )
    val result8: Int = hotelRoomRental.solution(bookTime8) // 2
    println("result8: $result8")

    // 9
    val bookTime9: Array<Array<String>> = arrayOf(
        arrayOf("09:10", "10:11"),
        arrayOf("10:20", "12:20")
    )
    val result9: Int = hotelRoomRental.solution(bookTime9) // 2
    println("result9: $result9")

    // 10
    val bookTime10: Array<Array<String>> = arrayOf(
        arrayOf("16:00", "16:10"),
        arrayOf("16:20", "16:30"),
        arrayOf("16:40", "16:50")
    )
    val result10: Int = hotelRoomRental.solution(bookTime10) // 1
    println("result10: $result10")

    // 11
    val bookTime11: Array<Array<String>> = arrayOf(
        arrayOf("09:10", "10:10"),
        arrayOf("10:20", "12:20"),
        arrayOf("12:30", "13:20")
    )
    val result11: Int = hotelRoomRental.solution(bookTime11) // 1
    println("result11: $result11")

    // 12
    val bookTime12: Array<Array<String>> = arrayOf(
        arrayOf("10:00", "10:10")
    )
    val result12: Int = hotelRoomRental.solution(bookTime12) // 1
    println("result12: $result12")

    // 13
    val bookTime13: Array<Array<String>> = arrayOf(
        arrayOf("11:01", "17:27"),
        arrayOf("04:10", "04:20"),
        arrayOf("07:59", "08:59"),
        arrayOf("09:10", "10:49")
    )
    val result13: Int = hotelRoomRental.solution(bookTime13) // 1
    println("result13: $result13")

    // 14
    val bookTime14: Array<Array<String>> = arrayOf(
        arrayOf("00:00", "00:07"),
        arrayOf("00:01", "00:08"),
        arrayOf("00:02", "00:09"),
        arrayOf("10:26", "10:41")
    )
    val result14: Int = hotelRoomRental.solution(bookTime14) // 3
    println("result14: $result14")

    // 15
    val bookTime15: Array<Array<String>> = arrayOf(
        arrayOf("01:00", "02:01"),
        arrayOf("02:10", "03:00"),
        arrayOf("03:10", "04:00")
    )
    val result15: Int = hotelRoomRental.solution(bookTime15) // 2
    println("result15: $result15")

    // 16
    val bookTime16: Array<Array<String>> = arrayOf(
        arrayOf("11:50", "11:59"),
        arrayOf("12:09", "12:45"),
        arrayOf("11:50", "11:59"),
    )
    val result16: Int = hotelRoomRental.solution(bookTime16) // 2
    println("result16: $result16")

    // 17
    val bookTime17: Array<Array<String>> = arrayOf(
        arrayOf("08:00", "08:30"),
        arrayOf("08:00", "13:00"),
        arrayOf("12:30", "13:30"),
    )
    val result17: Int = hotelRoomRental.solution(bookTime17) // 2
    println("result17: $result17")

    // 18
    val bookTime18: Array<Array<String>> = arrayOf(
        arrayOf("23:40", "23:55"),
        arrayOf("23:42", "23:52")
    )
    val result18: Int = hotelRoomRental.solution(bookTime18) // 2
    println("result18: $result18")
}

// 2 3 6 7 8 10 11 12