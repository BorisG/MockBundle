
import android.os.Bundle
import android.os.Parcelable
import com.nhaarman.mockitokotlin2.whenever
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock

// Inspired by the amazing solution from andrei-zaitcev:
// https://stackoverflow.com/a/40383527/3853450

fun mockBundle(): Bundle {
    val fakeData = HashMap<String, Any>()
    val mockBundle = mock(Bundle::class.java)

    mockPuts(mockBundle, fakeData)
    mockGets(mockBundle, fakeData)

    return mockBundle
}

fun mockGets(mockBundle: Bundle, fakeData: HashMap<String, Any>) {
    whenever(mockBundle.containsKey(anyString())).thenAnswer { invocation ->
        fakeData.contains(key(invocation))
    }

    whenever(mockBundle.get(anyString())).thenAnswer { invocation ->
        fakeData[key(invocation)] as Any
    }

    whenever(mockBundle.getString(anyString())).thenAnswer { invocation ->
        fakeData[key(invocation)] as String
    }

    whenever(mockBundle.getInt(anyString())).thenAnswer { invocation ->
        fakeData[key(invocation)] as Int
    }

    whenever(mockBundle.getParcelable<Parcelable>(anyString())).thenAnswer { invocation ->
        fakeData[key(invocation)] as Parcelable
    }

    ///////////////////////////////////////
    // Add all the getters you need here //
    ///////////////////////////////////////
}

private fun mockPuts(mockBundle: Bundle, fakeData: HashMap<String, Any>) {
    doAnswer {
        fakeData[key(it)] = value(it) as String
        null
    }.whenever(mockBundle).putString(anyString(), anyString())

    doAnswer {
        fakeData[key(it)] = value(it) as Int
        null
    }.whenever(mockBundle).putInt(anyString(), anyInt())

    doAnswer {
        fakeData[key(it)] = value(it) as Parcelable
        null
    }.whenever(mockBundle).putParcelable(anyString(), any())

    ///////////////////////////////////////
    // Add all the putters you need here //
    ///////////////////////////////////////
}

private fun key(invocation: InvocationOnMock) = invocation.arguments[0] as String
private fun value(invocation: InvocationOnMock) = invocation.arguments[1]
