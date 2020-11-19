package com.example.instagramapp.Models

class User {
    private var userName : String? = ""
    private var fullName : String? = null
    private var bio : String? = null
    private var email : String? = null
    private var profileImage : String? = null
    private var uid : String? = null



    constructor(
        userName: String?,
        fullName: String?,
        bio: String?,
        email: String?,
        profileImage: String?,
        uid: String?
    ) {
        this.userName = userName
        this.fullName = fullName
        this.bio = bio
        this.email = email
        this.profileImage = profileImage
        this.uid = uid
    }

    constructor()

    fun getuserName(): String{
        return userName!!
    }
    fun setuserName(userName: String?){
        this.userName = userName
    }
    fun getfullName(): String{
        return fullName!!
    }
    fun setfullName(fullName : String?){
        this.fullName = fullName
    }
    fun getbio(): String{
        return bio!!
    }
    fun setbio(bio: String?){
        this.bio = bio
    }
    fun getEmail(): String{
        return email!!
    }
    fun setEmail(email: String?){
        this.email = email
    }
    fun getProfileImage(): String{
        return profileImage!!
    }
    fun setProfileImage(profileImage: String?){
        this.profileImage = profileImage
    }
}
