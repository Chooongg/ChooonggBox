package com.chooongg.utils

import androidx.annotation.IntDef
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeConstants {
    const val MSEC = 1
    const val SEC = 1000
    const val MIN = 60000
    const val HOUR = 3600000
    const val DAY = 86400000

    @IntDef(MSEC, SEC, MIN, HOUR, DAY)
    annotation class Unit
}

object TimeUtils {

    private val SDF_THREAD_LOCAL: ThreadLocal<HashMap<String, SimpleDateFormat>> =
        object : ThreadLocal<HashMap<String, SimpleDateFormat>>() {
            override fun initialValue(): HashMap<String, SimpleDateFormat> = HashMap()
        }

    private fun getDefaultFormat() = getSafeDateFormat("yyyy-MM-dd HH:mm:ss")

    fun getSafeDateFormat(pattern: String): SimpleDateFormat {
        val sdfMap = SDF_THREAD_LOCAL.get()!!
        var simpleDateFormat = sdfMap[pattern]
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            sdfMap[pattern] = simpleDateFormat
        }
        return simpleDateFormat
    }

    fun millis2String(millis: Long): String =
        millis2String(millis, getDefaultFormat())

    fun millis2String(millis: Long, pattern: String): String =
        millis2String(millis, getSafeDateFormat(pattern))

    fun millis2String(millis: Long, format: DateFormat): String =
        format.format(Date(millis))

    fun string2Millis(time: String) =
        string2Millis(time, getDefaultFormat())

    fun string2Millis(time: String, pattern: String) =
        string2Millis(time, getSafeDateFormat(pattern))

    fun string2Millis(time: String, format: DateFormat): Long {
        try {
            return format.parse(time)?.time ?: -1
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }

    fun string2Date(time: String) =
        string2Date(time, getDefaultFormat())

    fun string2Date(time: String, pattern: String) =
        string2Date(time, getSafeDateFormat(pattern))

    fun string2Date(time: String, format: DateFormat): Date? {
        try {
            return format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun date2String(date: Date) =
        date2String(date, getDefaultFormat())

    fun date2String(date: Date, pattern: String) =
        getSafeDateFormat(pattern).format(date)

    fun date2String(date: Date, format: DateFormat) =
        format.format(date)

    fun date2Millis(date: Date) =
        date.time

    fun millis2Date(millis: Long) =
        Date(millis)

    /**
     * 根据单位返回时间跨度
     * pattern = `yyyy-MM-dd HH:mm:ss`
     */
    fun getTimeSpan(
        time1: String,
        time2: String,
        @TimeConstants.Unit unit: Int
    ) = getTimeSpan(time1, time2, getDefaultFormat(), unit)

    /**
     * 根据单位返回时间跨度
     */
    fun getTimeSpan(
        time1: String,
        time2: String,
        format: DateFormat,
        @TimeConstants.Unit unit: Int
    ) = millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit)

    /**
     * 根据单位返回时间跨度
     */
    fun getTimeSpan(
        date1: Date,
        date2: Date,
        @TimeConstants.Unit unit: Int
    ) = millis2TimeSpan(date2Millis(date1) - date2Millis(date2), unit)

    /**
     * 根据单位返回时间跨度
     */
    fun getTimeSpan(
        millis1: Long,
        millis2: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2TimeSpan(millis1 - millis2, unit)

    /**
     * 返回拟合时间跨度
     * pattern = `yyyy-MM-dd HH:mm:ss`
     *  * precision = 0, return null
     *  * precision = 1, return 天
     *  * precision = 2, return 天, 小时
     *  * precision = 3, return 天, 小时, 分钟
     *  * precision = 4, return 天, 小时, 分钟, 秒
     *  * precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒
     */
    fun getFitTimeSpan(
        time1: String,
        time2: String,
        precision: Int
    ): String? {
        val delta =
            string2Millis(time1, getDefaultFormat()) - string2Millis(time2, getDefaultFormat())
        return millis2FitTimeSpan(delta, precision)
    }

    /**
     * 返回拟合时间跨度
     *  * precision = 0, return null
     *  * precision = 1, return 天
     *  * precision = 2, return 天, 小时
     *  * precision = 3, return 天, 小时, 分钟
     *  * precision = 4, return 天, 小时, 分钟, 秒
     *  * precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒
     */
    fun getFitTimeSpan(
        time1: String,
        time2: String,
        format: DateFormat,
        precision: Int
    ): String? {
        val delta = string2Millis(time1, format) - string2Millis(time2, format)
        return millis2FitTimeSpan(delta, precision)
    }

    /**
     * 返回拟合时间跨度
     *  * precision = 0, return null
     *  * precision = 1, return 天
     *  * precision = 2, return 天, 小时
     *  * precision = 3, return 天, 小时, 分钟
     *  * precision = 4, return 天, 小时, 分钟, 秒
     *  * precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒
     */
    fun getFitTimeSpan(date1: Date, date2: Date, precision: Int) =
        millis2FitTimeSpan(date2Millis(date1) - date2Millis(date2), precision)

    /**
     * 返回拟合时间跨度
     *  * precision = 0, return null
     *  * precision = 1, return 天
     *  * precision = 2, return 天, 小时
     *  * precision = 3, return 天, 小时, 分钟
     *  * precision = 4, return 天, 小时, 分钟, 秒
     *  * precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒
     */
    fun getFitTimeSpan(
        millis1: Long,
        millis2: Long,
        precision: Int
    ) = millis2FitTimeSpan(millis1 - millis2, precision)

    /**
     * 当前时间戳
     */
    fun getNowMills() =
        System.currentTimeMillis()

    /**
     * 当前时间
     * pattern = `yyyy-MM-dd HH:mm:ss`
     */
    fun getNowString() =
        millis2String(System.currentTimeMillis(), getDefaultFormat())

    /**
     * 当前时间
     */
    fun getNowString(format: DateFormat) =
        millis2String(System.currentTimeMillis(), format)

    /**
     * 当前时间
     */
    fun getNowDate() =
        Date()

    /**
     * 根据单位返回与当前时间跨度
     * pattern = `yyyy-MM-dd HH:mm:ss`
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     */
    fun getTimeSpanByNow(time: String, @TimeConstants.Unit unit: Int) =
        getTimeSpan(time, getNowString(), getDefaultFormat(), unit)

    /**
     * 根据单位返回与当前时间跨度
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     */
    fun getTimeSpanByNow(
        time: String,
        format: DateFormat,
        @TimeConstants.Unit unit: Int
    ) = getTimeSpan(time, getNowString(format), format, unit)

    /**
     * 根据单位返回与当前时间跨度
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     */
    fun getTimeSpanByNow(date: Date, @TimeConstants.Unit unit: Int) =
        getTimeSpan(date, Date(), unit)

    /**
     * 根据单位返回与当前时间跨度
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     */
    fun getTimeSpanByNow(millis: Long, @TimeConstants.Unit unit: Int) =
        getTimeSpan(millis, System.currentTimeMillis(), unit)

    /**
     * 返回与当前拟合时间跨度
     * pattern = `yyyy-MM-dd HH:mm:ss`
     *  * precision = 0，返回 null
     *  * precision = 1，返回天
     *  * precision = 2，返回天和小时
     *  * precision = 3，返回天、小时和分钟
     *  * precision = 4，返回天、小时、分钟和秒
     *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
     */
    fun getFitTimeSpanByNow(time: String, precision: Int) =
        getFitTimeSpan(time, getNowString(), getDefaultFormat(), precision)

    /**
     * 返回与当前拟合时间跨度
     *  * precision = 0，返回 null
     *  * precision = 1，返回天
     *  * precision = 2，返回天和小时
     *  * precision = 3，返回天、小时和分钟
     *  * precision = 4，返回天、小时、分钟和秒
     *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
     */
    fun getFitTimeSpanByNow(
        time: String,
        format: DateFormat,
        precision: Int
    ) = getFitTimeSpan(time, getNowString(format), format, precision)

    /**
     * 返回与当前拟合时间跨度
     *  * precision = 0，返回 null
     *  * precision = 1，返回天
     *  * precision = 2，返回天和小时
     *  * precision = 3，返回天、小时和分钟
     *  * precision = 4，返回天、小时、分钟和秒
     *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
     */
    fun getFitTimeSpanByNow(date: Date, precision: Int) =
        getFitTimeSpan(date, getNowDate(), precision)

    /**
     * 返回与当前拟合时间跨度
     *  * precision = 0，返回 null
     *  * precision = 1，返回天
     *  * precision = 2，返回天和小时
     *  * precision = 3，返回天、小时和分钟
     *  * precision = 4，返回天、小时、分钟和秒
     *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
     */
    fun getFitTimeSpanByNow(millis: Long, precision: Int) =
        getFitTimeSpan(millis, System.currentTimeMillis(), precision)

    /**
     * 返回与现在友好的时间跨度
     * pattern = `yyyy-MM-dd HH:mm:ss`
     *  * 如果小于 1 秒钟内，显示刚刚
     *  * 如果在 1 分钟内，显示 XXX秒前
     *  * 如果在 1 小时内，显示 XXX分钟前
     *  * 如果在 1 小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getFriendlyTimeSpanByNow(time: String) = getFriendlyTimeSpanByNow(time, getDefaultFormat())

    /**
     * 返回与现在友好的时间跨度
     *  * 如果小于 1 秒钟内，显示刚刚
     *  * 如果在 1 分钟内，显示 XXX秒前
     *  * 如果在 1 小时内，显示 XXX分钟前
     *  * 如果在 1 小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getFriendlyTimeSpanByNow(
        time: String,
        format: DateFormat
    ) = getFriendlyTimeSpanByNow(string2Millis(time, format))

    /**
     * 返回与现在友好的时间跨度
     *  * 如果小于 1 秒钟内，显示刚刚
     *  * 如果在 1 分钟内，显示 XXX秒前
     *  * 如果在 1 小时内，显示 XXX分钟前
     *  * 如果在 1 小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getFriendlyTimeSpanByNow(date: Date) = getFriendlyTimeSpanByNow(date.time)

    /**
     * 返回与现在友好的时间跨度
     *  * 如果小于 1 秒钟内，显示刚刚
     *  * 如果在 1 分钟内，显示 XXX秒前
     *  * 如果在 1 小时内，显示 XXX分钟前
     *  * 如果在 1 小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getFriendlyTimeSpanByNow(millis: Long): String? {
        val now = System.currentTimeMillis()
        val span = now - millis
        if (span < 0) // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis)
        when {
            span < 1000 -> return "刚刚"
            span < TimeConstants.MIN -> return java.lang.String.format(
                Locale.getDefault(),
                "%d秒前",
                span / TimeConstants.SEC
            )
            span < TimeConstants.HOUR -> return java.lang.String.format(
                Locale.getDefault(),
                "%d分钟前",
                span / TimeConstants.MIN
            )
            // 获取当天 00:00
            else -> {
                val wee = getWeeOfToday()
                return when {
                    millis >= wee -> String.format("今天%tR", millis)
                    millis >= wee - TimeConstants.DAY -> String.format("昨天%tR", millis)
                    millis >= getWeeOfThisWeek() -> {
                        val cal = Calendar.getInstance()
                        cal.timeInMillis = millis
                        var weekInt = cal.get(Calendar.DAY_OF_WEEK)
                        if (cal.firstDayOfWeek == Calendar.SUNDAY) {
                            weekInt -= 1
                            if (weekInt == 0) {
                                weekInt = 7
                            }
                        }
                        "${
                            when (weekInt) {
                                1 -> "周一"
                                2 -> "周二"
                                3 -> "周三"
                                4 -> "周四"
                                5 -> "周五"
                                6 -> "周六"
                                else -> "周日"
                            }
                        }${millis2String(millis, "HH:mm")}"
                    }
                    millis >= getWeeOfThisYear() -> millis2String(millis, "M月d日")
                    else -> millis2String(millis, "yyyy年M月d日")
                }
            }
        }
    }

    fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    fun getWeeOfThisWeek(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.DAY_OF_WEEK] = 1
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    fun getWeeOfThisYear(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.DAY_OF_YEAR] = 0
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    /**
     * Return the milliseconds differ time span.
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the milliseconds differ time span
     */
    fun getMillis(
        millis: Long,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis + timeSpan2Millis(timeSpan, unit)

    /**
     * Return the milliseconds differ time span.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the milliseconds differ time span
     */
    fun getMillis(
        time: String?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = getMillis(time, getDefaultFormat(), timeSpan, unit)

    /**
     * Return the milliseconds differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the milliseconds differ time span.
     */
    fun getMillis(
        time: String?,
        format: DateFormat,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = string2Millis(time!!, format) + timeSpan2Millis(timeSpan, unit)

    /**
     * Return the milliseconds differ time span.
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the milliseconds differ time span.
     */
    fun getMillis(
        date: Date?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = date2Millis(date!!) + timeSpan2Millis(timeSpan, unit)

    /**
     * Return the formatted time string differ time span.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        millis: Long,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = getString(millis, getDefaultFormat(), timeSpan, unit)

    /**
     * Return the formatted time string differ time span.
     *
     * @param millis   The milliseconds.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        millis: Long,
        format: DateFormat,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2String(millis + timeSpan2Millis(timeSpan, unit), format)

    /**
     * Return the formatted time string differ time span.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        time: String?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = getString(time, getDefaultFormat(), timeSpan, unit)

    /**
     * Return the formatted time string differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        time: String?,
        format: DateFormat,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2String(string2Millis(time!!, format) + timeSpan2Millis(timeSpan, unit), format)

    /**
     * Return the formatted time string differ time span.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        date: Date?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = getString(date, getDefaultFormat(), timeSpan, unit)

    /**
     * Return the formatted time string differ time span.
     *
     * @param date     The date.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span
     */
    fun getString(
        date: Date?,
        format: DateFormat,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2String(date2Millis(date!!) + timeSpan2Millis(timeSpan, unit), format)

    /**
     * Return the date differ time span.
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the date differ time span
     */
    fun getDate(
        millis: Long,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2Date(millis + timeSpan2Millis(timeSpan, unit))

    /**
     * Return the date differ time span.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the date differ time span
     */
    fun getDate(
        time: String?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = getDate(time, getDefaultFormat(), timeSpan, unit)

    /**
     * Return the date differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the date differ time span
     */
    fun getDate(
        time: String?,
        format: DateFormat,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2Date(string2Millis(time!!, format) + timeSpan2Millis(timeSpan, unit))

    /**
     * Return the date differ time span.
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the date differ time span
     */
    fun getDate(
        date: Date?,
        timeSpan: Long,
        @TimeConstants.Unit unit: Int
    ) = millis2Date(date2Millis(date!!) + timeSpan2Millis(timeSpan, unit))

    /**
     * Return the milliseconds differ time span by now.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the milliseconds differ time span by now
     */
    fun getMillisByNow(timeSpan: Long, @TimeConstants.Unit unit: Int) =
        getMillis(getNowMills(), timeSpan, unit)

    /**
     * Return the formatted time string differ time span by now.
     *
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span by now
     */
    fun getStringByNow(timeSpan: Long, @TimeConstants.Unit unit: Int) =
        getStringByNow(timeSpan, getDefaultFormat(), unit)

    /**
     * Return the formatted time string differ time span by now.
     *
     * @param timeSpan The time span.
     * @param format   The format.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the formatted time string differ time span by now
     */
    fun getStringByNow(
        timeSpan: Long,
        format: DateFormat,
        @TimeConstants.Unit unit: Int
    ) = getString(getNowMills(), format, timeSpan, unit)

    /**
     * Return the date differ time span by now.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *
     *  * [TimeConstants.MSEC]
     *  * [TimeConstants.SEC]
     *  * [TimeConstants.MIN]
     *  * [TimeConstants.HOUR]
     *  * [TimeConstants.DAY]
     *
     * @return the date differ time span by now
     */
    fun getDateByNow(timeSpan: Long, @TimeConstants.Unit unit: Int) =
        getDate(getNowMills(), timeSpan, unit)

    private fun timeSpan2Millis(timeSpan: Long, @TimeConstants.Unit unit: Int): Long {
        return timeSpan * unit
    }

    private fun millis2TimeSpan(millis: Long, @TimeConstants.Unit unit: Int): Long {
        return millis / unit
    }

    fun millis2FitTimeSpan(millis: Long, precision: Int): String? {
        var millis1 = millis
        var precision1 = precision
        if (precision1 <= 0) return null
        precision1 = precision1.coerceAtMost(5)
        val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
        if (millis1 == 0L) return "0" + units[precision1 - 1]
        val sb = StringBuilder()
        if (millis1 < 0) {
            sb.append("-")
            millis1 = -millis1
        }
        val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
        for (i in 0 until precision1) {
            if (millis1 >= unitLen[i]) {
                val mode = millis1 / unitLen[i]
                millis1 -= mode * unitLen[i]
                sb.append(mode).append(units[i])
            }
        }
        return sb.toString()
    }
}