import android.content.Context
import android.util.Log
import com.example.proj1_tablayout.model.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream

fun getJsonDataFromAsset(context: Context, filename: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    Log.e(jsonString, jsonString)
    return jsonString
}


//fun json2contacts(jsonString: String, context: Context):List<Contact>{
fun json2contacts(jsonString: String, context: Context):ArrayList<Contact>{
    // Load contacts dataset from json
    val jsonFileString = getJsonDataFromAsset(context, "database.json")
    Log.i("data", jsonFileString ?: "NULL")
    val gson = Gson()
    val listContactType = object : TypeToken<List<Contact>>() {}.type
    var contacts: ArrayList<Contact> = gson.fromJson(jsonFileString, listContactType)
    contacts.forEachIndexed { idx, contact -> Log.i("data", contact.name!! )}
    return contacts
}

fun saveContactsToJson(contacts:ArrayList<Contact>, filename: String) {
    var gson = Gson()
    var jsonString:String = gson.toJson(contacts)


    val file= File("src/main/assets/database1.json")
    file.writeText(jsonString)
}