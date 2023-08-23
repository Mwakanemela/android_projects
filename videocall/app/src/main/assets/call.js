let localVideo = document.getElementById("local-video")
let remoteVideo = document.getElementById("remote-video")

// initial display - do not display
localVideo.style.opacity = 0
remoteVideo.style.opacity = 0

// display if they are playing
localVideo.onplaying = () => { localVideo.style.opacity = 1 }
remoteVideo.onplaying = () => { remoteVideo.style.opacity = 1 }

let peer
function init(userId) {
    peer = new Peer(userId, {
        host: '192.168.56.1', //102.70.4.147
        port: 9000,
        path: '/videocall'
    })
//peerjs --port 9000 --key peerjs --path /videocall - server cmd
    peer.on('open', () => {
        // Make a call to kotlin in android
       Android.onPeerConnected()
    })

    listen()
}

let localStream
function listen() {
    peer.on('call', (call) => {

        navigator.getUserMedia({
            audio: true, 
            video: true
        }, (stream) => {
            localVideo.srcObject = stream
            localStream = stream

            call.answer(stream)
            call.on('stream', (remoteStream) => {
                remoteVideo.srcObject = remoteStream

                remoteVideo.className = "primary-video"
                localVideo.className = "secondary-video"

            })

        })
        
    })
}

function startCall(otherUserId) {
    navigator.getUserMedia({
        audio: true,
        video: true
    }, (stream) => {

        localVideo.srcObject = stream
        localStream = stream

        const call = peer.call(otherUserId, stream)
        call.on('stream', (remoteStream) => {
            remoteVideo.srcObject = remoteStream

            remoteVideo.className = "primary-video"
            localVideo.className = "secondary-video"
        })

    })
}

function toggleVideo(b) {
    if (b == "true") {
        localStream.getVideoTracks()[0].enabled = true
    } else {
        localStream.getVideoTracks()[0].enabled = false
    }
} 

function toggleAudio(b) {
    if (b == "true") {
        localStream.getAudioTracks()[0].enabled = true
    } else {
        localStream.getAudioTracks()[0].enabled = false
    }
} 

// Function to end the call
function endCall() {
    // Close the peer connection
    if (peer && peer.destroy) {
        peer.destroy();
    }

    // Release local media stream (if needed)
    if (localStream) {
        localStream.getTracks().forEach((track) => {
            track.stop();
        });
    }

    // Reset the video elements and UI
    localVideo.srcObject = null;
    remoteVideo.srcObject = null;

    // Update UI as needed (e.g., hide video elements, show call end message)
    localVideo.style.opacity = 0;
    remoteVideo.style.opacity = 0;
}

// end call code
/*// Function to end the call and signal the other peer
function endCall() {
    // Close the peer connection (local peer)
    if (peer && peer.destroy) {
        peer.destroy();
    }

    // Send a signal to the other peer indicating call termination
    if (otherPeerId) { // Replace 'otherPeerId' with the actual ID of the other peer
        peer.send({ type: 'call-end' });
    }

    // Release local media stream (if needed)
    if (localStream) {
        localStream.getTracks().forEach((track) => {
            track.stop();
        });
    }

    // Reset the video elements and UI
    localVideo.srcObject = null;
    remoteVideo.srcObject = null;

    // Update UI as needed (e.g., hide video elements, show call end message)
    localVideo.style.opacity = 0;
    remoteVideo.style.opacity = 0;



    On the receiving side, you should listen for incoming signals and take action when a 'call-end' signal is received. Modify your signaling code to handle incoming signals and invoke the endCall function when the 'call-end' signal is detected.

javascript
Copy code
// Example of handling incoming signals (simplified)
peer.on('data', (data) => {
    if (data.type === 'call-end') {
        // Invoke the endCall function to end the call
        endCall();
    }
    // Handle other types of signals as needed
});
}
*/