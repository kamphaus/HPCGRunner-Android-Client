package lu.uni.ck.pyrunnerconsole;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

public class PubNubService extends Service {
    private Pubnub pubnub;

    public PubNubService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("PubNubService.onCreate");
        pubnub = new Pubnub(
                (String) getResources().getText(R.string.pubnub_pubkey),
                (String) getResources().getText(R.string.pubnub_subkey),
                true
        );

        try {
            pubnub.subscribe((String) getResources().getText(R.string.pubnub_channel), new Callback() {
                        @Override
                        public void connectCallback(String channel, Object message) {
                            //pubnub.publish("my_channel", "Hello from the PubNub Java SDK", new Callback() {});
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());

                            // Received message:
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                    + " : " + error.toString());
                        }
                    }
            );
        } catch (PubnubException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void onDestroy() {
        pubnub.unsubscribeAll();
        System.out.println("PubNubService.onDestroy");
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
