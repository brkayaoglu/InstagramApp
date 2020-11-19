package com.example.instagramapp.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramapp.Adapters.UserSearchAdapter
import com.example.instagramapp.Models.User
import com.example.instagramapp.R
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var recyclerView : RecyclerView? = null
    private var userSearchAdapter : UserSearchAdapter? = null
    private var userList : MutableList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_search_fragment)

        recyclerView?.setHasFixedSize(true)

        recyclerView?.layoutManager = LinearLayoutManager(context)

        userList = ArrayList()

        //userSearchAdapter?.notifyDataSetChanged()
        userSearchAdapter = context?.let {
            UserSearchAdapter(it, userList as ArrayList<User>,true)
        }

        recyclerView?.adapter = userSearchAdapter

        view.findViewById<EditText>(R.id.search_edit_text).addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if(view.findViewById<EditText>(R.id.search_edit_text).text.toString() == ""){
                    retrieveUser()
               }else{
                   recyclerView?.visibility = View.VISIBLE
                   retrieveUser()
                   searchUser(p0.toString().toLowerCase())
               }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        return view
    }

    private fun searchUser(chars: String) {
        val query = FirebaseDatabase.getInstance().reference.child("Users")
            .orderByChild("fullName").startAt(chars).endAt(chars + "\uf8ff")
        query.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList?.clear()
                for(snap in snapshot.children){
                    val user = snap.getValue(User::class.java)
                    if(user != null){
                        userList!!.add(user)
                        Toast.makeText(context,user.getfullName(),Toast.LENGTH_LONG).show()
                    }
                }

                userSearchAdapter?.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun retrieveUser() {
        val usersRef = FirebaseDatabase.getInstance().reference.child("Users")
        usersRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(view!!.findViewById<EditText>(R.id.search_edit_text).text.toString() == ""){
                    userList?.clear()
                    for(snap in snapshot.children){
                        val user = snap.getValue(User::class.java)
                        if(user != null){
                            userList!!.add(user)
                            Toast.makeText(context,user.getfullName(),Toast.LENGTH_LONG).show()
                        }
                    }
                    userSearchAdapter?.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}