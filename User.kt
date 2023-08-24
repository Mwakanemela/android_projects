package com.example.chatappupdate

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var notificationToken:String? = null

    constructor() {}
    constructor(name: String?, email: String?, uid: String?, notificationToken:String?) {
        this.name= name
        this.email = email
        this.uid = uid
        this.notificationToken = notificationToken
    }
}