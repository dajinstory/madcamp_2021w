import android.content.Context
import android.util.Log
import com.example.proj1_tablayout.model.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun json2contacts(jsonString: String, context: Context):List<Contact>{
    // Load contacts dataset from json
    val jsonFileString = getJsonDataFromAsset(context, "database.json")
    Log.i("data", jsonFileString ?: "NULL")
    val gson = Gson()
    val listContactType = object : TypeToken<List<Contact>>() {}.type
    var contacts: List<Contact> = gson.fromJson(jsonFileString, listContactType)
    contacts.forEachIndexed { idx, contact -> Log.i("data", contact.name!! )}
    return contacts
}