package com.example.appchat

class Message {
    var message: String? = null
    var senderId: String? = null
    var files: String? = null // This will hold the URI or URL of the attached file
    var fileType: String? = null // This will hold the type of the attached file ("image", "audio", "video")
    //var notificationToken:String? = null
    constructor() {}
    constructor(message: String?, senderId: String, files: String?, fileType: String?) {
        this.message = message
        this.senderId = senderId
        this.files = files
        this.fileType = fileType
       // this.notificationToken = notificationToken
    }
}
