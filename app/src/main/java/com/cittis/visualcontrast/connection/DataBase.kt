package com.cittis.visualcontrast.connection

import android.content.Context

class DataBase(// Context
    private var context: Context
) {
/*
     // Data from GET
    var check = false
    var message = ""

    //adding a new record to database
    fun addSignal(signalMain: CittisList, url: String = EndPoints.URL_ADD_SIGNAL): Boolean {

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Log.e("JSON", response)
                check = try {
                    val obj = JSONObject(response)
                    // Check message error - Get
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show()
                    (!obj.getBoolean("error"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("Error", e.message)
                    false
                }
            },
            Response.ErrorListener { volleyError ->
                Log.e("Error_2", volleyError.message)
                Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                check = false
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Signal"] = signalMain.toString()
                Log.e("Parameter", params.toString())
                // Flag - Check
                check = true
                return params
            }
        }


        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)

        // Show Content
        Log.i("Make", stringRequest.toString())

        //CustomVolleyRequest.getInstance(contextMain).addToRequestQueue(stringRequest);
        return check

    }


    fun getPostBoolean(user: String, password: String, url: String = EndPoints.URL_CHECK_SIGIN): Boolean {
        //creating volley string request

        val service = ServiceVolley()
        val apiController = APIController(service)

        val params = JSONObject()
        params.put("userAcceso", user)
        params.put("passAcceso", password)


        apiController.post(url, params) { response ->
            // Parse the result
            message =  response!!.getString("message").toString()
            check = (!response.getBoolean("error"))
            Log.e("Data",response.toString());
        }


        /*val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.e("JSON", response)
                check = try {
                    val obj = JSONObject(response)
                    message =  obj.getString("message").toString()
                    (!obj.getBoolean("error"))
                } catch (e: JSONException) {
                    false
                }
            },
            Response.ErrorListener {
                check = false
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["userAcceso"] = user
                params["passAcceso"] = password
                return params
            }
        }
        //adding request to queue
        //CustomVolleyRequest(context).addToRequestQueue(stringRequest)
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        //CustomVolleyRequest.getInstance(context).addToRequestQueue(stringRequest)
        */
        return check
    }


    fun sendcall() {
        //RequestQueue initialized
        var mRequestQueue = Volley.newRequestQueue(context)

        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_CHECK_SIGIN,
            Response.Listener<String> { response ->
                val obj = JSONObject(response)
                Log.i("This is the Response",(!obj.getBoolean("error")).toString());
                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()


            }, Response.ErrorListener { error ->
                Log.i("This is the error", "Error :$error")
                Toast.makeText(context, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["userAcceso"] = "as"
                params2["passAcceso"] = "Mavster.1"
                return JSONObject(params2).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }

    // Get Single Data
    fun getDataSingle(url: String): String {
        var inside = 0
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            Response.Listener<String> { s ->
                if (s.isNotEmpty() && inside == 0) {
                    message = ""
                    inside++
                    message = s.toString()
                }
            }, Response.ErrorListener { volleyError ->
                Log.e("Error", volleyError.message)
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
        return message
    }


    // TODO: Rvisar
    // EndPoints.URL_GET_ARTIST
    private fun getArraymessages(url: String) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    // Check message error - Get
                    if (!obj.getBoolean("error")) {
                        // Get Object Array
                        // val array = obj.getJSONArray("artists")
                        /*/ Json to Object
                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Artist(
                                objectArtist.getString("name"),
                                objectArtist.getString("genre")
                            )
                            artistList!!.add(artist)
                         }*/
                    } else {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
    }

    // Test
    fun getData(url: String): ArrayList<String> {
        var inside = 0
        val messages = ArrayList<String>()
        val stringRequest = StringRequest(
            Request.Method.GET, // Method GET
            url,    // Url JSON
            Response.Listener<String> { s ->
                try {
                    // Check if Values Get is not Empty
                    if (s.isNotEmpty() && inside == 0) {
                        inside++
                        // Get messages by URL and make the JSON - Array
                        val messagesJSON = JSONArray(s)
                        // Add messages to array (Formatted JSON)
                        for (i in 0 until messagesJSON.length()) {
                            messages.add(messagesJSON.get(i).toString())
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError ->
                Log.e("Error", volleyError.message)
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
        return messages
    }
*/

}