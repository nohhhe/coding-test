package com.example.study

import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class ParkingFeeCalculator {
    /* https://school.programmers.co.kr/learn/courses/30/lessons/92341
     * 주차장 요금 계산
     * 입차된 후에 출차된 내역이 없다면, 23:59에 출차된 것으로 간주
     * 00:00부터 23:59까지의 입/출차 내역을 바탕으로 차량별 누적 주차 시간을 계산하여 요금을 일괄로 정산
     * 누적 주차 시간이 기본 시간 이하라면, 기본 요금을 청구
     * 누적 주차 시간이 기본 시간을 초과하면, 기본 요금에 더해서, 초과한 시간에 대해서 단위 시간 마다 단위 요금을 청구
     * 초과한 시간이 단위 시간으로 나누어 떨어지지 않으면, 올림
     *
     * fees: 주차 요금 (정수 배열)
     * * fees[0]: 기본 시간 (분)
     * * fees[1]: 기본 요금 (원)
     * * fees[2]: 단위 시간 (분)
     * * fees[3]: 단위 요금 (원)
     * records: 입/출차 내역 (문자열 배열)
     * * format: 시각 차량번호 내역 (하나의 공백으로 구분)
     * * 시각: HH:MM (00:00 ~ 23:59)
     * * 차량번호: 0 ~ 9로 구성된 4자리 문자열
     * * 내역: IN(입차) or OUT(출차)
     * 차량 번호가 작은 자동차부터 청구할 주차 요금을 차례대로 정수 배열에 담아서 return
     *
     * 제한사항
     * fees 배열의 길이 = 4
     * * 1 ≤ fees[0] ≤ 1,439
     * * 0 ≤ fees[1] ≤ 100,000
     * * 1 ≤ fees[2] ≤ 1,439
     * * 1 ≤ fees[3] ≤ 10,000
     * 1 ≤ records의 길이 ≤ 1,000
     */

    private companion object {
        private const val LAST_OUT_TIME: String = "23:59"
        private const val OUT: String = "OUT"
        private val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }

    data class FeeInfo(val basicTime: Int, val basicFee: Int, val unitTime: Int, val unitFee: Int)
    data class InOutInfo(val time: LocalTime, val carNumber: String, val inOut: String)
    data class ParkingFeeInfo(val carNumber: String, var inTime: LocalTime, var parkingTime: Int = 0, var isIn: Boolean = true)

    fun solution(fees: IntArray, records: Array<String>): IntArray {
        var answer: IntArray = intArrayOf()
        // 계산 시 명확하게 하기 위해 data class 사용
        val feeInfo = FeeInfo(fees[0], fees[1], fees[2], fees[3])
        // 주차 요금 정보를 담을 리스트
        val parkingFeeInfos: MutableList<ParkingFeeInfo> = mutableListOf()

        records.forEach { record ->
            // 시간, 차량번호, 입/출차 정보를 분리
            val inOutInfo:InOutInfo = record.split(" ").let { InOutInfo(LocalTime.parse(it[0], TIME_FORMAT), it[1], it[2]) }

            /* 차량의 주차 요금 정보를 찾아 정보 업데이트
             * 1.출차 차량인 경우 주차 시간 합산
             * 2.재 입차 차량인 경우 입차 시간 업데이트
             * 3.신규 입차 차량인 경우 주차 요금 정보 리스트에 추가
             */
            parkingFeeInfos.firstOrNull { it.carNumber == inOutInfo.carNumber }?.also { parkingFeeInfo ->
                // 입차 여부 업데이트
                parkingFeeInfo.isIn = inOutInfo.inOut != OUT

                // 입차인 경우 입차 시간 업데이트
                if (parkingFeeInfo.isIn) {
                    parkingFeeInfo.inTime = inOutInfo.time
                }
                // 출차인 경우 주차 시간 합산
                else {
                    parkingFeeInfo.parkingTime += parkingFeeInfo.inTime.BetweenMinutes(inOutInfo.time)
                }
            } ?: parkingFeeInfos.add(
                ParkingFeeInfo(
                    inOutInfo.carNumber,
                    inOutInfo.time
                )
            )
        }

        // 차량 번호가 작은 순으로 정렬
        parkingFeeInfos.sortedBy { it.carNumber }.forEach { parkingFeeInfo ->
            // 출차 내역 없는 차량 요금 계산
            if (parkingFeeInfo.isIn) {
                // 주차 시간 계산
                parkingFeeInfo.parkingTime += parkingFeeInfo.inTime.BetweenMinutes(LocalTime.parse(LAST_OUT_TIME, TIME_FORMAT))
            }

            // 주차 요금 계산
            answer += feeCalculation(parkingFeeInfo.parkingTime, feeInfo)
        }

        return answer
    }

    private fun feeCalculation(parkingTime: Int, feeInfo: FeeInfo): Int {
        // 기본 시간을 초과한 경우
        return if (parkingTime > feeInfo.basicTime) {
            // 기본 요금 + (올림(초과 시간 / 단위 시간) * 단위 요금)
            feeInfo.basicFee + ceil((parkingTime - feeInfo.basicTime).toDouble() / feeInfo.unitTime).toInt() * feeInfo.unitFee
        }
        // 기본 시간 이하인 경우 기본 요금만 청구
        else {
            feeInfo.basicFee
        }
    }

    // 시간 차이 계산(분단위)
    private fun LocalTime.BetweenMinutes(endTime: LocalTime): Int {
        return Duration.between(this, endTime).toMinutes().toInt()
    }
}

fun main() {
    val parkingFeeCalculator = ParkingFeeCalculator()

    /* 1
    val fees = intArrayOf(180, 5000, 10, 600)
    val records = arrayOf("05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT") // result [14600, 34400, 5000]
    */

    /* 2
    val fees = intArrayOf(120, 0, 60, 591)
    val records = arrayOf("16:00 3961 IN", "16:00 0202 IN", "18:00 3961 OUT", "18:00 0202 OUT", "23:58 3961 IN") // result [0, 591]
    */

    /* 3 */
    val fees = intArrayOf(1, 461, 1, 10)
    val records = arrayOf("00:00 1234 IN") // result [14841]


    val result = parkingFeeCalculator.solution(fees, records)
    println(result.joinToString(" "))
}