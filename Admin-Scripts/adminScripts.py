import firebase_admin
from firebase_admin import credentials

cred = credentials.Certificate("women-e598c-firebase-adminsdk-2nvya-385a16cd62.json")
firebase_admin.initialize_app(cred)