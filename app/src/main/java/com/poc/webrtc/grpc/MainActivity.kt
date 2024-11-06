package com.poc.webrtc.grpc

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poc.webrtc.grpc.common.peer.common.SignalingClient
import com.poc.webrtc.grpc.common.peer.common.StreamPeerConnectionFactory
import com.poc.webrtc.grpc.common.peer.common.WebRTCSessionState
import com.poc.webrtc.grpc.common.peer.common.sessions.LocalWebRtcSessionManager
import com.poc.webrtc.grpc.common.peer.common.sessions.WebRtcSessionManager
import com.poc.webrtc.grpc.common.peer.common.sessions.WebRtcSessionManagerImpl
import com.poc.webrtc.grpc.ui.screens.stage.StageScreen
import com.poc.webrtc.grpc.ui.theme.WebRTCTheme

class MainActivity : ComponentActivity() {
    private val sessionManager: WebRtcSessionManager by lazy {
        WebRtcSessionManagerImpl(
            context = this,
            signalingClient = SignalingClient(),
            peerConnectionFactory = StreamPeerConnectionFactory(this),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), 0)

        setContent {
            WebRTCTheme {
                CompositionLocalProvider(LocalWebRtcSessionManager provides sessionManager) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background,
                    ) {
                        val state by sessionManager.signalingClient.sessionStateFlow.collectAsState()

                        if (!state == WebRTCSessionState.Active) {
                            StageScreen(state = state) { onCallScreen = true }
                        } else {
//                            AudioScreen()

                        }
                    }
                }
            }
    }
}

    @Composable
fun greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun greetingPreview() {
    WebRTCTheme {
        greeting("Android")
    }
}
