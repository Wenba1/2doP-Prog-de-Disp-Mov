package com.ucb.framework.dollar

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ucb.data.dollar.IRealDatabaseDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRealDatabase: IRealDatabaseDataSource {

   /*override fun getDollarUpdates(): Flow<String> = callbackFlow {

        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                close(p0.toException())
            }
            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.getValue(String::class.java)
                if (value != null) {
                    trySend(value)
                }
            }
        }

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("app_dollar")
        myRef.addValueEventListener(callback)

        awaitClose {
            myRef.removeEventListener(callback)
        }
    }*/
   override fun getDollarUpdates(): Flow<String> = callbackFlow {
       // Asegura que apuntamos a TU instancia
       val db = Firebase.database("https://ucbtest-c808b-default-rtdb.firebaseio.com/")
       val ref = db.getReference("app_dollar") // 👈 tu nodo

       val listener = object : ValueEventListener {
           override fun onDataChange(snap: DataSnapshot) {
               // Firebase guarda enteros como Long; convertimos seguro a Double
               val oficial = snap.child("oficial").getValue(Double::class.java)
                   ?: snap.child("oficial").getValue(Long::class.java)?.toDouble()
                   ?: 0.0
               val paralelo = snap.child("paralelo").getValue(Double::class.java)
                   ?: snap.child("paralelo").getValue(Long::class.java)?.toDouble()
                   ?: 0.0
               val venta_of = snap.child("venta_of").getValue(Double::class.java)
                   ?: snap.child("venta_of").getValue(Long::class.java)?.toDouble()
                   ?: 0.0
               val venta_par = snap.child("venta_par").getValue(Double::class.java)
                   ?: snap.child("venta_par").getValue(Long::class.java)?.toDouble()
                   ?: 0.0

               trySend("Compra Oficial: $oficial • Compra Paralelo: $paralelo • Venta Oficial: $venta_of • Venta Paralelo: $venta_par")
           }

           override fun onCancelled(error: DatabaseError) {
               close(error.toException())
           }
       }

       ref.addValueEventListener(listener)
       awaitClose { ref.removeEventListener(listener) }
   }

}