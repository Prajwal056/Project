import pyrebase
from pusher_push_notifications import PushNotifications

config = {
    'apiKey': "AIzaSyBgGCobx-1M03qcTB9kwqocZkegqiTsTL4",
    'authDomain': "iot-control-3.firebaseapp.com",
    'databaseURL': "https://iot-control-3.firebaseio.com",
    'projectId': "iot-control-3",
    'storageBucket': "iot-control-3.appspot.com",
    'messagingSenderId': "1094405237479",
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()

beams_client = PushNotifications(
    instance_id='52c6e414-9b94-4339-9105-53323844f1a3',
    secret_key='51512610053659EBEA5B990AFA1CCFE009EE6F43A80264E27A9AD605B6A3EF17',
)

def  stream_handler(message):
    print(message)
    if (message['data'] is 1):
        response = beams_client.publish_to_interests(
            interests=['hello'],
            publish_body={
                'apns': {
                    'aps': {
                        'alert': 'Hello!',
                    },
                },
                'fcm': {
                    'notification': {
                        'title': 'Fire in the forest',
                        'body': 'RUN RUN RUN',
                    },
                },
            },
        )
        print(response['publishId'])
my_stream = db.child("fire_sensor_status").stream(stream_handler, None)
