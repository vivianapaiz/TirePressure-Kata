package org.example

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random

class AlarmTest {
    private val sensor : Sensor = mockk()

    @Test
    fun alarmIsOffByDefault() {
        val alarm = Alarm(sensor)
        assertFalse(alarm.isAlarmOn())
    }

    @Test
    fun alarmIsOnWhenPressureIsTooLow() {
        val alarm = Alarm(sensor)
        withSensorPressure(max = 15.0)
        alarm.check()
        assertTrue(alarm.isAlarmOn())
    }

    @Test
    fun alarmIsOnWhenPressureIsTooHigh() {
        val alarm = Alarm(sensor)
        withSensorPressure(min = 21.0)
        alarm.check()
        assertTrue(alarm.isAlarmOn())
    }

    @Test
    fun `alarm should be off when pressure is normal`() {
        withSensorPressure(min = 17.0, max = 21.0)
        val alarm = Alarm(sensor)
        alarm.check()
        assertFalse(alarm.isAlarmOn())
    }

    private fun withSensorPressure(min: Double = 0.0, max: Double = 99.9) {
        val pressure = Random.nextDouble(min, max)
        every { sensor.popNextPressurePsiValue() } returns pressure
        }
}
