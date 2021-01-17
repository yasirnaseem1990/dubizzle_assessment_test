package app.assessmentdubizzle.com.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    companion object {

        fun convertStringToDate(dateObj: String?): String? {
            var formattedDateStr = ""
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            try {
                val serverDate = SimpleDateFormat("yyyy-MM-dd HH:mm:SSS", Locale.getDefault()).parse(dateObj)
                formattedDateStr = formatter.format(serverDate)
                println("Converted date is: $serverDate")
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return formattedDateStr
        }

    }
}