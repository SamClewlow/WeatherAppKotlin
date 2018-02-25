package uk.co.pdextech.android.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.`when` as whenever
import org.mockito.Mockito.mock
import uk.co.pdextech.android.domain.Forecast
import java.text.DateFormat

/**
 * Created by Pdex on 20/02/2018.
 */
class ExtensionTests {

    @Test fun testLongToDateString() {
        assertEquals("19-Oct-2015", 1445275635000L.toDateString(DateFormat.MEDIUM))
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("Monday, 19 October 2015",
                1445275635000L.toDateString(DateFormat.FULL))
    }

    @Test fun testDataSourceReturnsValue() {
        val ds = mock(ForecastDataSource::class.java)
        whenever(ds.requestDayForecast(0)).then {
            Forecast(0,0,"desc", 0, 0, "url")
        }

        val provider = ForecastProvider(listOf(ds))
        assertNotNull(provider.sources[0])
    }
}